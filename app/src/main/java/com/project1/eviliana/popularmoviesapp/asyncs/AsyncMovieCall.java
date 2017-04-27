package com.project1.eviliana.popularmoviesapp.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import com.project1.eviliana.popularmoviesapp.models.Movie;
import com.project1.eviliana.popularmoviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.project1.eviliana.popularmoviesapp.utils.JsonDataParser.getMovieDataFromJSON;

/**
 * The AsyncTaskCall handles the http call in a background thread
 * and calls getMovieDataFromJSON which parses the JSON data into
 * movie objects
 */
public class AsyncMovieCall extends AsyncTask<URL, Void, ArrayList<Movie>> {

    private Context context;
    private ArrayList<Movie> moviesList;
    private AsyncTaskCompleteListener<ArrayList<Movie>> listener;

    public AsyncMovieCall(Context cnt, AsyncTaskCompleteListener<ArrayList<Movie>> lsnr)
    {
        this.context = cnt;
        this.listener = lsnr;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(context, "The data is downloading", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        URL tmdbURL = urls[0];
        String jsonResults = null;
        try {
            jsonResults = NetworkUtils.getResponseFromHttpUrl(tmdbURL);
            if (jsonResults != null) {
                moviesList = getMovieDataFromJSON(jsonResults);
            }
            return moviesList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *Get the movieList and call onTaskComplete
     * @param moviesList
     */
    @Override
    protected void onPostExecute(ArrayList<Movie> moviesList) {
        super.onPostExecute(moviesList);
        listener.onTaskComplete(moviesList);
    }
}
