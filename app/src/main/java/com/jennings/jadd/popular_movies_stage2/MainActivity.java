package com.jennings.jadd.popular_movies_stage2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jennings.jadd.popular_movies_stage2.Utilities.Helpers;
import com.jennings.jadd.popular_movies_stage2.Utilities.MovieQueryTask;
import com.jennings.jadd.popular_movies_stage2.Utilities.NetworkUtils;
import com.jennings.jadd.popular_movies_stage2.database.AppDatabase;
import com.jennings.jadd.popular_movies_stage2.database.FavoriteMovie;
import com.jennings.jadd.popular_movies_stage2.models.FavMovieViewModel;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.ListItemClickListener {

    private ArrayList<Object> movieResultsJson;
    private List<FavoriteMovie> favMovieList;
    private RecyclerView movieList;
    private MoviePosterAdapter mvAdapter;
    private Context mnContext;
    private LinearLayout imgHolder;
    private AppDatabase mDb;
    private Parcelable mvListState;
    private URL movieRequest;
    private Parcelable mLayoutManagerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewModel();
        movieResultsJson = new ArrayList<Object>();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Helpers.LIFECYCLE_CALLBACKS_SORT_MODE)) {
                int allPreviousLifecycleCallbacks = savedInstanceState.getInt(Helpers.LIFECYCLE_CALLBACKS_SORT_MODE);
                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                editor.putInt("sort_order", allPreviousLifecycleCallbacks);
                editor.apply();
            }
            if (savedInstanceState.containsKey(Helpers.MOVIE_LIST_STATE_KEY)) {
                mnContext = this;
                if(movieList == null) {
                    movieList = (RecyclerView) findViewById(R.id.rv_movies);
                    movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));
                    mvAdapter = new MoviePosterAdapter(mnContext, this);
                    movieList.setHasFixedSize(true);
                    movieList.setAdapter(mvAdapter);
                }
                mLayoutManagerState = savedInstanceState.getParcelable(Helpers.MOVIE_LIST_STATE_KEY);
                movieList.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);

            }
        }
            startActivity();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int sort_order = pref.getInt("sort_order", Helpers.MOST_POP_SORT);
        outState.putInt(Helpers.LIFECYCLE_CALLBACKS_SORT_MODE, sort_order);
        Parcelable currentListSate = movieList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(Helpers.MOVIE_LIST_STATE_KEY, currentListSate);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Bundle mBundleRecyclerViewState = new Bundle();
        Parcelable listState = movieList.getLayoutManager().onSaveInstanceState();
        mvListState = listState;
        mBundleRecyclerViewState.putParcelable(Helpers.MOVIE_LIST_STATE_KEY, listState);
    }


    private void startActivity() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int current_sort_by = pref.getInt("sort_order", Helpers.MOST_POP_SORT);

        mnContext = this;
        if(movieList == null) {
            movieList = (RecyclerView) findViewById(R.id.rv_movies);
            movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));
            mvAdapter = new MoviePosterAdapter(mnContext, this);
            movieList.setHasFixedSize(true);
            movieList.setAdapter(mvAdapter);
        }
        if(current_sort_by == Helpers.SHOWFAV){

            mvAdapter.setFavoriteMovieList(favMovieList);
            mvAdapter.notifyDataSetChanged();
        }
        else if(checkInternetConnection(this) && movieRequest == null) {
            movieRequest = NetworkUtils.buildUrl(current_sort_by);
            new MovieQueryTask(movieResultsJson, mvAdapter, movieList, mLayoutManagerState ).execute(movieRequest);
        }


    }
    private void setupViewModel() {

        FavMovieViewModel viewModel = ViewModelProviders.of(this).get(FavMovieViewModel.class);
        // COMPLETED (7) Observe the LiveData object in the ViewModel
        viewModel.getFavoriteMovies().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovie> favoriteMovie) {
             //   Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                favMovieList = favoriteMovie;
                mvAdapter.setFavoriteMovieList(favoriteMovie);
                mvAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean checkInternetConnection(Context main) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        else{
            Toast.makeText(main, "You have to be connected to the internet to get most popular or top rated movies", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        movieResultsJson = mvAdapter.getMovieList();
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra("intValue",clickedItemIndex);
        intentToStartDetailActivity.putExtra("MovieObject",(MovieObject)movieResultsJson.get(clickedItemIndex));
        startActivity(intentToStartDetailActivity);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int menuItemThatWasSelected = item.getItemId();
        movieRequest = null;
        if(menuItemThatWasSelected == R.id.sort_by_popular_movie){
            Context context = MainActivity.this;
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putInt("sort_order", Helpers.MOST_POP_SORT);
            editor.apply();

            startActivity();
        }
        if(menuItemThatWasSelected == R.id.sort_by_top_rated_movie){
            Context context = MainActivity.this;
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putInt("sort_order", Helpers.TOP_RATED_SORT);
            editor.apply();
            startActivity();
        }
        if(menuItemThatWasSelected == R.id.show_favorite_movies){
            Context context = MainActivity.this;
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putInt("sort_order", Helpers.SHOWFAV);
            editor.apply();

            startActivity();
        }
        return super.onOptionsItemSelected(item);
    }

}
