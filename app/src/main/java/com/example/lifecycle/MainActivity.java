package com.example.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    MoviesAdapter myadapter;
    RecyclerView rv;
    public static final int movies_size=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.movies_rv);
        myadapter= new MoviesAdapter();
        myadapter.setmNumber(movies_size);
        rv.setAdapter(myadapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

    }
}
