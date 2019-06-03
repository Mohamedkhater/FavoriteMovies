package com.example.lifecycle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<MovieEntry> mMoviesEntries;

    public void setTasks(List<MovieEntry> names) {
        this.mMoviesEntries = names;
        notifyDataSetChanged();
    }
    public List<MovieEntry>getTasks(){
        return mMoviesEntries;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_item,viewGroup,false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int i) {
        favoritesViewHolder.favoriteName_tv.setText(mMoviesEntries.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        return mMoviesEntries.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder{
        TextView favoriteName_tv;
        final FavoritesViewHolder viewHolder=this;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteName_tv=itemView.findViewById(R.id.favorite_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"launching detailActivity",Toast.LENGTH_SHORT).show();
                   int viewholderPosition= viewHolder.getAdapterPosition();
                   MovieEntry entry=mMoviesEntries.get(viewholderPosition);
                   Movie movie = new Movie();
                   movie.setId(entry.getId());
                   movie.setPoster_path(entry.getImagePath());
                   movie.setTitle(entry.getTitle());
                   movie.setOverview(entry.getDescription());
                   movie.setVote_average(entry.getVoteAverage());
                   movie.setRelease_date(entry.getReleaseDate());
                    Intent intent = new Intent(v.getContext(),DetailActivity.class);
                    intent.putExtra(MoviesAdapter.MoviesViewHolder.NAME,movie);
                    v.getContext().startActivity(intent);



                }
            });

        }
    }
}
