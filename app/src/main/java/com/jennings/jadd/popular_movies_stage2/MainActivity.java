package com.jennings.jadd.popular_movies_stage2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jennings.jadd.popular_movies_stage2.Utilities.JsonUtils;
import com.jennings.jadd.popular_movies_stage2.Utilities.NetworkUtils;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.ListItemClickListener {

    private ArrayList<Object> movieResultsJson;

    private RecyclerView movieList;
    private MoviePosterAdapter mvAdapter;
    private Context mnContext;
    private LinearLayout imgHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startActivity(1);

    }

    private void startActivity(int currentSortBy) {
        setContentView(R.layout.activity_main);
        mnContext = this;
        movieList = (RecyclerView) findViewById(R.id.rv_movies);
        movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));

        mvAdapter = new MoviePosterAdapter(mnContext, this);

        movieList.setHasFixedSize(true);
        movieList.setAdapter(mvAdapter);

        if(checkInternetConnection(this)) {
            URL movieRequest= NetworkUtils.buildUrl(currentSortBy);
            new MovieQueryTask().execute(movieRequest);
        }
        else{
            finish();
        }
    }

    private boolean checkInternetConnection(Context main) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        else{

            Toast.makeText(main, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
            final Handler delay = new Handler();
            delay.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },2000);
            return false;
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
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
            startActivity(1);
        }
        if(menuItemThatWasSelected == R.id.sort_by_top_rated_movie){
            Context context = MainActivity.this;
            startActivity(2);
        }
        return super.onOptionsItemSelected(item);
    }
    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(URL... params) {
                URL searchUrl = params[0];
                String MovieSearchResults = null;
                try {
                    MovieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (MovieSearchResults != null && !MovieSearchResults.equals("")) {

                    movieResultsJson = JsonUtils.getMovieObjects(MovieSearchResults);

                }


                return MovieSearchResults;
            }

            @Override
            protected void onPostExecute(String MovieSearchResults) {
                if (MovieSearchResults != null && !MovieSearchResults.equals("")) {
                    // COMPLETED (17) Call showJsonDataView if we have valid, non-null results

                    movieResultsJson = JsonUtils.getMovieObjects(MovieSearchResults);

                }
                mvAdapter.setMovieList(movieResultsJson);
                mvAdapter.notifyDataSetChanged();

            }

    }
}
