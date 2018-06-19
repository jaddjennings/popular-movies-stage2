package com.jennings.jadd.popular_movies_stage2.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.jennings.jadd.popular_movies_stage2.database.AppDatabase;
import com.jennings.jadd.popular_movies_stage2.database.FavoriteMovie;

import java.util.List;


public class FavMovieViewModel extends AndroidViewModel {

    private static final String TAG = FavMovieViewModel.class.getSimpleName();

    private LiveData<List<FavoriteMovie>> favoriteMovies;

    public FavMovieViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favoriteMovies = database.favoriteMovieDao().loadAllMovies();
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }
}
