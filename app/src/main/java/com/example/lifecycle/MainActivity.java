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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    public static com.example.lifecycle.AppViewModel viewModel;
    public int choice=0;
    private static final String TOP_RATED_MOVIES="top_rated";
    private static final String POPULARITY="popular";
    public static final String SAVED_MOVIES_TEXT="savedmovies";
    private Parcelable recyclerviewLayoutParcel;
    public static final String BASE_URL="http://api.themoviedb.org/3/movie";
    AsyncTaskListener<String> listener;
    public static Database mDb;
    URL topRatedMoviesUrl;
    public URL moviesUrl;
    private FavoritesAdapter favoritesAdapter;
    private static final String RECYCLERVIEW_ID="recyclerviewId";
    MoviesAdapter myadapter;
   private GridLayoutManager layoutManager1;
   private static final  String LIST_STATE="liststate";
   private static final String FAVORITE_ITEMS="favoriteitems";
   ArrayList<Movie>movies=new ArrayList<>();
   ArrayList<MovieEntry>favoriteMovies=new ArrayList<>();
   private int calculateBestColSpan(){
       int posterWidth=540;
       Display display= getWindowManager().getDefaultDisplay();
       DisplayMetrics metrics=new DisplayMetrics();
       display.getMetrics(metrics);
       float screenWidth=metrics.widthPixels;
       return Math.round(screenWidth/posterWidth);
   }


    @Override
    public void onSaveInstanceState(Bundle outState ) {
        super.onSaveInstanceState(outState);
        recyclerviewLayoutParcel =rv.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVED_MOVIES_TEXT, recyclerviewLayoutParcel);
            outState.putParcelableArrayList(LIST_STATE,movies);
            outState.putInt(RECYCLERVIEW_ID,choice);
            outState.putParcelableArrayList(FAVORITE_ITEMS,favoriteMovies);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){

             recyclerviewLayoutParcel =savedInstanceState.getParcelable(SAVED_MOVIES_TEXT);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (choice==R.id.favorite_list_btn){
            rv.setAdapter(favoritesAdapter);
            rv.setLayoutManager(layoutManager1);
            setupViewModel();
        }
        else{

            rv.setAdapter(myadapter);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pBar=findViewById(R.id.progress_bar);

        topRatedMoviesUrl= new NetworkUtils(TOP_RATED_MOVIES).makeURLFromString(BASE_URL);

        moviesUrl=new NetworkUtils(POPULARITY).makeURLFromString(BASE_URL);
        rv=findViewById(R.id.movies_rv);
        layoutManager1= new GridLayoutManager(MainActivity.this,calculateBestColSpan());
        layoutManager= new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager1);



        mDb= com.example.lifecycle.Database.getInstance(getApplicationContext());
        listener = new AsyncTaskListener<String>() {
            @Override
            public void onComplete(String results) {
                if (results==null || results==""){
                    Toast.makeText(MainActivity.this,"PLease insert your TMBD api key",Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this,"NO INTERNET CONNECTION, PLEASE TRY AGAIN LATER",Toast.LENGTH_LONG).show();

                }


                else
                    setMoviesInViews(results);

            }

            @Override
            public void launchTask(URL url) {
                MovieTask movieTask= new MovieTask(new ProgressBar(getApplicationContext()),rv,this);
                movieTask.execute(url);

            }
        };


        if(savedInstanceState!=null){
            choice=savedInstanceState.getInt(RECYCLERVIEW_ID);


                if(choice==R.id.favorite_list_btn){
                    favoriteMovies=savedInstanceState.getParcelableArrayList(FAVORITE_ITEMS);
                    favoritesAdapter=new FavoritesAdapter(this,favoriteMovies);
                }
                else{
                    movies=savedInstanceState.getParcelableArrayList(LIST_STATE);
                    myadapter= new MoviesAdapter(movies,this);
                }

                recyclerviewLayoutParcel=savedInstanceState.getParcelable(SAVED_MOVIES_TEXT);
                if(recyclerviewLayoutParcel!=null)
                    rv.getLayoutManager().onRestoreInstanceState(recyclerviewLayoutParcel);

        }
        else{
            rv=findViewById(R.id.movies_rv);
            rv.setLayoutManager(layoutManager1);

            listener.launchTask(moviesUrl);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.sort_by_popularity){
            choice=R.id.sort_by_popularity;

            if (rv.getLayoutManager()!=layoutManager1)
            {
                rv.setVisibility(View.INVISIBLE);
                rv=findViewById(R.id.movies_rv);

                rv.setLayoutManager(layoutManager1);
                rv.setAdapter(myadapter);
                rv.setVisibility(View.VISIBLE);

            }

            listener.launchTask(moviesUrl);


        }
        else if(item.getItemId()==R.id.sort_by_rating){
            choice=R.id.sort_by_rating;
           // movieTask.execute(topRatedMoviesUrl);
            if (rv.getLayoutManager()!=layoutManager1){
                rv.setVisibility(View.INVISIBLE);
                rv=findViewById(R.id.movies_rv);
                rv.setLayoutManager(layoutManager1);
                rv.setAdapter(myadapter);
                rv.setVisibility(View.VISIBLE);

            }

            listener.launchTask(topRatedMoviesUrl);
        }
        else if(item.getItemId()==R.id.favorite_list_btn){
            choice=R.id.favorite_list_btn;

           rv.setVisibility(View.INVISIBLE);
           setupViewModel();
        }
        return true;
    }



    public  void setMoviesInViews(String s){
        pBar.setVisibility(View.INVISIBLE);
        //Log.d(TAG, s);
        mPopularList=NetworkUtils.parseJSON(s);
      //  Log.d(MainActivity.class.getSimpleName(),""+mPopularList.size());
        if(mPopularList!=null)
        {
            movies.clear();
            movies.addAll(mPopularList);
        }


        myadapter= new MoviesAdapter(mPopularList,MainActivity.this);
        rv.setLayoutManager(layoutManager1);
        rv.setAdapter(myadapter);
        rv.setVisibility(View.VISIBLE);






    }
    public void setupViewModel( ){
        //rv=findViewById(R.id.favorites_rv);
        rv.setVisibility(View.VISIBLE);
       // rv.setLayoutManager(layoutManager);
        favoritesAdapter=new FavoritesAdapter(this,favoriteMovies);

        rv.setAdapter(favoritesAdapter);
        rv.setLayoutManager(layoutManager1);

        viewModel= ViewModelProviders.of(this).get(com.example.lifecycle.AppViewModel.class);
        viewModel.getMoviesEntries().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<com.example.lifecycle.MovieEntry> movieEntries) {




                favoritesAdapter.setTasks(movieEntries);
                favoriteMovies.clear();
                if (movieEntries!=null){
                    favoriteMovies.clear();
                    favoriteMovies.addAll(movieEntries);

                }
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
                                favoriteMovies.remove(position);


                            }
                        });

                    }
                }).attachToRecyclerView(rv);


            }
        });


    }





}
