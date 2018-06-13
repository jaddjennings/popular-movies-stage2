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
public interface FavoriteMovieReviewDao {

        @Query("SELECT * FROM favoriteMovieReview ")
        LiveData<List<FavoriteMovieReview>> loadAllMovieReviews();

        @Insert
        void insertMovieReview(FavoriteMovieReview favoriteMovieReview);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovieReview(FavoriteMovieReview favoriteMovieReview);

        @Delete
        void deleteMovieReview(FavoriteMovieReview favoriteMovieReview);

        @Query("SELECT * FROM favoriteMovieReview WHERE id = :id")
        FavoriteMovieReview loadMovieReviewById(int id);

        @Query("SELECT * FROM favoriteMovieReview WHERE movieId = :movieId")
        FavoriteMovieReview loadMovieReviewByMovieId(int movieId);

    }

