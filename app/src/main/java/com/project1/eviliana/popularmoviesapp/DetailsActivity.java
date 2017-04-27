package com.project1.eviliana.popularmoviesapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
import com.project1.eviliana.popularmoviesapp.adapters.ReviewRecyclerAdapter;
import com.project1.eviliana.popularmoviesapp.adapters.TrailerRecyclerAdapter;
import com.project1.eviliana.popularmoviesapp.asyncs.AsyncReviewCall;
import com.project1.eviliana.popularmoviesapp.asyncs.AsyncTaskCompleteListener;
import com.project1.eviliana.popularmoviesapp.asyncs.AsyncTrailerCall;
import com.project1.eviliana.popularmoviesapp.databinding.ActivityDetailsBinding;
import com.project1.eviliana.popularmoviesapp.models.Movie;
import com.project1.eviliana.popularmoviesapp.models.Review;
import com.project1.eviliana.popularmoviesapp.models.Trailer;
import com.project1.eviliana.popularmoviesapp.utils.NetworkUtils;
import com.project1.eviliana.popularmoviesapp.utils.Queries;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements TrailerRecyclerAdapter.MovieTrailerClickListener {

    private Movie movieItem;
    private ActivityDetailsBinding mBinding;
    private ArrayList<Trailer> trailersList;
    private ArrayList<Review> reviewsList;
    private TrailerRecyclerAdapter mVideoAdapter;
    private ReviewRecyclerAdapter mReviewAdapter;
    private final static String RECYCLER_STATE = "recycler_state";
    private RecyclerView mVideoRecyclerView;
    private RecyclerView mReviewRecyclerView;
    private RecyclerView.LayoutManager mVideoLayoutManager;
    private RecyclerView.LayoutManager mReviewLayoutManager;
    private static final int TRAILER_LOADER = 22;
    private static final int REVIEW_LOADER = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        //Change the stars color in ratingBar to yellow
        LayerDrawable stars = (LayerDrawable) mBinding.detailsSection.simpleRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        trailersList = new ArrayList<>();
        reviewsList = new ArrayList<>();

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
            //Toast.makeText(this,Integer.toString(movieItem.getId()),Toast.LENGTH_SHORT).show();

            //initialize video recycler
            mVideoRecyclerView = mBinding.trailerSection.trailersRecycler;
            mVideoLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            mVideoRecyclerView.setLayoutManager(mVideoLayoutManager);
            mVideoRecyclerView.setHasFixedSize(true);

            //initialize review recycler
            mReviewRecyclerView = mBinding.trailerSection.reviewsRecycler;
            mReviewLayoutManager = new LinearLayoutManager(this);
            mReviewRecyclerView.setLayoutManager(mReviewLayoutManager);
            mReviewRecyclerView.setHasFixedSize(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (trailersList.size() == 0 && reviewsList.size() == 0){
                if (NetworkUtils.hasNetworkAcces(this)) {
                    fetchTrailers(Integer.toString(movieItem.getId()));
                    fetchReviews(Integer.toString(movieItem.getId()));
                } else {
                    Toast.makeText(this, "Internet connection failed, please try again", Toast.LENGTH_SHORT).show();
                }
        }
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

    public void fetchTrailers(String id){
        String apiKey = getString(R.string.myApiKey);
        URL trailerQueryUrl = Queries.buildVideoUrl(id, apiKey);
        AsyncTrailerCall at = new AsyncTrailerCall(this, new FetchMyTrailerTaskCompleteListener());
        at.execute(trailerQueryUrl);
    }

    public void fetchReviews(String id){
        String apiKey = getString(R.string.myApiKey);
        URL reviewQueryUrl = Queries.buildReviewUrl(id, apiKey);
        AsyncReviewCall ar = new AsyncReviewCall(this, new FetchMyReviewTaskCompleteListener());
        ar.execute(reviewQueryUrl);
    }

    private void populateTrailerRecycler(){
        mVideoAdapter = new TrailerRecyclerAdapter(this, trailersList, this);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
    }

    private void populateReviewRecycler(){
        mReviewAdapter = new ReviewRecyclerAdapter(this, reviewsList);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
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

    /**
     * Onclick, open the trailer url in browser
     * @param clickedTrailer - the thumbnail tha the user choose from the recyclerview
     */
    @Override
    public void onMovieTrailerClick(int clickedTrailer) {
        String trailerPath = trailersList.get(clickedTrailer).getVideoPath();
        Uri youtubePage = Uri.parse(Queries.YOUTUBE_TRAILER_BASE + trailerPath);
        Intent intentToOpenYoutube = new Intent(Intent.ACTION_VIEW, youtubePage);
        if (intentToOpenYoutube.resolveActivity(getPackageManager()) != null){
            startActivity(intentToOpenYoutube);
        }
    }

    public class FetchMyTrailerTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Trailer>>
    {
        /**
         * @param result The resulting object from the AsyncTask.
         */
        @Override
        public void onTaskComplete(ArrayList<Trailer> result) {
            trailersList = result;
            if (trailersList != null && !trailersList.equals("")) {
                populateTrailerRecycler();
            }
        }
    }

    public class FetchMyReviewTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Review>>
    {
        /**
         * @param result The resulting object from the AsyncTrailerTask.
         */
        @Override
        public void onTaskComplete(ArrayList<Review> result) {
            reviewsList = result;
            if (reviewsList != null && !reviewsList.equals("")) {
                populateReviewRecycler();
            }
        }
    }
}
