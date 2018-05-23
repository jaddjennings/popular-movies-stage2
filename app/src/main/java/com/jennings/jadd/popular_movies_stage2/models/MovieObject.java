package com.jennings.jadd.popular_movies_stage2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieObject implements Parcelable{


    private int vote_count;
    private int id;
    private double vote_average;
    private String title;
    private double popularity;
    private String posterPath;
    private String overview;
    private String release_date;


    public MovieObject(){
    }
    public MovieObject(int id, Double vote_average, String title, Double popularity, String poster_path, String overview, String release_date) {
        this.id =id;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.overview = overview;
        this.release_date = release_date;
        this.posterPath = poster_path;
    }
    private MovieObject(Parcel in) {
        this.id = in.readInt();
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
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.vote_average);
        dest.writeDouble(this.popularity);

    }
}
