package com.example.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.FitWindowsViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView moiveName_tv;
    private RatingBar rating_rb;
    private ImageView movie_image;
    private TextView description_tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        moiveName_tv=(TextView)findViewById(R.id.movie_tv);
        movie_image=(ImageView)findViewById(R.id.movie_image);
        Intent intent=getIntent();
        String action=intent.getStringExtra(MoviesAdapter.MoviesViewHolder.name);
        Movie movie =intent.getParcelableExtra(MoviesAdapter.MoviesViewHolder.name);
        String title= movie.getTitle();
        String img=movie.getPoster_path();
        movie_image=findViewById(R.id.movie_detail_image);
        Picasso.with(DetailActivity.this).load(MoviesAdapter.BASE_URL+movie.getPoster_path()).fit().into(movie_image);
        rating_rb=(RatingBar)findViewById(R.id.rating);
        rating_rb.setRating((float)(movie.getVote_average()%5));
        rating_rb.setFocusable(false);
        String description=movie.getOverview();
        description_tv=findViewById(R.id.description);
        description_tv.setText(description);
        double rating=movie.getVote_average();



        moiveName_tv.setText(title);

    }
}
