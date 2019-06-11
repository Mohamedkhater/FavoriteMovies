package com.example.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Utils.NetworkUtils;

import static java.lang.Math.round;

public class DetailActivity extends AppCompatActivity {
    private String videoURL="http://api.themoviedb.org/3/movie/";
    TextView trailer_tv;
    private static final  String message="PLEASE CONNECT TO A NETWORK AND TRY AGAIN!";
    TextView reviews_tv;
    ProgressBar bb;
    public Movie movie;
    Database mDb;
    Context context=this;
    public final LifecycleOwner owner=this;
    ArrayList<String>trailerslist;
    RecyclerView trailersRecyclerview;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        TextView moiveName_tv;
        RatingBar rating_rb;
        final ImageView movie_image;
        TextView description_tv;
        TextView releaseDate_tv;
        TextView rate;
        trailerslist= new ArrayList<>();
        bb=findViewById(R.id.trailer_pb);
        mDb= Database.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        moiveName_tv=findViewById(R.id.movie_tv);
        releaseDate_tv=findViewById(R.id.release_date);
        movie_image=findViewById(R.id.movie_detail_image);
        rating_rb=findViewById(R.id.rating);
        rate=findViewById(R.id.rate_value);
        description_tv=findViewById(R.id.description);
        reviews_tv=findViewById(R.id.reviews_tv);
        bb=findViewById(R.id.trailer_pb);
        final ImageView favorite=findViewById(R.id.favorite_btn);
        trailersRecyclerview=findViewById(R.id.trailers_rv);



        final Intent intent=getIntent();
        if (intent.hasExtra(MoviesAdapter.MoviesViewHolder.NAME)){
            movie =intent.getParcelableExtra(MoviesAdapter.MoviesViewHolder.NAME);
            final String movieId=movie.getId();
            final String title= movie.getTitle();
            final String rateVal=String.valueOf(round(movie.getVote_average()));
            final String releaseDate=movie.getRelease_date();
            final String description=movie.getOverview();
            final String image=movie.getPoster_path();
            final double voteAverage=movie.getVote_average();



            Picasso.with(DetailActivity.this).load(MoviesAdapter.BASE_URL+movie.getPoster_path())
                    .fit().error(R.drawable.movie_foreground).into(movie_image);
            Log.w(DetailActivity.class.getSimpleName(),String.valueOf(movie.getVote_average()));
            rating_rb.setRating((float)(movie.getVote_average()%5));

            rate.setText("Vote Average: "+rateVal);
            rating_rb.setFocusable(false);
            releaseDate_tv.append(releaseDate);
            description_tv.setText(description);
            moiveName_tv.setText(title);

            final String videoUrl = videoURL + movie.getId() + "/videos";
            String reviewsUrl=videoURL+movie.getId()+"/reviews";
            Log.w(DetailActivity.class.getSimpleName(),reviewsUrl);
             Uri uri = Uri.parse(videoUrl).buildUpon().appendQueryParameter(NetworkUtils.API_KEY,
                     NetworkUtils.API_KEY_VALUE).build();
            final Uri uriReviews=Uri.parse(reviewsUrl).buildUpon().appendQueryParameter(NetworkUtils.API_KEY,
                    NetworkUtils.API_KEY_VALUE).build();
            URL url= null;
            URL urlReviews=null;
            try {
                url = new URL(uri.toString());
                urlReviews=new URL(uriReviews.toString());
                Log.d(DetailActivity.class.getSimpleName(),uriReviews.toString());

                AsyncTaskListener myListener= new AsyncTaskListener<String>() {        // listener for a click event on the trailer TextView
                    @Override
                    public void onComplete(String results) {
                        if (results==null || results=="")
                        {
                            bb.setVisibility(View.INVISIBLE);
                            reviews_tv.setVisibility(View.INVISIBLE);
                            trailer_tv.setVisibility(View.INVISIBLE);
                            Toast.makeText(DetailActivity.this,message,Toast.LENGTH_LONG).show();
                            return;

                        }
                        JSONObject obj = null;

                        try {
                            obj = new JSONObject(results);

                            JSONArray jsonArray = obj.getJSONArray("results");


                            bb.setVisibility(View.INVISIBLE);
                            trailersAdapter adapter=new trailersAdapter(jsonArray);
                            trailersRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                            trailersRecyclerview.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }


                    @Override
                    public void launchTask(URL url) {
                        MovieTask movieTask= new MovieTask(this);
                        movieTask.execute(url);

                    }
                };
                myListener.launchTask(url);
                AsyncTaskListener reviewsListener= new AsyncTaskListener<String>() {
                    @Override
                    public void onComplete(String results) {
                        final Intent intent1 = new Intent(DetailActivity.this,ReviewsActivity.class);
                        intent1.putExtra(ReviewsActivity.REVIEWS_TEXT,results);
                        reviews_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.getContext().startActivity(intent1);
                            }
                        });

                    }

                    @Override
                    public void launchTask(URL url) {
                        MovieTask retrieveReviewsTask= new MovieTask(this);
                        retrieveReviewsTask.execute(url);

                    }
                };
                reviewsListener.launchTask(urlReviews);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            final DetailViewModelFactory factory=new DetailViewModelFactory(mDb,movieId);

            final DetailViewModel viewModel=ViewModelProviders.of(this,factory).get(DetailViewModel.class);
            viewModel.getEntry().observe(this, new Observer<MovieEntry>() {
                @Override
                public void onChanged(@Nullable MovieEntry movieEntry) {
                    if (movieEntry!=null){
                        favorite.setImageResource(R.drawable.ic_favorite_black_24dp);        //Is there a better implementation for that button functionality??
                    }
                    else{
                        favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    }
                    viewModel.getEntry().removeObservers(owner);


                }
            });
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewModel.getEntry().observe(owner, new Observer<MovieEntry>() {
                        @Override
                        public void onChanged(@Nullable final MovieEntry movieEntry) {
                            if (movieEntry!=null){
                                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.movieTaskDao().deleteMovie(movieEntry);
                                    }
                                });
                                favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                viewModel.getEntry().removeObservers(owner);

                            }
                            else{
                                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.movieTaskDao().insertMovie(new MovieEntry(movieId,title,
                                                description,image,voteAverage,
                                                releaseDate));
                                    }
                                });
                                favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                                viewModel.getEntry().removeObservers(owner);
                            }
                        }
                    });

                }
            });





        }

    }

}
