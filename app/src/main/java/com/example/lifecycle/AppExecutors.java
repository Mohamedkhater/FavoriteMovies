package com.example.lifecycle;

import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

import android.os.Handler;

public class AppExecutors {
    public static final Object LOCK=new Object();
    private static AppExecutors INSTANCE=null;
    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThreadIO() {
        return mainThreadIO;
    }

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThreadIO;
    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThreadIO){
        this.diskIO=diskIO;
        this.networkIO=networkIO;
        this.mainThreadIO=mainThreadIO;
    }
    public static AppExecutors getInstance(){
        if (INSTANCE==null) {


            synchronized (LOCK) {
                INSTANCE = new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());

            }
        }
        return INSTANCE;

    }
    public static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler= new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);

        }
    }

}
