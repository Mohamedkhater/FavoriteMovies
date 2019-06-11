package com.example.lifecycle;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class trailersAdapter extends RecyclerView.Adapter<trailersAdapter.trailersViewHolder> {
    JSONArray array;
    ArrayList<Uri>moviesUris=new ArrayList<>();
    public trailersAdapter(JSONArray array){
        this.array=array;
    }


    @NonNull
    @Override
    public trailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailers,viewGroup,false);

        return new trailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull trailersViewHolder trailersViewHolder, int i) {
        String firstVideoPath = null;
        try {
            firstVideoPath = array.getJSONObject(i).getString("key");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Uri movie_uri = Uri.parse("https://www.youtube.com").buildUpon()
                .appendPath("watch").appendQueryParameter("v", firstVideoPath)
                .build();
        moviesUris.add(movie_uri);
        trailersViewHolder.trailers_tv.setText("Trailer "+Integer.valueOf(i+1));


    }


    @Override
    public int getItemCount() {
        return array.length();
    }
    public class trailersViewHolder extends RecyclerView.ViewHolder{
        private TextView trailers_tv;

        public void setTrailers_tv(TextView trailers_tv) {
            this.trailers_tv = trailers_tv;
        }

        public TextView getTrailers_tv() {
            return trailers_tv;
        }


        public trailersViewHolder(@NonNull View itemView) {
            super(itemView);
            trailers_tv=itemView.findViewById(R.id.trailer_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(Intent.ACTION_VIEW);
                    intent.setData(moviesUris.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
