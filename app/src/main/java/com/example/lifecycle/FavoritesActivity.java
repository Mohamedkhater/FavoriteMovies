package com.example.lifecycle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoritesRv;
    private FavoritesAdapter favoritesAdapter;
    private Database mDb;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

          mDb= com.example.lifecycle.Database.getInstance(getApplicationContext());
          setupViewModel();


    }
    public void setupViewModel(){


                com.example.lifecycle.AppViewModel viewModel= ViewModelProviders.of(this).get(com.example.lifecycle.AppViewModel.class);
                viewModel.getMoviesEntries().observe(this, new Observer<List<com.example.lifecycle.MovieEntry>>() {
                    @Override
                    public void onChanged(@Nullable List<com.example.lifecycle.MovieEntry> movieEntries) {
                        favoritesRv=findViewById(R.id.favorites_rv);
                        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
                        favoritesRv.setLayoutManager(layoutManager);
                        favoritesAdapter=new FavoritesAdapter();
                        favoritesRv.setAdapter(favoritesAdapter);
                        Log.w(FavoritesActivity.class.getSimpleName(),"geting changes from LiveData!!!");


                        favoritesAdapter.setTasks(movieEntries);
                        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                                final   List<com.example.lifecycle.MovieEntry> movies= favoritesAdapter.getTasks();

                                com.example.lifecycle.AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        int position=viewHolder.getAdapterPosition();


                                        mDb.movieTaskDao().deleteMovie(movies.get(position));


                                    }
                                });

                            }
                        }).attachToRecyclerView(favoritesRv);


                    }
                });


    }

}
