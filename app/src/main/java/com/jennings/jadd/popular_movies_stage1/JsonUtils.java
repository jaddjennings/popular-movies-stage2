package com.jennings.jadd.popular_movies_stage1;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {

    private static ArrayList<Object> movieObjects;
    private static String imagePath = "http://image.tmdb.org/t/p/w185//";
    private static ArrayList<Object> convertJSONArrayToList(JSONArray jsonArray) throws JSONException{
        ArrayList<Object> retList = new ArrayList<>();
        for (int i = 0; i<jsonArray.length();i++) {
            retList.add(jsonArray.get(i));
        }
        return retList;
    }

    public static  ArrayList<Object> getMovieObjects (String apiJsonResult){

        JSONObject js_results = new JSONObject();
        ArrayList<Object> listOfResults = new ArrayList<Object>();
        try {
            js_results = new JSONObject(apiJsonResult);
            listOfResults  = convertJSONArrayToList((JSONArray) js_results.get("results"));
            StoreResults(listOfResults);
        }
        catch (JSONException e){

            System.out.println(e.getMessage());
        }
        return movieObjects;
    }

    private static void StoreResults(ArrayList<Object> listOfResults)throws JSONException {
            movieObjects = new ArrayList<Object>();
            MovieObject m;
            for (int i = 0; i<listOfResults.size();i++) {
                m = parseMovieObjectJson(listOfResults.get(i).toString());
                movieObjects.add(m);
            }
    }

    /**Load single movie from results array of movies**/
    public static MovieObject parseMovieObjectJson(String json) throws JSONException{

        JSONObject js = new JSONObject();
        MovieObject ret;
        js = new JSONObject(json);

        int movieId = -1;
        if(js.has("id"))
            movieId=js.getInt("id");

        Double vote_average = 0.0;
        if(js.has("vote_average"))
            vote_average=js.getDouble("vote_average");

        String title = new String("");
        if(js.has("title"))
            title=js.getString("title");

        Double popularity = 0.0;
        if(js.has("popularity"))
            popularity=js.getDouble("popularity");

        String poster_path = "";
        if(js.has("poster_path"))
            poster_path = imagePath.concat(js.getString("poster_path"));

        String overview = "";
        if(js.has("overview"))
            overview=js.getString("overview");

        String release_date = "";
        if(js.has("release_date"))
            release_date=js.getString("release_date");

        ret = new MovieObject(vote_average, title, popularity, poster_path, overview,release_date);

        ret.setId(movieId);

    return ret;
    }
}

