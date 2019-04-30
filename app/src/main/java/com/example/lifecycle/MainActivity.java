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
import android.view.MotionEvent;
import android.view.View;

import java.net.URL;
import java.util.ArrayList;

import Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    MoviesAdapter myadapter;
    RecyclerView rv;
    ArrayList<Movie> mpopularList;
    String popularMovies="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=51d850fe504b9b9ebd6df40d48d30cf4";




    //  public static final int movies_size=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.movies_rv);

        MovieTask movieTask=new MovieTask();
        movieTask.execute(popularMovies);

       // myadapter.setmNumber(movies_size);







    }
    class MovieTask extends AsyncTask<String,Void,String>{
        NetworkUtils utils= new NetworkUtils();


        String topratedMovies="http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=51d850fe504b9b9ebd6df40d48d30cf4";
        ArrayList<Movie> mtopratedList;

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
