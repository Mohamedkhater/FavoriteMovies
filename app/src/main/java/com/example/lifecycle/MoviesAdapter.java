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
    TextView movieName;
    ArrayList<Movie>movies;
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


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public static final String name="movie Name";
        private Toast mToast;
        private String message;
        public Intent intent;


        private ImageView movieThumbnail;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName=itemView.findViewById(R.id.movie_name);
            movieThumbnail=itemView.findViewById(R.id.movie_image);

        }

        public void bind(int i){

            movieName.setText(movies.get(i).getTitle());
            message=movieName.getText().toString(); 





            Picasso.with(context).load(BASE_URL+movies.get(i).getPoster_path()).into(movieThumbnail);


            movieThumbnail.setOnClickListener(this);
            intent= new Intent(context,DetailActivity.class);

            intent.putExtra(MoviesAdapter.MoviesViewHolder.name,movies.get(i));


        }

        @Override
        public void onClick(View v) {

            v.getContext().startActivity(intent);

        }


    }
}
