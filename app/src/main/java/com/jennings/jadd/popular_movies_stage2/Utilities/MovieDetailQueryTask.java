package com.jennings.jadd.popular_movies_stage2.Utilities;

import android.os.AsyncTask;

import com.jennings.jadd.popular_movies_stage2.MoviePosterAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailQueryTask extends AsyncTask<URL, Void, String> {

    private ArrayList<Object> movieReviews;
    private ArrayList<Object> movieTrailers;
    private MoviePosterAdapter mvAdapter;
    private int detailType;

    public MovieDetailQueryTask(int detailTypeP){
        detailType = detailTypeP;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String MovieDetailResults = null;
        try {
            MovieDetailResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MovieDetailResults != null && !MovieDetailResults.equals("")) {
            if(detailType==1)
                movieReviews = JsonUtils.getObjectsFromJson(MovieDetailResults,1);
            else
                movieTrailers= JsonUtils.getObjectsFromJson(MovieDetailResults,2);
        }

        return MovieDetailResults;
    }

    @Override
    protected void onPostExecute(String MovieDetailResults) {
        if (MovieDetailResults != null && !MovieDetailResults.equals("")) {
            if(detailType==1)
                movieReviews = JsonUtils.getObjectsFromJson(MovieDetailResults,1);
            else
                movieTrailers= JsonUtils.getObjectsFromJson(MovieDetailResults,2);
        }

//        mvAdapter.setMovieList(movieResultsJson);
  //      mvAdapter.notifyDataSetChanged();

    }

}
