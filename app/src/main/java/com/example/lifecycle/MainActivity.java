package com.example.lifecycle;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;

import Utils.NetworkUtils;
import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    ProgressBar pBar;
    ArrayList<Movie> mPopularList;
    private static final String TOP_RATED_MOVIES="top_rated";
    private static final String POPULARITY="popular";

    public static final String BASE_URL="http://api.themoviedb.org/3/movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pBar=findViewById(R.id.progress_bar);
        rv=findViewById(R.id.movies_rv);
        MovieTask movieTask= new MovieTask();
        //NetworkUtils utils= new NetworkUtils();
        NetworkUtils utils= new NetworkUtils(POPULARITY);

        URL url=utils.makeURLFromString(BASE_URL);
        movieTask.execute(url);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieTask movieTask=new MovieTask();
        URL popularMoviesUrl=new NetworkUtils(POPULARITY).makeURLFromString(BASE_URL);
        URL topRatedMoviesUrl= new NetworkUtils(TOP_RATED_MOVIES).makeURLFromString(BASE_URL);

        if(item.getItemId()==R.id.sort_by_popularity){
            movieTask.execute(popularMoviesUrl);

        }
        else if(item.getItemId()==R.id.sort_by_rating){
            movieTask.execute(topRatedMoviesUrl);
        }
        return true;
    }

    class MovieTask extends AsyncTask<URL,Void,String>{
        //NetworkUtils utils= new NetworkUtils();




        @Override
        protected void onPreExecute() {
            pBar.setVisibility(View.VISIBLE);
            rv.setVisibility(View.INVISIBLE);

        }

        @Override
        protected String doInBackground(URL...urls) {


            String data;
            try{

                data= NetworkUtils.fetchData(urls[0]);

                return data;
            }
            catch (Exception e){
                Log.d(MainActivity.class.getSimpleName(),"can't return JSON format");
                e.printStackTrace();
            }


            return null;
        }

       // @Override
        protected void onPostExecute(String s) {
            if (s==null || s.equals("")){
                return;
            }
            pBar.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);


            mPopularList=NetworkUtils.parseJSON(s);
            Log.d(MainActivity.class.getSimpleName(),""+mPopularList.size());
            MoviesAdapter myadapter;

            myadapter= new MoviesAdapter(mPopularList,MainActivity.this);
            rv.setAdapter(myadapter);

            //LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
            rv.setLayoutManager(layoutManager);


        }
    }
}
