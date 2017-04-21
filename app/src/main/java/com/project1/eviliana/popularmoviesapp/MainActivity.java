package com.project1.eviliana.popularmoviesapp;

import com.project1.eviliana.popularmoviesapp.adapter.MovieRecyclerAdapter;
import com.project1.eviliana.popularmoviesapp.model.Movie;
import com.project1.eviliana.popularmoviesapp.utils.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MovieRecyclerAdapter.MoviePosterClickListener{

    ArrayList<Movie> moviesList;
    private MovieRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    protected final static String MOVIE_ITEM = "movie_item";
    private final static String RECYCLER_STATE = "recycler_state";
    private final static String MENU_SELECTED = "selected";
    private int selected = -1;
    GridLayoutManager layoutManager;
    private Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.posters_recycler);

        int numberOfColumns = 2; //2 columns for the GridLayoutManager
        layoutManager = new GridLayoutManager(context, numberOfColumns);

        //If in Landscape orientation, make number of columns 3 instead of 2
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.setSpanCount(3);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState != null){
            //Restore recycler state and menu choice
            selected = savedInstanceState.getInt(MENU_SELECTED);
            moviesList = savedInstanceState.getParcelableArrayList(RECYCLER_STATE);
            mAdapter = new MovieRecyclerAdapter(context, moviesList, this);
            mRecyclerView.setAdapter(mAdapter);
            }
    }

    /**
     * The reason i don't store hasNetworkAcces results in a var
     * is mainly because in this way, i always get real time connectivity check :P
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (moviesList.size() == 0){
            if (NetworkUtils.hasNetworkAcces(this)){
                fetchMovieData("popular");
            } else {
                Toast.makeText(context,"Internet connection failed, please try again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save recycler state
        outState.putParcelableArrayList(RECYCLER_STATE, moviesList);
        //Save menu choice
        outState.putInt(MENU_SELECTED,selected);
    }

    /**
     * This is a workaround to help me pass click listener in onPostExecute :)
     */
    public void populateAdapter(){
        mAdapter = new MovieRecyclerAdapter(context, moviesList, this);
        mRecyclerView.setAdapter(mAdapter);
    }
    /**
     * Get menu selection and build the url for the http call
     * Also, this where we pass user's api key for tmdb
     * We call AsyncTask to move our http call from the main thread
     * @param qParam - popular or top_rated for the api call
     */
    private void fetchMovieData(String qParam) {
        String apiKey = getString(R.string.myApiKey);
        URL movieQueryUrl = Queries.buildMovieUrl(qParam, apiKey);
        AsyncTaskCall at = new AsyncTaskCall(this, new FetchMyDataTaskCompleteListener());
        at.execute(movieQueryUrl);
    }

    /**
     * We inflate our options menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        //check if there is an id from screen rotation and choose the correct menu item
        //solution from stackoverflow
        switch (selected){
            case R.id.action_popular:
                menu.findItem(R.id.action_popular).setChecked(true);
                break;
            case R.id.action_topRated:
                menu.findItem(R.id.action_topRated).setChecked(true);
                break;
            case R.id.action_favorites:
                menu.findItem(R.id.action_favorites).setChecked(true);
                break;
            default:
                //The app initializes with a "most popular" call, so if no saved menu id,
                //set most popular as choice
                menu.findItem(R.id.action_popular).setChecked(true);
        }
        return true;
    }

    /**
     * Choose a menu action and pass the parameter for the query
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
               if (NetworkUtils.hasNetworkAcces(this)){
                   fetchMovieData("popular");
                   selected = item.getItemId();
                   if(!item.isChecked()){
                       item.setChecked(true);
                   }
                } else {
                    Toast.makeText(context,"Internet connection failed, please try again",Toast.LENGTH_SHORT).show();
                    //mRecyclerView.setAdapter(null); in case we want to clear the images from previous call
                }
                break;
            case R.id.action_topRated:
                if (NetworkUtils.hasNetworkAcces(this)){
                    fetchMovieData("top_rated");
                    selected = item.getItemId();
                    if(!item.isChecked()){
                        item.setChecked(true);
                    }
                } else {
                    Toast.makeText(context,"Internet connection failed, please try again",Toast.LENGTH_SHORT).show();
                    //mRecyclerView.setAdapter(null); in case we want to clear the images from previous call
                }
                break;
            case R.id.action_favorites:
            if (NetworkUtils.hasNetworkAcces(this)){
                //fetchMovieData("top_rated");
                selected = item.getItemId();
                if(!item.isChecked()){
                    item.setChecked(true);
                }
            } else {
                Toast.makeText(context,"Internet connection failed, please try again",Toast.LENGTH_SHORT).show();
                //mRecyclerView.setAdapter(null); in case we want to clear the images from previous call
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviePosterClick(int clickedPosterId) {
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailsActivity = new Intent(context, destinationClass);
        intentToStartDetailsActivity.putExtra(MOVIE_ITEM, moviesList.get(clickedPosterId));
        startActivity(intentToStartDetailsActivity);
    }

    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Movie>>
    {
        /**
         * @param result The resulting object from the AsyncTask.
         */
        @Override
        public void onTaskComplete(ArrayList<Movie> result) {
            moviesList = result;
            if (moviesList != null && !moviesList.equals("")) {
                populateAdapter();
            }
        }
    }
}
