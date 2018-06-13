package com.jennings.jadd.popular_movies_stage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieTrailerDao {

        @Query("SELECT * FROM favoriteMovieTrailer ")
        LiveData<List<FavoriteMovieTrailer>> loadAllMovieTrailers();

        @Insert
        void insertMovieTrailer(FavoriteMovieTrailer favoriteMovieTrailer);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovieTrailer(FavoriteMovieTrailer favoriteMovieTrailer);

        @Delete
        void deleteMovieTrailer(FavoriteMovieTrailer favoriteMovieTrailer);

        @Query("SELECT * FROM favoriteMovieTrailer WHERE id = :id")
        FavoriteMovieTrailer loadMovieTrailerById(int id);
    
        @Query("SELECT * FROM favoriteMovieTrailer WHERE movieId = :movieId")
        FavoriteMovieTrailer loadMovieTrailerByMovieId(int movieId);

    }

