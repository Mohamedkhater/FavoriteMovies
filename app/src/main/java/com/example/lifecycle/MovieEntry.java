package com.example.lifecycle;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "Movie")
public class MovieEntry implements Parcelable {
    @PrimaryKey @NonNull
    public String id;

    public String title;
    public String description;
    public String imagePath;
    public double voteAverage;

    protected MovieEntry(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        imagePath = in.readString();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
    }

    public static final Creator<MovieEntry> CREATOR = new Creator<MovieEntry>() {
        @Override
        public MovieEntry createFromParcel(Parcel in) {
            return new MovieEntry(in);
        }

        @Override
        public MovieEntry[] newArray(int size) {
            return new MovieEntry[size];
        }
    };

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String releaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }



    public MovieEntry(String id, String title, String description, String imagePath, double voteAverage,String releaseDate){
        this.id=id;
        this.title=title;
        this.description=description;
        this.imagePath=imagePath;
        this.voteAverage=voteAverage;
        this.releaseDate=releaseDate;
    }
    @Ignore
    public MovieEntry( String title, String description, String imagePath, double voteAverage,String releaseDate){

        this.title=title;
        this.description=description;
        this.imagePath=imagePath;
        this.voteAverage=voteAverage;
        this.releaseDate=releaseDate;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imagePath);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
    }
}
