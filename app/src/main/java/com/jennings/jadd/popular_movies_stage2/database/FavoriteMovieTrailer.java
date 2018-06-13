package com.jennings.jadd.popular_movies_stage2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = FavoriteMovie.class,
                                  parentColumns = "movieId",
                                  childColumns = "movieId",
                                  onDelete = ForeignKey.CASCADE),
        tableName = "favoriteMovieTrailer"
        )
public class FavoriteMovieTrailer {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private String key;
    private String name;

    @Ignore
    public FavoriteMovieTrailer(int movieId, String key, String name){
        this.movieId = movieId;
        this.key = key;
        this.name = name;
    }
    public FavoriteMovieTrailer(int id, int movieId, String author, String content){
        this.setId(id);
        this.movieId = movieId;
        this.key = key;
        this.name = name;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
