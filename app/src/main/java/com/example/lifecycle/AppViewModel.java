package com.example.lifecycle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class AppViewModel extends AndroidViewModel {


    private LiveData<List<MovieEntry>> moviesEntries;
    public AppViewModel(@NonNull Application application) {
        super(application);
        Database database=Database.getInstance(this.getApplication());
        moviesEntries=database.movieTaskDao().loadAllMovies();
        Log.w(AppViewModel.class.getSimpleName(),"loading tasks from the database!!!");
    }
    public LiveData<List<MovieEntry>> getMoviesEntries() {
        return moviesEntries;
    }
}
