package com.project1.eviliana.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project1.eviliana.popularmoviesapp.R;
import com.project1.eviliana.popularmoviesapp.models.Review;

import java.util.ArrayList;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.NumberViewHolder> {

    private Context mContext;
    private ArrayList<Review> mReviewsList;

    public ReviewRecyclerAdapter(Context context, ArrayList<Review> reviewsList){
        mContext = context;
        mReviewsList = reviewsList;
    }

    @Override
    public ReviewRecyclerAdapter.NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdforReviewItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately=false;

        View view = inflater.inflate(layoutIdforReviewItem, viewGroup, shouldAttachToParentImmediately);
        ReviewRecyclerAdapter.NumberViewHolder viewHolder = new ReviewRecyclerAdapter.NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewRecyclerAdapter.NumberViewHolder holder, int position) {
       holder.reviewAuthorNumberView.setText(mReviewsList.get(position).getReviewAuthor());
       holder.reviewContentNumberView.setText(mReviewsList.get(position).getReviewContent());
    }
    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    /**
     * This is the ViewHolder class for caching our ImageViews
     */
    class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthorNumberView;
        TextView reviewContentNumberView;

        public NumberViewHolder(View itemView) {
            super(itemView);

            reviewAuthorNumberView = (TextView) itemView.findViewById(R.id.review_author);
            reviewContentNumberView = (TextView) itemView.findViewById(R.id.review_content);
        }

    }
}
