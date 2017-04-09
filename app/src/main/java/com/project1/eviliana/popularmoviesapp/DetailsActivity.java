package com.project1.eviliana.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project1.eviliana.popularmoviesapp.adapter.MovieRecyclerAdapter;
import com.project1.eviliana.popularmoviesapp.model.Movie;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDate;
    private TextView mRating;
    private TextView mPlotTitle;
    private TextView mPlotSummary;
    private ImageView mPoster;
    private ViewGroup mContentView;
    private Movie movieItem;
    private String fgf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitle = (TextView) findViewById(R.id.rating_display);
        mDate = (TextView) findViewById(R.id.date_display);
        mRating = (TextView) findViewById(R.id.rating_display);
        mPlotTitle = (TextView) findViewById(R.id.plot_display);
        mPlotSummary = (TextView) findViewById(R.id.plot_summary);
        mPoster = (ImageView) findViewById(R.id.poster_display);
        mContentView = (ViewGroup) findViewById(R.id.activity_details);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_ITEM)){
            movieItem = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_ITEM);
            fgf = movieItem.getOriginalTitle().toString();

        }

    }
    //Shal we go fullscreen?
    /*private void setToFullScreen (){

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
               // | View.SYSTEM_UI_FLAG_VISIBLE);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //when activity is on focus, call setToFullScreen
        //setToFullScreen();
        Toast.makeText(this,fgf,Toast.LENGTH_SHORT).show();
    }
}
