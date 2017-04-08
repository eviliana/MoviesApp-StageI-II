package com.project1.eviliana.popularmoviesapp.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.project1.eviliana.popularmoviesapp.MainActivity;
import com.project1.eviliana.popularmoviesapp.R;
import com.project1.eviliana.popularmoviesapp.model.Movie;
import com.project1.eviliana.popularmoviesapp.utils.Queries;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.NumberViewHolder> {

    private Context mContext;
    private ArrayList<Movie> mMovies;

    public MovieRecyclerAdapter(Context context, ArrayList<Movie> moviesList) {
        this.mContext = context;
        this.mMovies = moviesList;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdforPosterItem = R.layout.poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately=false;

        View view = inflater.inflate(layoutIdforPosterItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    /**
     * On onBindViewHolder, we load our posters with Picasso
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
            String posPath = mMovies.get(position).getPosterPath().toString();
            String url = Queries.POSTERS_BASE_URL_185 + posPath;
            Picasso.with(mContext)
                    .load(url)
                    .error(R.drawable.movie_icon_15138)
                    .placeholder(R.drawable.movie_icon_15138)
                    .into(holder.PosterItemNumberView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    /**
     * This is the ViewHolder class for caching our ImageViews
     */
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView PosterItemNumberView;

        public NumberViewHolder(View itemView){
            super(itemView);

            PosterItemNumberView = (ImageView) itemView.findViewById(R.id.poster_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
