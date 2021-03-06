package com.project1.eviliana.popularmoviesapp.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * This class contains methods regarding internet connectivity
 */
public class NetworkUtils {

    /**
     * This method checks for internet connectivity
     * Taken from a very useful Lynda.com tutorial
     * @return
     */
    public static boolean hasNetworkAcces(Context context){

        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method performs the http connection for our query to tmdbAPI
     * @param url - the build url from Queries
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000); //call timeout in case of slow connectivity
        urlConnection.setReadTimeout(10000); //read timeout to 10 seconds
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);

            //this forces the scanner to read the entire contents of the stream
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }

        } finally{
            urlConnection.disconnect();
        }

    }

}
