package com.example.lifecycle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity  {
    private static String TAG=MainActivity.class.getSimpleName();

    RecyclerView rv;
    ProgressBar pBar;
    private LinearLayoutManager layoutManager;

    ArrayList<Movie> mPopularList;
    private static final String TOP_RATED_MOVIES="top_rated";
    private static final String POPULARITY="popular";
    public static final String SAVED_MOVIES_TEXT="savedmovies";
    private Parcelable recyclerviewLayoutParcel;
    public static final String BASE_URL="http://api.themoviedb.org/3/movie";
    AsyncTaskListener<String> listener;
    private Database mDb;

    private FavoritesAdapter favoritesAdapter;

    MoviesAdapter myadapter;
   private GridLayoutManager layoutManager1;

    @Override
    public void onSaveInstanceState(Bundle outState ) {
        super.onSaveInstanceState(outState);
      //   list=myadapter.getMovies();
         recyclerviewLayoutParcel =layoutManager1.onSaveInstanceState();
        outState.putParcelable(SAVED_MOVIES_TEXT, recyclerviewLayoutParcel);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
             recyclerviewLayoutParcel =savedInstanceState.getParcelable(SAVED_MOVIES_TEXT);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerviewLayoutParcel !=null){

            layoutManager1.onRestoreInstanceState(recyclerviewLayoutParcel);

        }
        if (rv==findViewById(R.id.favorites_rv)){
            rv.setAdapter(favoritesAdapter);
            rv.setLayoutManager(layoutManager);
        }
        else{
            rv.setAdapter(myadapter);
            rv.setLayoutManager(layoutManager1);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb= com.example.lifecycle.Database.getInstance(getApplicationContext());

        pBar=findViewById(R.id.progress_bar);
        rv=findViewById(R.id.movies_rv);

        NetworkUtils utils= new NetworkUtils(POPULARITY);

        URL moviesUrl=utils.makeURLFromString(BASE_URL);

        layoutManager1= new GridLayoutManager(MainActivity.this,2);

        listener = new AsyncTaskListener<String>() {
          @Override
          public void onComplete(String results) {
              setMoviesInViews(results);


          }

          @Override
          public void launchTask(URL url) {
              MovieTask movieTask= new MovieTask(new ProgressBar(getApplicationContext()),rv,this);
              movieTask.execute(url);

          }
      };
      listener.launchTask(moviesUrl);








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         URL popularMoviesUrl=new NetworkUtils(POPULARITY).makeURLFromString(BASE_URL);
         URL topRatedMoviesUrl= new NetworkUtils(TOP_RATED_MOVIES).makeURLFromString(BASE_URL);


        //MovieTask movieTask=new MovieTask();

        if(item.getItemId()==R.id.sort_by_popularity){

            if (rv.getLayoutManager()!=layoutManager1)
            {
                rv.setVisibility(View.INVISIBLE);
                rv=findViewById(R.id.movies_rv);

                rv.setLayoutManager(layoutManager1);
                rv.setAdapter(myadapter);

            }

            listener.launchTask(popularMoviesUrl);


        }
        else if(item.getItemId()==R.id.sort_by_rating){
           // movieTask.execute(topRatedMoviesUrl);
            if (rv.getLayoutManager()!=layoutManager1){
                rv=findViewById(R.id.movies_rv);
                rv.setLayoutManager(layoutManager1);
                rv.setAdapter(myadapter);

            }

            listener.launchTask(topRatedMoviesUrl);
        }
        else if(item.getItemId()==R.id.favorite_list_btn){
           /* Intent intent= new Intent(this, FavoritesActivity.class);
            startActivity(intent);*/
           rv.setVisibility(View.INVISIBLE);
           setupViewModel();
        }
        return true;
    }



    public  void setMoviesInViews(String s){
        pBar.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.VISIBLE);
        //Log.d(TAG, s);
        mPopularList=NetworkUtils.parseJSON(s);
      //  Log.d(MainActivity.class.getSimpleName(),""+mPopularList.size());

        myadapter= new MoviesAdapter(mPopularList,MainActivity.this);
        //rv.setLayoutManager(layoutManager1);
        rv.setAdapter(myadapter);



        //LinearLayoutManager layoutManager= new LinearLayoutManager(this);


    }
    public void setupViewModel( ){
        rv=findViewById(R.id.favorites_rv);
        rv.setVisibility(View.VISIBLE);
        layoutManager= new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        favoritesAdapter=new FavoritesAdapter();


        com.example.lifecycle.AppViewModel viewModel= ViewModelProviders.of(this).get(com.example.lifecycle.AppViewModel.class);
        viewModel.getMoviesEntries().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<com.example.lifecycle.MovieEntry> movieEntries) {

                rv.setAdapter(favoritesAdapter);

                Log.w(FavoritesActivity.class.getSimpleName(),"geting changes from LiveData!!!");


                favoritesAdapter.setTasks(movieEntries);
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                        final List<MovieEntry> movies= favoritesAdapter.getTasks();

                        com.example.lifecycle.AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                int position=viewHolder.getAdapterPosition();


                                mDb.movieTaskDao().deleteMovie(movies.get(position));


                            }
                        });

                    }
                }).attachToRecyclerView(rv);


            }
        });


    }





}
