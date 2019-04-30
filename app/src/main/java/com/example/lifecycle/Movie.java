package com.example.lifecycle;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String id;
    private double vote_average;
    private boolean video;
    private String title;
    private String backdrop_path;
    public Movie(){

    }

    public Movie(Parcel in) {
        id = in.readString();
        vote_average = in.readDouble();
        video = in.readByte() != 0;
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        original_title = in.readString();
        gender_ids = in.createIntArray();
        adult = in.readByte() != 0;
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int[] getGender_ids() {
        return gender_ids;
    }

    public void setGender_ids(int[] gender_ids) {
        this.gender_ids = gender_ids;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private double popularity;
    private String poster_path;
    private String original_title;
    private int []gender_ids;
    private boolean adult;
    private String overview;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(vote_average);
        dest.writeByte((byte)(video?1:0));
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeIntArray(gender_ids);

        dest.writeByte((byte) (adult? 1:0));
        dest.writeString(overview);

    }
}
