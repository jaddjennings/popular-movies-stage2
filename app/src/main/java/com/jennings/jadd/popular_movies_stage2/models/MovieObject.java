package com.jennings.jadd.popular_movies_stage2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieObject implements Parcelable{


    private int vote_count;
    private int id;
    private Boolean video;
    private double vote_average;
    private String title;
    private double popularity;
    private String posterPath;
    private String original_language;
    private String original_title;
    private ArrayList<Object> genre_ids;
    private String backdrop_path;
    private Boolean adult;
    private String overview;
    private String release_date;

    public MovieObject(){

    }
    public MovieObject(Double vote_average, String title, Double popularity, String poster_path, String overview, String release_date) {
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.overview = overview;
        this.release_date = release_date;
        this.posterPath = poster_path;
    }
    private MovieObject(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.posterPath = in.readString();
        this.vote_average = in.readDouble();
        this.popularity = in.readDouble();

    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public ArrayList<Object> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Object> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
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

    public static final Parcelable.Creator<MovieObject> CREATOR
     = new Parcelable.Creator<MovieObject>() {

              public  MovieObject createFromParcel(Parcel in) {
                    return new MovieObject(in);
                }

               public     MovieObject[] newArray(int size) {
                    return new MovieObject[size];
                }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.vote_average);
        dest.writeDouble(this.popularity);

    }
}
