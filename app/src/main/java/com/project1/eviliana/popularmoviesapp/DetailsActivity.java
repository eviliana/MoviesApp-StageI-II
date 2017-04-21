package com.project1.eviliana.popularmoviesapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.project1.eviliana.popularmoviesapp.databinding.ActivityDetailsBinding;
import com.project1.eviliana.popularmoviesapp.model.Movie;
import com.project1.eviliana.popularmoviesapp.utils.Queries;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private Movie movieItem;
    ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        //Change the stars color in ratingBar to yellow
        LayerDrawable stars = (LayerDrawable) mBinding.detailsSection.simpleRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_ITEM)){
            movieItem = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_ITEM);
            mBinding.titleDisplay.setText(movieItem.getOriginalTitle());
            //workaround to get the year
            mBinding.detailsSection.dateDisplay.setText(movieItem.getReleaseDate().substring(0,4));
            //workaround to rate with a scale from 1 to 5 instead of 10
            float convert = (float) (movieItem.getVoteAverage() / 2);
            mBinding.detailsSection.simpleRatingBar.setRating(convert);
            mBinding.detailsSection.ratingDisplay.setText(Double.toString(movieItem.getVoteAverage()) + "/10");
            mBinding.plotSummary.setText(movieItem.getOverview());
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
                .into(mBinding.detailsSection.posterDisplay);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
