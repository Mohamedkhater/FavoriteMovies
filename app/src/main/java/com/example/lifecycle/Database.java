package com.example.lifecycle;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@android.arch.persistence.room.Database(entities = {MovieEntry.class}, version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static   Database sInstance=null;
    private static final String TAG=Database.class.getSimpleName();
    private static final String DATABASE_NAME="movieslist";
    private static final Object LOCK=new Object();
    public static Database getInstance(Context context){
        if (sInstance==null){
            synchronized (LOCK){
                sInstance= Room.databaseBuilder(context.getApplicationContext(),Database.class,Database.DATABASE_NAME).build();
                Log.d(TAG,"creating a database instance!!!!");

            }

        }
        Log.d(TAG,"Getting the already existing database instance!!");
        return sInstance;


    }
    public abstract MovieTaskDao movieTaskDao();

}
