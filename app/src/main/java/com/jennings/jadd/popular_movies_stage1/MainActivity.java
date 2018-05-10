package com.jennings.jadd.popular_movies_stage1;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Object> movieResultsJson;

    private RecyclerView movieList;
    private MoviePosterAdapter mvAdapter;
    private Context mnContext;
    private LinearLayout imgHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mnContext = this;
        movieList = (RecyclerView) findViewById(R.id.rv_movies);
        movieList.setLayoutManager(new GridLayoutManager(mnContext, 2));

        mvAdapter = new MoviePosterAdapter(mnContext);

        movieList.setHasFixedSize(true);
        movieList.setAdapter(mvAdapter);

        URL movierequest = NetworkUtils.buildUrl();
        new MovieQueryTask().execute(movierequest);
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
                    // COMPLETED (17) Call showJsonDataView if we have valid, non-null results

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
