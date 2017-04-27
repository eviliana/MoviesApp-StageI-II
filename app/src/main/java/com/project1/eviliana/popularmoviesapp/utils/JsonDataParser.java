package com.project1.eviliana.popularmoviesapp.utils;

import com.project1.eviliana.popularmoviesapp.models.Movie;
import com.project1.eviliana.popularmoviesapp.models.Review;
import com.project1.eviliana.popularmoviesapp.models.Trailer;

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

    private static final String JSON_ROOT_ARRAY="results";
    private static final String GENERIC_ID = "id";

    /**
     * Parse Movies JSON
     * @param JSON
     * @return
     * @throws JSONException
     */
    public static ArrayList<Movie> getMovieDataFromJSON(String JSON) throws JSONException{

        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_OVERVIEW ="overview";
        final String MOVIE_RELEASE_DATE = "release_date";
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
            int id = movie.getInt(GENERIC_ID);
            String originalTitle = movie.getString(MOVIE_ORIGINAL_TITLE);
            double voteAverage = movie.getDouble(MOVIE_VOTE_AVERAGE);

            movies.add(new Movie(posterPath,overview,releaseDate,id,originalTitle,voteAverage));


        }
        return movies;
    }

    /**
     * Parse JSON for youtube trailers
     * @param JSON
     * @return
     * @throws JSONException
     */
    public static ArrayList<Trailer> getVideoDataFromJSON(String JSON) throws JSONException{

        final String VIDEO_KEY = "key";
        final String VIDEO_NAME = "name";

        ArrayList<Trailer> trailers = new ArrayList<>();

        JSONObject root = new JSONObject(JSON);
        JSONArray results = root.getJSONArray(JSON_ROOT_ARRAY);

        for (int i=0; i< results.length(); i++){
            JSONObject video = results.getJSONObject(i);
            String id = video.getString(GENERIC_ID);
            String videoPath = video.getString(VIDEO_KEY);
            String videoName = video.getString(VIDEO_NAME);

            trailers.add(new Trailer(id,videoName,videoPath));


        }
        return trailers;
    }

    /**
     * Parse movie's reviews
     * @param JSON
     * @return
     * @throws JSONException
     */
    public static ArrayList<Review> getReviewDataFromJSON(String JSON) throws JSONException{

        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";

        ArrayList<Review> reviews = new ArrayList<>();

        JSONObject root = new JSONObject(JSON);
        JSONArray results = root.getJSONArray(JSON_ROOT_ARRAY);

        for (int i=0; i< results.length(); i++){
            JSONObject review = results.getJSONObject(i);
            String id = review.getString(GENERIC_ID);
            String reviewAuthor = review.getString(REVIEW_AUTHOR);
            String reviewContent = review.getString(REVIEW_CONTENT);

            reviews.add(new Review(id,reviewAuthor,reviewContent));


        }
        return reviews;
    }
}
