package com.jennings.jadd.popular_movies_stage2.Utilities;

import android.net.Uri;

import com.jennings.jadd.popular_movies_stage2.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String POP_MOVIE_BASE_URL =
            "http://api.themoviedb.org/3/movie/popular"
            ;

    final static String TOP_MOVIE_BASE_URL =
            "http://api.themoviedb.org/3/movie/top_rated"
            ;
    final static String PARAM_API_KEY = "api_key";

    final static String api_key = BuildConfig.GoogleSecAPIKEY;


    public static URL buildUrl(int sortBy) {
        Uri builtUri;
        if(sortBy==1){
             builtUri = Uri.parse(POP_MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(PARAM_API_KEY, api_key)
                    /**.appendQueryParameter(PARAM_SORT, sortBy)**/
                    .build();
        }
        else {
             builtUri = Uri.parse(TOP_MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(PARAM_API_KEY, api_key)
                    /**.appendQueryParameter(PARAM_SORT, sortBy)**/
                    .build();
        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
