package com.example.lifecycle;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

import Utils.NetworkUtils;




class MovieTask extends AsyncTask<URL,Void,String> {
        ProgressBar pBar;
        RecyclerView rv;
       // private AsyncTaskHelper helper;

        //NetworkUtils utils= new NetworkUtils();
        private AsyncTaskListener taskListener;
        public MovieTask(ProgressBar pBar,RecyclerView rv,AsyncTaskListener listener){
            this.pBar=pBar;
            this.rv=rv;
            this.taskListener=listener;

        }
        public MovieTask(AsyncTaskListener listener){
            this.taskListener= listener;
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
                taskListener.onComplete(s);
            }

            taskListener.onComplete(s);


        }
    }

