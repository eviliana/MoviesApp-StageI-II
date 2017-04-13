package com.project1.eviliana.popularmoviesapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.project1.eviliana.popularmoviesapp.model.Movie;
import com.project1.eviliana.popularmoviesapp.utils.Queries;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDate;
    private TextView mRating;
    private TextView mPlotSummary;
    private ImageView mPoster;
    private RatingBar mRatingBar;
    private Movie movieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitle = (TextView) findViewById(R.id.title_display);
        mDate = (TextView) findViewById(R.id.date_display);
        mRating = (TextView) findViewById(R.id.rating_display);
        TextView mPlotTitle = (TextView) findViewById(R.id.plot_display);
        mPlotSummary = (TextView) findViewById(R.id.plot_summary);
        mPoster = (ImageView) findViewById(R.id.poster_display);
        mRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        TextView mTextDate = (TextView) findViewById(R.id.text_date);
        TextView mTextRating = (TextView) findViewById(R.id.text_rating);
        //Change the stars color in ratingBar to yellow
        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_ITEM)){
            movieItem = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_ITEM);
            mTitle.setText(movieItem.getOriginalTitle());
            //workaround to get the year
            mDate.setText(movieItem.getReleaseDate().substring(0,4));
            //workaround to rate with a scale from 1 to 5 instead of 10
            float convert = (float) (movieItem.getVoteAverage() / 2);
            mRatingBar.setRating(convert);
            mRating.setText(Double.toString(movieItem.getVoteAverage()) + "/10");
            mPlotSummary.setText(movieItem.getOverview());
            usePicasso();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void usePicasso(){
        String posPath = movieItem.getPosterPath().toString();
        String url = Queries.POSTERS_BASE_URL_185 + posPath;
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.nodisplay)
                .into(mPoster);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
