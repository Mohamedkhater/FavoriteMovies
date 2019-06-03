package com.example.lifecycle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import Utils.NetworkUtils;


    class DetailTask extends AsyncTask<URL,Void,String> {


        private Movie movie;
        private TextView textView;
        private Context context;
        public DetailTask(Movie movie  , TextView tv, Context context){
            this.movie=movie;
            this.textView=tv;
            this.context=context;


        }

        @Override
        protected String doInBackground(URL... urls) {



            URL url=null;

            {
                try {
                    url =urls[0];

                    String jsonResponse = NetworkUtils.fetchData(url);

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(jsonResponse);
                        JSONArray jsonArray = obj.getJSONArray("results");
                        String firstVideoPath = jsonArray.getJSONObject(0).getString("id");
                        return firstVideoPath;


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            final Uri movie_uri = Uri.parse("https://www.youtube.com").buildUpon().appendPath("watch").appendQueryParameter("v", s).build();

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (Intent.ACTION_VIEW);
                    intent.setData(movie_uri);
                    v.getContext().startActivity(intent);
                }
            });




        }






    }

