package com.jennings.jadd.popular_movies_stage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoriteMovieDao {

        @Query("SELECT * FROM favoriteMovie ")
        LiveData<List<FavoriteMovie>> loadAllMovies();

        @Insert
        void insertMovie(FavoriteMovie favoriteMovie);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovie(FavoriteMovie favoriteMovie);

        @Delete
        void deleteMovie(FavoriteMovie favoriteMovie);

        @Query("SELECT * FROM favoriteMovie WHERE id = :id")
        FavoriteMovie loadMovieById(int id);

        @Query("SELECT * FROM favoriteMovie WHERE movieId = :movieId")
        FavoriteMovie loadMovieByMovieId(int movieId);

    }

