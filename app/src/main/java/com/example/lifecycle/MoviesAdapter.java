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
    ArrayList<Movie>movies;
    public ArrayList<Intent>intents;

    public static final String BASE_URL="https://image.tmdb.org/t/p/w400";

    private int index=0;
    Context context;
    public MoviesAdapter(ArrayList<Movie> movies, Context context){
        this.movies=movies;
        this.context=context;

    }

   /* public void setmNumber(int i){
        this.mNumber=i;

    }*/


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);
        MoviesViewHolder viewHolder= new MoviesViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        moviesViewHolder.bind(i);
        Movie bindMovie=movies.get(i);

        moviesViewHolder.movieName.setText(bindMovie.getOriginal_title()+" "+i);





        Picasso.with(context).load(BASE_URL+bindMovie.getPoster_path()).into(moviesViewHolder.movieThumbnail);


     //   moviesViewHolder.movieThumbnail.setOnClickListener(this);

       Intent intent= new Intent(context,DetailActivity.class);


      /*  intent.putExtra(name,bindMovie);
        intents.add(intent);*/



    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public static final String name="movie Name";
        TextView movieName;
        Intent inn;



        private ImageView movieThumbnail;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName=itemView.findViewById(R.id.movie_name);
            movieThumbnail=itemView.findViewById(R.id.movie_image);

        }

        public void bind(int i){

            movieThumbnail.setOnClickListener(this);
             inn= new Intent(context,DetailActivity.class);
            inn.putExtra(name,movies.get(i));

        }


        @Override
        public void onClick(View v) {

            v.getContext().startActivity(inn);
        }
    }
}
