package com.example.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
   Database mDb;
   private final String id;


    public DetailViewModelFactory(Database db,String id) {
        this.mDb=db;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mDb,id);
    }
}
