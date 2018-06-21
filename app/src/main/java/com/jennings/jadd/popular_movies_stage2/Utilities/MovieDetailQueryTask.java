package com.jennings.jadd.popular_movies_stage2.Utilities;

import android.os.AsyncTask;

import com.jennings.jadd.popular_movies_stage2.MoviePosterAdapter;
import com.jennings.jadd.popular_movies_stage2.MovieTrailerReviewAdapter;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailQueryTask extends AsyncTask<URL, Void, ArrayList<Object>> {

    private ArrayList<Object> movieReturnList;
    private int detailType;
    private MovieTrailerReviewAdapter mvTrailerReviewAdapter;

    public MovieDetailQueryTask(int detailTypeP, ArrayList<Object> retList, MovieTrailerReviewAdapter mvTRAdapter){
        detailType = detailTypeP;
        movieReturnList= retList;
        mvTrailerReviewAdapter = mvTRAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Object> doInBackground(URL... params) {
        URL searchUrl = params[0];
        String MovieDetailResults = null;
        try {
            MovieDetailResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MovieDetailResults != null && !MovieDetailResults.equals("")) {
            movieReturnList.addAll(JsonUtils.getObjectsFromJson(MovieDetailResults, detailType));
        }
        return movieReturnList;
    }

    @Override
    protected void onPostExecute(ArrayList<Object> MovieDetailResults) {

        mvTrailerReviewAdapter.setMovieTrailerReviewList(movieReturnList);
        mvTrailerReviewAdapter.notifyDataSetChanged();
    }

}
