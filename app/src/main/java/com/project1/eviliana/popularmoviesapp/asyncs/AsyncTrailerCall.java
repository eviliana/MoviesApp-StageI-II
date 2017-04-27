package com.project1.eviliana.popularmoviesapp.asyncs;

import android.content.Context;
import android.os.AsyncTask;
import com.project1.eviliana.popularmoviesapp.models.Trailer;
import com.project1.eviliana.popularmoviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static com.project1.eviliana.popularmoviesapp.utils.JsonDataParser.getVideoDataFromJSON;


public class AsyncTrailerCall extends AsyncTask<URL, Void, ArrayList<Trailer>> {

    private Context context;
    private ArrayList<Trailer> trailersList;
    private AsyncTaskCompleteListener<ArrayList<Trailer>> listener;

    public AsyncTrailerCall(Context cnt, AsyncTaskCompleteListener<ArrayList<Trailer>> lsnr)
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
    protected ArrayList<Trailer> doInBackground(URL... urls) {
        URL tmdbURL = urls[0];
        String jsonResults = null;
        try {
            jsonResults = NetworkUtils.getResponseFromHttpUrl(tmdbURL);
            if (jsonResults != null) {
                trailersList = getVideoDataFromJSON(jsonResults);
            }
            return trailersList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *Get the trailersList and call onTaskComplete
     * @param trailersList
     */
    @Override
    protected void onPostExecute(ArrayList<Trailer> trailersList) {
        super.onPostExecute(trailersList);
        listener.onTaskComplete(trailersList);
    }
}