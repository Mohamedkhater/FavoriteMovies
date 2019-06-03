package com.example.lifecycle;

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
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;

import Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity  {
    private static String TAG=MainActivity.class.getSimpleName();

    RecyclerView rv;
    ProgressBar pBar;

    ArrayList<Movie> mPopularList;
    private static final String TOP_RATED_MOVIES="top_rated";
    private static final String POPULARITY="popular";
    public static final String SAVED_MOVIES_TEXT="savedmovies";
    private Parcelable recyclerviewLayoutParcel;
    public static final String BASE_URL="http://api.themoviedb.org/3/movie";
    AsyncTaskListener<String> listener;
    MoviesAdapter myadapter;
   private GridLayoutManager layoutManager;

    @Override
    public void onSaveInstanceState(Bundle outState ) {
        super.onSaveInstanceState(outState);
      //   list=myadapter.getMovies();
         recyclerviewLayoutParcel =layoutManager.onSaveInstanceState();
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

            layoutManager.onRestoreInstanceState(recyclerviewLayoutParcel);

        }
        rv.setLayoutManager(layoutManager);// for some reason it doesn't work when disconnecting the wifi ,what should i do?


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pBar=findViewById(R.id.progress_bar);
        rv=findViewById(R.id.movies_rv);

        NetworkUtils utils= new NetworkUtils(POPULARITY);

        URL moviesUrl=utils.makeURLFromString(BASE_URL);

        layoutManager= new GridLayoutManager(MainActivity.this,2);

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
            listener.launchTask(popularMoviesUrl);


        }
        else if(item.getItemId()==R.id.sort_by_rating){
           // movieTask.execute(topRatedMoviesUrl);
            listener.launchTask(topRatedMoviesUrl);
        }
        else if(item.getItemId()==R.id.favorite_list_btn){
            Intent intent= new Intent(this, FavoritesActivity.class);
            startActivity(intent);
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
        rv.setAdapter(myadapter);


        //LinearLayoutManager layoutManager= new LinearLayoutManager(this);


    }




}
