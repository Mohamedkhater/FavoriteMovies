package com.example.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private ArrayList<Movie>movies;


    public static final String BASE_URL="https://image.tmdb.org/t/p/w400";


    private Context context;
    public MoviesAdapter(ArrayList<Movie> movies, Context context){
        this.movies=movies;
        this.context=context;

    }




    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);



        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        moviesViewHolder.bind(i);
        Movie bindMovie=movies.get(i);

        moviesViewHolder.movieName.setText(bindMovie.getTitle());





        Picasso.with(context).load(BASE_URL+bindMovie.getPoster_path()).into(moviesViewHolder.movieThumbnail);


     //   moviesViewHolder.movieThumbnail.setOnClickListener(this);

       Intent intent= new Intent(context,DetailActivity.class);





    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public  static final String NAME="movie Name";
        TextView movieName;
        Intent inn;



        private ImageView movieThumbnail;



        private MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName=itemView.findViewById(R.id.movie_name);
            movieThumbnail=itemView.findViewById(R.id.movie_image);

        }

        private void bind(int i){

            movieThumbnail.setOnClickListener(this);
             inn= new Intent(context,DetailActivity.class);
            inn.putExtra(NAME,movies.get(i));

        }


        @Override
        public void onClick(View v) {

            v.getContext().startActivity(inn);
        }
    }
}
