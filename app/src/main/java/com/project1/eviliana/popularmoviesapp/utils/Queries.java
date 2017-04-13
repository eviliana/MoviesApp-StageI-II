package com.project1.eviliana.popularmoviesapp.utils;


import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This where the API queries are build
 * Based on ToysApp exercises
 */
public class Queries {

    final static String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String POSTERS_BASE_URL_185 = "http://image.tmdb.org/t/p/w185/";
    final static String API_KEY = "api_key";

    /**
     * Build the api url call, depending of user's choice
     * @param qParam
     * @param apiKey
     * @return
     */
    public static URL buildMovieUrl(String qParam, String apiKey){

        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(qParam)
                .appendQueryParameter(API_KEY,apiKey)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}
