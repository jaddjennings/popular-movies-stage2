package com.jennings.jadd.popular_movies_stage2.Utilities;

import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.jennings.jadd.popular_movies_stage2.models.MovieReviewObject;
import com.jennings.jadd.popular_movies_stage2.models.MovieTrailerObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    private static String imagePath = "http://image.tmdb.org/t/p/w185//";
    private static ArrayList<Object> movieObjects;
    private static ArrayList<Object> movieReviewObjects;
    private static ArrayList<Object> movieTrailerObjects;


    private static ArrayList<Object> convertJSONArrayToList(JSONArray jsonArray) throws JSONException{
        ArrayList<Object> retList = new ArrayList<>();
        for (int i = 0; i<jsonArray.length();i++) {
            retList.add(jsonArray.get(i));
        }
        return retList;
    }

    public static  ArrayList<Object> getObjectsFromJson (String apiJsonResult, int objectType){

        JSONObject js_results = new JSONObject();
        ArrayList<Object> listOfResults = new ArrayList<Object>();
        try {
            js_results = new JSONObject(apiJsonResult);
            listOfResults  = convertJSONArrayToList((JSONArray) js_results.get("results"));
            StoreResults(listOfResults, objectType);
        }
        catch (JSONException e){

            System.out.println(e.getMessage());
        }
        if(objectType == 0)
            return movieObjects;
        else  if(objectType == 1)
            return movieReviewObjects;
        else
            return movieTrailerObjects;
    }

    private static void StoreResults(ArrayList<Object> listOfResults, int objectType)throws JSONException {

            if(objectType == 0){
                movieObjects = new ArrayList<Object>();
                MovieObject m;
                for (int i = 0; i<listOfResults.size();i++) {
                    m = parseMovieObjectJson(listOfResults.get(i).toString());
                    movieObjects.add(m);
                }
            }
            else  if(objectType == 1) {
                movieReviewObjects = new ArrayList<Object>();
                MovieReviewObject m;
                for (int i = 0; i < listOfResults.size(); i++) {
                    m = parseMovieReviewObjectJson(listOfResults.get(i).toString());
                    movieReviewObjects.add(m);
                }
            }
            else{
                movieTrailerObjects = new ArrayList<Object>();
                MovieTrailerObject m;
                for (int i = 0; i < listOfResults.size(); i++) {
                    m = parseMovieTrailerObjectJson(listOfResults.get(i).toString());
                    movieTrailerObjects.add(m);
                }
             }
    }

    private static MovieTrailerObject parseMovieTrailerObjectJson(String json) throws  JSONException {
        JSONObject js = new JSONObject();
        String id = "";
        String name = "";
        if(js.has("id"))
            id=js.getString("id");

        if(js.has("name"))
            name=js.getString("name");

        MovieTrailerObject m = new MovieTrailerObject(id,name);
        return m;
    }


    public static MovieReviewObject parseMovieReviewObjectJson(String json) throws  JSONException{
        JSONObject js = new JSONObject();
        String author = "";

        if(js.has("author"))
            author=js.getString("author");

        String content = "";
        if(js.has("content"))
            content=js.getString("content");

        MovieReviewObject m = new MovieReviewObject(author,content);
        return m;
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

        ret = new MovieObject(movieId,vote_average, title, popularity, poster_path, overview,release_date);


    return ret;
    }
}

