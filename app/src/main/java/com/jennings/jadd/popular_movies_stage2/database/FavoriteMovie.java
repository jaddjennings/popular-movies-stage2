package com.jennings.jadd.popular_movies_stage2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.jennings.jadd.popular_movies_stage2.models.MovieObject;

@Entity(tableName = "favoriteMovie")
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private String movieName;
    private double vote_average;
    private String overview;
    private String release_date;
    private byte[] poster;



    @Ignore
    public FavoriteMovie(int movieId, String movieName, double vote_average, String overview, String release_date){
        this.movieId = movieId;
        this.movieName = movieName;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;


    }

    @Ignore
    public FavoriteMovie(MovieObject m){
        this.movieId = m.getId();
        this.movieName = m.getTitle();
        this.vote_average = m.getVote_average();
        this.overview = m.getOverview();
        this.release_date = m.getRelease_date();

    }
    public FavoriteMovie(int id, int movieId, String movieName, double vote_average, String overview, String release_date, byte[] poster){
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.poster = poster;

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

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public MovieObject getMovieObject() {
        MovieObject movieObject = new MovieObject();
        movieObject.setId(movieId);
        movieObject.setTitle(movieName);
        movieObject.setVote_average(vote_average);
        movieObject.setOverview(overview);
        movieObject.setRelease_date(release_date);
        return movieObject;
    }


    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
}
