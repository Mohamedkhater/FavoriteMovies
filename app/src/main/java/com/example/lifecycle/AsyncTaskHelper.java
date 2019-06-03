package com.example.lifecycle;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.net.URL;

public class AsyncTaskHelper implements AsyncTaskListener<String> {
    ProgressBar pb;
    RecyclerView rv;

    public AsyncTaskHelper(){

    }
    public AsyncTaskHelper(ProgressBar progressBar, RecyclerView recyclerView){
        this.rv=recyclerView;
        this.pb=progressBar;

    }

    @Override
    public void launchTask(URL url){
         MovieTask movieTask=new MovieTask(pb,rv,this);
         movieTask.execute(url);

    }
    @Override
    public void onComplete(String results) {

    }
}
