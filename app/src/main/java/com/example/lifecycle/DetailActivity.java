package com.example.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static java.lang.Math.round;

public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TextView moiveName_tv;
        RatingBar rating_rb;
        ImageView movie_image;
        TextView description_tv;
        TextView releaseDate_tv;
        TextView rate;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        moiveName_tv=findViewById(R.id.movie_tv);
        releaseDate_tv=findViewById(R.id.release_date);
        Intent intent=getIntent();
      //  String action=intent.getStringExtra(MoviesAdapter.MoviesViewHolder.name);
        Movie movie =intent.getParcelableExtra(MoviesAdapter.MoviesViewHolder.NAME);
        String title= movie.getTitle();
        movie_image=findViewById(R.id.movie_detail_image);
        Picasso.with(DetailActivity.this).load(MoviesAdapter.BASE_URL+movie.getPoster_path()).fit().into(movie_image);
        rating_rb=findViewById(R.id.rating);
        rating_rb.setRating((float)(movie.getVote_average()%5));
        String rateVal=String.valueOf(round(movie.getVote_average()));
        Log.d(DetailActivity.class.getSimpleName(),rateVal);
       rate=findViewById(R.id.rate_value);

        rate.setText("Vote Average: "+rateVal);
        rating_rb.setFocusable(false);
        String releaseDate=movie.getRelease_date();
        releaseDate_tv.append(releaseDate);
        String description=movie.getOverview();
        description_tv=findViewById(R.id.description);
        description_tv.setText(description);




        moiveName_tv.setText(title);

    }
}
