package com.jennings.jadd.popular_movies_stage1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Object> movieResultsJson;
    private String imagePath = "http://image.tmdb.org/t/p/w185//";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ImageView ingredientsIv = findViewById(R.id.image_iv);

        /**TODO: create Array Adapter for Grid Layout**/

       /** Picasso.with(this)
                .load(imagePath.concat("nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"))
                .into(ingredientsIv);

        ingredientsIv = findViewById(R.id.image_iv2);
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg ")
                .into(ingredientsIv);

**/

        URL movierequest = NetworkUtils.buildUrl();
        new MovieQueryTask().execute(movierequest);
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

            // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
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
            }

    }
}
