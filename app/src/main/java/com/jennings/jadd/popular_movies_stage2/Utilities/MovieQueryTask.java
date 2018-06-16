package com.jennings.jadd.popular_movies_stage2.Utilities;

import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.jennings.jadd.popular_movies_stage2.MoviePosterAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieQueryTask extends AsyncTask<URL, Void, String> {

    private final RecyclerView mvListRV;
    private final Parcelable mvStateOfRV;
    private ArrayList<Object> movieResultsJson;
    private MoviePosterAdapter mvAdapter;

    public  MovieQueryTask(ArrayList<Object> mvResults, MoviePosterAdapter mvAdaptr, RecyclerView rv, Parcelable stateOfList){
        movieResultsJson = mvResults;
        mvAdapter = mvAdaptr;
        mvListRV = rv;
        mvStateOfRV = stateOfList;
    }

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

            movieResultsJson = JsonUtils.getObjectsFromJson(MovieSearchResults, 0);

        }


        return MovieSearchResults;
    }

    @Override
    protected void onPostExecute(String MovieSearchResults) {
        if (MovieSearchResults != null && !MovieSearchResults.equals("")) {
            // COMPLETED (17) Call showJsonDataView if we have valid, non-null results

            movieResultsJson = JsonUtils.getObjectsFromJson(MovieSearchResults,0);

        }
        mvAdapter.setMovieList(movieResultsJson);
        mvAdapter.notifyDataSetChanged();
        if(mvStateOfRV != null)
            mvListRV.getLayoutManager().onRestoreInstanceState(mvStateOfRV);

    }

}
