package com.example.lifecycle;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

@Entity(tableName = "Movie")
public class MovieEntry {
    @PrimaryKey @NonNull
    public String id;

    public String title;
    public String description;
    public String imagePath;
    public double voteAverage;

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
}
