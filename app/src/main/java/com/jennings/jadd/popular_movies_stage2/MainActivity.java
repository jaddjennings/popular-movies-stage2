package com.jennings.jadd.popular_movies_stage2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
    private ArrayList<Object> movieResultsJson;
    private ArrayList<Object> favMovieList;
    private RecyclerView movieList;
    private MoviePosterAdapter mvAdapter;
    private Context mnContext;
    private LinearLayout imgHolder;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        movieResultsJson = new ArrayList<Object>();
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        loadFavoriteMovies();
        startActivity(MOST_POP_SORT);
    }

    private void startActivity(int currentSortBy) {


        mnContext = this;
        movieList = (RecyclerView) findViewById(R.id.rv_movies);
        movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));
        mvAdapter = new MoviePosterAdapter(mnContext, this);
        movieList.setHasFixedSize(true);
        movieList.setAdapter(mvAdapter);
        if(currentSortBy == SHOWFAV){

            mvAdapter.setMovieList(favMovieList);
            mvAdapter.notifyDataSetChanged();
        }
        else if(checkInternetConnection(this)) {
            URL movieRequest= NetworkUtils.buildUrl(currentSortBy);
            new MovieQueryTask(movieResultsJson, mvAdapter).execute(movieRequest);
        }

    }

    private void loadFavoriteMovies() {

        mDb = AppDatabase.getInstance(getApplicationContext());
        favMovieList = new ArrayList<Object>();
        LiveData<List<FavoriteMovie>> favMovies = mDb.favoriteMovieDao().loadAllMovies();
        //movieResultsJson = (ArrayList<Object>) favMovies.getValue();
        favMovies.observe(this, new Observer<List<FavoriteMovie>>(){
            @Override
            public void onChanged(@Nullable List<FavoriteMovie> favoriteMovie) {
                for (int i = 0; i<favoriteMovie.size();i++) {

                    MovieObject fvMv =  favoriteMovie.get(i).getMovieObject();
                    fvMv.setPosterImage(favoriteMovie.get(i).getPoster());
                    favMovieList.add(fvMv);
                }
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
            startActivity(SHOWFAV);
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
        if(menuItemThatWasSelected == R.id.sort_by_popular_movie){
            Context context = MainActivity.this;
            startActivity(MOST_POP_SORT);
        }
        if(menuItemThatWasSelected == R.id.sort_by_top_rated_movie){
            Context context = MainActivity.this;
            startActivity(TOP_RATED_SORT);
        }
        if(menuItemThatWasSelected == R.id.show_favorite_movies){
            Context context = MainActivity.this;
            /** todo:add functionality for favorite movies and use in parameter**/
            startActivity(SHOWFAV);
        }
        return super.onOptionsItemSelected(item);
    }

}
