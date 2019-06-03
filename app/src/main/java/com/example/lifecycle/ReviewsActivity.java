package com.example.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
    public static String REVIEWS_TEXT="reviews";
    private String response;
    ArrayList<String>users;
    ArrayList<String>usersReviews;
    RecyclerView reviewsRecyclerView;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Intent intent= getIntent();
        users=new ArrayList<String>();
        usersReviews=new ArrayList<String>();
        if (intent.hasExtra(REVIEWS_TEXT)){
          response=  intent.getStringExtra(REVIEWS_TEXT);
            Log.d(ReviewsActivity.class.getSimpleName(),response);

            try {
                JSONObject jsonObject= new JSONObject(response);
                JSONArray jsonArray= jsonObject.getJSONArray("results");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    String userName=jsonObject1.getString("author");

                    String userReview=jsonObject1.getString("content");
                    users.add(userName);
                    usersReviews.add(userReview);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ReviewsAdapter adapter= new ReviewsAdapter();
        adapter.setUserNames(users);
        adapter.setUserReviews(usersReviews);
        reviewsRecyclerView=findViewById(R.id.reviews_rv);
        reviewsRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(layoutManager);
    }
}
