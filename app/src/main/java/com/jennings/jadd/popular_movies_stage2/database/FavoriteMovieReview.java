package com.jennings.jadd.popular_movies_stage2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = FavoriteMovie.class,
                                  parentColumns = "movieId",
                                  childColumns = "movieId",
                                  onDelete = ForeignKey.CASCADE),
        tableName = "favoriteMovieReview"
        )
public class FavoriteMovieReview {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private String author;
    private String content;

    @Ignore
    public FavoriteMovieReview(int movieId, String author, String content){
        this.movieId = movieId;
        this.author = author;
        this.content = content;
    }
    public FavoriteMovieReview(int id, int movieId, String author, String content){
        this.setId(id);
        this.movieId = movieId;
        this.author = author;
        this.content = content;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
