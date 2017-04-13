package com.project1.eviliana.popularmoviesapp.utils;

import com.project1.eviliana.popularmoviesapp.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * This class is a JSON parser for the movie data.
 * Each property is stored in an array list with movie objects.
 * This solution is inspired from Android Basics: Networking and Sunshine project combined
 */

public class JsonDataParser {

    public static ArrayList<Movie> getMovieDataFromJSON(String JSON) throws JSONException{

        final String JSON_ROOT_ARRAY="results";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_OVERVIEW ="overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_VOTE_AVERAGE = "vote_average";

        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject root = new JSONObject(JSON);
        JSONArray results = root.getJSONArray(JSON_ROOT_ARRAY);

        for (int i=0; i< results.length(); i++){
            JSONObject movie = results.getJSONObject(i);
            String posterPath = movie.getString(MOVIE_POSTER_PATH);
            String overview = movie.getString(MOVIE_OVERVIEW);
            //dateObject = new Date(time);
            String releaseDate = movie.getString(MOVIE_RELEASE_DATE);
            int id = movie.getInt(MOVIE_ID);
            String originalTitle = movie.getString(MOVIE_ORIGINAL_TITLE);
            double voteAverage = movie.getDouble(MOVIE_VOTE_AVERAGE);

            movies.add(new Movie(posterPath,overview,releaseDate,id,originalTitle,voteAverage));


        }
        return movies;
    }
}
