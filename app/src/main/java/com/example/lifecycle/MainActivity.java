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

public class MainActivity extends AppCompatActivity {
    MoviesAdapter myadapter;
    RecyclerView rv;
    ProgressBar pbar;
    ArrayList<Movie> mpopularList;
    String popularMovies="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=51d850fe504b9b9ebd6df40d48d30cf4";

    String topratedMovies="http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=51d850fe504b9b9ebd6df40d48d30cf4";
    String defaultquery=popularMovies;
    public static final String API_KEY="api_key";
    public static final String api_key_value="51d850fe504b9b9ebd6df40d48d30cf4";



    //  public static final int movies_size=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbar=findViewById(R.id.progress_bar);
        rv=findViewById(R.id.movies_rv);
        MovieTask movieTask= new MovieTask();
        movieTask.execute(defaultquery);



       // myadapter.setmNumber(movies_size);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieTask movieTask=new MovieTask();

        if(item.getItemId()==R.id.sort_by_popularity){
            movieTask.execute(popularMovies);

        }
        else if(item.getItemId()==R.id.sort_by_rating){
            movieTask.execute(topratedMovies);
        }
        return true;
    }

    class MovieTask extends AsyncTask<String,Void,String>{
        NetworkUtils utils= new NetworkUtils();


        ArrayList<Movie> mtopratedList;

        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
            rv.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {


            mtopratedList= new ArrayList<>();
            String data;
            try{
                
                data= utils.fetchData(urls[0]);

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
            if (s==null || s==""){
                return;
            }
            pbar.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);


            mpopularList=utils.parseJSON(s);
            Log.d(MainActivity.class.getSimpleName(),""+mpopularList.size());
            myadapter= new MoviesAdapter(mpopularList,MainActivity.this);
            rv.setAdapter(myadapter);

            //LinearLayoutManager layoutManager= new LinearLayoutManager(this);
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
            rv.setLayoutManager(layoutManager);

          /*  for (int i=0;i<mpopularList.size();i++){
            }*/



            // Log.d(MainActivity.class.getSimpleName(),"popularlist size = "+mpopularList.size());
           // Log.d(MainActivity.class.getSimpleName(),s);

        }
    }
}
