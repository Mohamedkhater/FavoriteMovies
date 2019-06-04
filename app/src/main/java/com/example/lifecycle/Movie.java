package com.example.lifecycle;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.method.MovementMethod;

import java.util.ArrayList;

public class Movie implements Parcelable {
    private String id;
    private double mvoteAverage;
    private String title;
    private String mReleaseDate;
    private String backdrop_path;

    public Movie(){

    }

    public Movie(Parcel in) {
        id = in.readString();
        mvoteAverage = in.readDouble();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        original_title = in.readString();
        gender_ids = in.createIntArray();
        mReleaseDate=in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
      //  in.readTypedList(movies,Movie.CREATOR);
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
        return mvoteAverage;
    }

    public void setVote_average(double vote_average) {
        this.mvoteAverage = vote_average;
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
        dest.writeDouble(mvoteAverage);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeIntArray(gender_ids);
        dest.writeString(mReleaseDate);

        dest.writeByte((byte) (adult? 1:0));
        dest.writeString(overview);
      //  dest.writeTypedList(movies);

    }

    public String getRelease_date() {
        return mReleaseDate;
    }

    public void setRelease_date(String release_date) {
        this.mReleaseDate = release_date;
    }
}
