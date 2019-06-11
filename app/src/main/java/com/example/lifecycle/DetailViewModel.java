package com.example.lifecycle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {
    public LiveData<MovieEntry> getEntry() {
        return entry;
    }

    private LiveData<MovieEntry> entry;
    public DetailViewModel(Database db, String id){
        entry=db.movieTaskDao().loadMovieById(id);

    }

}
