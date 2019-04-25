package com.example.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private int mNumber;
    TextView movieName;

    public void setmNumber(int i){
        this.mNumber=i;

    }


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
        return mNumber;
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
            movieName.setText("movie "+i);
            message=movieName.getText().toString();
            mToast=Toast.makeText(itemView.getContext(),message,Toast.LENGTH_SHORT);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            intent= new Intent(v.getContext(),DetailActivity.class);
            intent.putExtra(MoviesAdapter.MoviesViewHolder.name,movieName.getText());
            v.getContext().startActivity(intent);

        }

     /*   @Override
        public void onClick(View v) {mToast.show();

            Intent intent= new Intent(MainActivity,DetailActivity.class);
            intent.putExtra(name,movieName.getText());




        }*/
    }
}
