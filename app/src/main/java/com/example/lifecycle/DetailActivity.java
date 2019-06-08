package com.example.lifecycle;

import android.app.ActionBar;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import java.util.List;
import java.util.zip.Inflater;

import Utils.NetworkUtils;

import static Utils.NetworkUtils.fetchData;
import static java.lang.Math.round;

public class DetailActivity extends AppCompatActivity {
    private String videoURL="http://api.themoviedb.org/3/movie/";
    TextView trailer_tv;
    ArrayList<Uri>moviesUris=new ArrayList<>();

    ImageView favoriteImage;

    private static final  String message="PLEASE CONNECT TO A NETWORK AND TRY AGAIN!";
    TextView reviews_tv;
    ProgressBar bb;

    private String response;
    public static  void setRestpose(String response){
        response=response;
    }
    public Movie movie;
    LinearLayout layout;

    Database mDb;
    boolean isAvailable=false;
    Context context=this;
    public final LifecycleOwner owner=this;
    MovieEntry data;
    ArrayList<String>trailerslist;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        TextView moiveName_tv;
        RatingBar rating_rb;
        ImageView movie_image;
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
        trailer_tv= findViewById(R.id.trailer_tv);
        bb=findViewById(R.id.trailer_pb);

        final Intent intent=getIntent();
        if (intent.hasExtra(MoviesAdapter.MoviesViewHolder.NAME)){
            movie =intent.getParcelableExtra(MoviesAdapter.MoviesViewHolder.NAME);
            String title= movie.getTitle();
            Picasso.with(DetailActivity.this).load(MoviesAdapter.BASE_URL+movie.getPoster_path()).fit().error(R.drawable.movie_foreground).into(movie_image);
            Log.w(DetailActivity.class.getSimpleName(),String.valueOf(movie.getVote_average()));
            rating_rb.setRating((float)(movie.getVote_average()%5));
            String rateVal=String.valueOf(round(movie.getVote_average()));

            rate.setText("Vote Average: "+rateVal);
            rating_rb.setFocusable(false);
            String releaseDate=movie.getRelease_date();
            releaseDate_tv.append(releaseDate);
            String description=movie.getOverview();
            description_tv.setText(description);
            moiveName_tv.setText(title);

            final String videoUrl = videoURL + movie.getId() + "/videos";
            String reviewsUrl=videoURL+movie.getId()+"/reviews";
            Log.w(DetailActivity.class.getSimpleName(),reviewsUrl);
             Uri uri = Uri.parse(videoUrl).buildUpon().appendQueryParameter(NetworkUtils.API_KEY, NetworkUtils.API_KEY_VALUE).build();
            final Uri uriReviews=Uri.parse(reviewsUrl).buildUpon().appendQueryParameter(NetworkUtils.API_KEY,NetworkUtils.API_KEY_VALUE).build();
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
                            ArrayList<TextView> tvs=new ArrayList<>();
                            layout=findViewById(R.id.detail_layout);

                            for (int i=0;i<jsonArray.length();i++){
                                String firstVideoPath = jsonArray.getJSONObject(i).getString("key");
                                final Uri movie_uri = Uri.parse("https://www.youtube.com").buildUpon().appendPath("watch").appendQueryParameter("v", firstVideoPath).build();
                                //  Log.d(DetailActivity.class.getSimpleName(),movie_uri.toString());
                                moviesUris.add(movie_uri);
                                trailer_tv.setVisibility(View.VISIBLE);
                                bb.setVisibility(View.INVISIBLE);


                                TextView tv=new TextView(context);
                                tv.setText("trailer "+i+1);
                                tv.setTextSize(30);
                                tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                layout.addView(tv);
                                final int index=i;

                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (Intent.ACTION_VIEW);


                                        intent.setData(moviesUris.get(index));
                                        v.getContext().startActivity(intent);
                                    }
                                });
                                tvs.add(tv);

                            }


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

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        switch (itemId){
            case R.id.favorite_btn:
                final String movieName=movie.getTitle();
                final String movieId=movie.getId();
                final String description=movie.getOverview();
                final String imagePath=movie.getPoster_path();
                final String releaseDate=movie.getRelease_date();
                final double voteAverage=movie.getVote_average();
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        data=mDb.movieTaskDao().loadMovieById(movieId);
                                if (data==null){
                                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDb.movieTaskDao().insertMovie(new MovieEntry(movieId,movieName,description,imagePath,voteAverage,releaseDate));

                                        }
                                    });


                                }
                                else{

                                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDb.movieTaskDao().deleteMovie(data);

                                        }
                                    });

                                }

                    }
                });

        }


        return true;
    }

}
