package com.jennings.jadd.popular_movies_stage2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jennings.jadd.popular_movies_stage2.Utilities.JsonUtils;
import com.jennings.jadd.popular_movies_stage2.Utilities.MovieQueryTask;
import com.jennings.jadd.popular_movies_stage2.Utilities.NetworkUtils;
import com.jennings.jadd.popular_movies_stage2.database.AppDatabase;
import com.jennings.jadd.popular_movies_stage2.database.FavoriteMovie;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.ListItemClickListener {

    public static final int SHOWFAV = 3;
    public static final int TOP_RATED_SORT = 2;
    public static final int MOST_POP_SORT = 1;
    private static final String LIFECYCLE_CALLBACKS_SORT_MODE = "sort_mode";
    private static final String MOVIE_LIST_STATE_KEY = "movie_list_state_key";
    private ArrayList<Object> movieResultsJson;
    private ArrayList<Object> favMovieList;
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
        loadFavoriteMovies();
        movieResultsJson = new ArrayList<Object>();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_SORT_MODE)) {
                int allPreviousLifecycleCallbacks = savedInstanceState.getInt(LIFECYCLE_CALLBACKS_SORT_MODE);
                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                editor.putInt("sort_order", allPreviousLifecycleCallbacks);
                editor.apply();
            }
            if (savedInstanceState.containsKey(MOVIE_LIST_STATE_KEY)) {
                mnContext = this;
                if(movieList == null) {
                    movieList = (RecyclerView) findViewById(R.id.rv_movies);
                    movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));
                    mvAdapter = new MoviePosterAdapter(mnContext, this);
                    movieList.setHasFixedSize(true);
                    movieList.setAdapter(mvAdapter);
                }
                mLayoutManagerState = savedInstanceState.getParcelable(MOVIE_LIST_STATE_KEY);
                movieList.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);

            }
        }
            startActivity();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int sort_order = pref.getInt("sort_order", MOST_POP_SORT);
        outState.putInt(LIFECYCLE_CALLBACKS_SORT_MODE, sort_order);
        Parcelable currentListSate = movieList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(MOVIE_LIST_STATE_KEY, currentListSate);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Bundle mBundleRecyclerViewState = new Bundle();
        Parcelable listState = movieList.getLayoutManager().onSaveInstanceState();
        mvListState = listState;
        mBundleRecyclerViewState.putParcelable(MOVIE_LIST_STATE_KEY, listState);
    }


    private void startActivity() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int current_sort_by = pref.getInt("sort_order", MOST_POP_SORT);

        mnContext = this;
        if(movieList == null) {
            movieList = (RecyclerView) findViewById(R.id.rv_movies);
            movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));
            mvAdapter = new MoviePosterAdapter(mnContext, this);
            movieList.setHasFixedSize(true);
            movieList.setAdapter(mvAdapter);
        }
        if(current_sort_by == SHOWFAV){

            mvAdapter.setMovieList(favMovieList);
            mvAdapter.notifyDataSetChanged();
        }
        else if(checkInternetConnection(this) && movieRequest == null) {
            movieRequest = NetworkUtils.buildUrl(current_sort_by);
            new MovieQueryTask(movieResultsJson, mvAdapter, movieList, mLayoutManagerState ).execute(movieRequest);
        }


    }

    private void loadFavoriteMovies() {

        mDb = AppDatabase.getInstance(getApplicationContext());
        favMovieList = new ArrayList<Object>();
        //LiveData<List<FavoriteMovie>> favMovies = mDb.favoriteMovieDao().loadAllMovies();
        List<FavoriteMovie> favMovies = mDb.favoriteMovieDao().loadAllMoviesAlt();
        for (int i = 0; i<favMovies.size();i++) {

            MovieObject fvMv =  favMovies.get(i).getMovieObject();
            fvMv.setPosterImage(favMovies.get(i).getPoster());
            favMovieList.add(fvMv);
        }
       // favMovies.observe(this, new Observer<List<FavoriteMovie>>(){
       /** favMovies.observeForever(new Observer<List<FavoriteMovie>>(){
            @Override
            public void onChanged(@Nullable List<FavoriteMovie> favoriteMovie) {
                for (int i = 0; i<favoriteMovie.size();i++) {

                    MovieObject fvMv =  favoriteMovie.get(i).getMovieObject();
                    fvMv.setPosterImage(favoriteMovie.get(i).getPoster());
                    favMovieList.add(fvMv);
                }
            }
        });**/
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
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
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
            editor.putInt("sort_order", MOST_POP_SORT);
            editor.apply();

            startActivity();
        }
        if(menuItemThatWasSelected == R.id.sort_by_top_rated_movie){
            Context context = MainActivity.this;
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putInt("sort_order", TOP_RATED_SORT);
            editor.apply();
            startActivity();
        }
        if(menuItemThatWasSelected == R.id.show_favorite_movies){
            Context context = MainActivity.this;
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putInt("sort_order", SHOWFAV);
            editor.apply();

            startActivity();
        }
        return super.onOptionsItemSelected(item);
    }

}
