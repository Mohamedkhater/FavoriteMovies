package com.example.lifecycle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private ArrayList<String>userNames;
    private ArrayList<String>userReviews;
    public void setUserNames(ArrayList<String> userNames) {
        this.userNames = userNames;
    }



    public void setUserReviews(ArrayList<String> userReviews) {
        this.userReviews = userReviews;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reviews_list_item,viewGroup,false);

        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder reviewsViewHolder, int i) {
        reviewsViewHolder.usernameTextView.setText(userNames.get(i));
        reviewsViewHolder.userReviewTextView.setText(userReviews.get(i));



    }

    @Override
    public int getItemCount() {
        return userNames!=null? userNames.size():0;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private TextView userReviewTextView;
        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView =itemView.findViewById(R.id.reviewer_name);
             userReviewTextView=itemView.findViewById(R.id.reviewer_review);
        }
    }
}
