package com.jennings.jadd.popular_movies_stage2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favoriteMovie")
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    private  int id;
    private int movieId;
    private String movieName;

    @Ignore
    public FavoriteMovie(int movieId, String movieName){
        this.movieId = movieId;
        this.movieName = movieName;

    }
    public FavoriteMovie(int id, int movieId, String movieName){
        this.setId(id);
        this.movieId = movieId;
        this.movieName = movieName;

    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
