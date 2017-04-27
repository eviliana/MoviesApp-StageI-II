package com.project1.eviliana.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project1.eviliana.popularmoviesapp.R;
import com.project1.eviliana.popularmoviesapp.models.Trailer;
import com.project1.eviliana.popularmoviesapp.utils.Queries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.NumberViewHolder> {

        private Context mContext;
        private ArrayList<Trailer> mTrailersList;
        private MovieTrailerClickListener mOnClickListener;

        public TrailerRecyclerAdapter(Context context, ArrayList<Trailer> trailersList, MovieTrailerClickListener listener){
            mContext = context;
            mTrailersList = trailersList;
            mOnClickListener = listener;
        }

        /**
         * This interface handles the trailer clicks
         */
        public interface MovieTrailerClickListener {
            void onMovieTrailerClick(int clickedTrailerKey);
        }

        @Override
        public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context = viewGroup.getContext();
            int layoutIdforPosterItem = R.layout.trailer_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately=false;

            View view = inflater.inflate(layoutIdforPosterItem, viewGroup, shouldAttachToParentImmediately);
            NumberViewHolder viewHolder = new NumberViewHolder(view);

            return viewHolder;
        }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        String vidPath = mTrailersList.get(position).getVideoPath();
        String url = Queries.YOUTUBE_THUMBNAIL_BASE + vidPath + Queries.SM_YOUTUBE_THUMB_JPG;
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.nodisplay)
                .into(holder.TrailerItemNumberView);
        holder.trailerTitle.setText(mTrailersList.get(position).getVideoName());
    }
        @Override
        public int getItemCount() {
            return mTrailersList.size();
        }

        /**
         * This is the ViewHolder class for caching our ImageViews
         */
        class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView TrailerItemNumberView;
            TextView trailerTitle;

            public NumberViewHolder(View itemView) {
                super(itemView);

                trailerTitle = (TextView) itemView.findViewById(R.id.trailer_title);
                TrailerItemNumberView = (ImageView) itemView.findViewById(R.id.trailer_item);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int clickedPosition = getAdapterPosition();
                mOnClickListener.onMovieTrailerClick(clickedPosition);
            }
        }
}
