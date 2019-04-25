package com.example.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView moiveName_tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       // setTitle();
        moiveName_tv=(TextView)findViewById(R.id.movie_tv);
        Intent intent=getIntent();
        String action=intent.getStringExtra(MoviesAdapter.MoviesViewHolder.name);
        setTitle(action);

        moiveName_tv.setText(action);
    }
}
