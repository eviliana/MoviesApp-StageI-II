package com.project1.eviliana.popularmoviesapp.asyncs;

import android.content.Context;
import android.os.AsyncTask;
import com.project1.eviliana.popularmoviesapp.models.Review;
import com.project1.eviliana.popularmoviesapp.utils.NetworkUtils;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static com.project1.eviliana.popularmoviesapp.utils.JsonDataParser.getReviewDataFromJSON;


public class AsyncReviewCall extends AsyncTask<URL, Void, ArrayList<Review>> {

    private Context context;
    private ArrayList<Review> reviewsList;
    private AsyncTaskCompleteListener<ArrayList<Review>> listener;

    public AsyncReviewCall(Context cnt, AsyncTaskCompleteListener<ArrayList<Review>> lsnr)
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
    protected ArrayList<Review> doInBackground(URL... urls) {
        URL tmdbURL = urls[0];
        String jsonResults = null;
        try {
            jsonResults = NetworkUtils.getResponseFromHttpUrl(tmdbURL);
            if (jsonResults != null) {
                reviewsList = getReviewDataFromJSON(jsonResults);
            }
            return reviewsList;
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
     * @param reviewsList
     */
    @Override
    protected void onPostExecute(ArrayList<Review> reviewsList) {
        super.onPostExecute(reviewsList);
        listener.onTaskComplete(reviewsList);
    }
}
