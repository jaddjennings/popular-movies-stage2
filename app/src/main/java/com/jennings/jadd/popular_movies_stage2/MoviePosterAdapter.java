package com.jennings.jadd.popular_movies_stage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviePosterAdapter extends RecyclerView.Adapter <MoviePosterAdapter.MoviePosterViewHolder>{

    private static final String TAG = MoviePosterAdapter.class.getSimpleName();

    private int mMoviePosterItems;
    private Context mnActivity;
    private String imagePath;
    private ArrayList<Object> movieList;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MoviePosterAdapter( Context main, ListItemClickListener listener){
        mMoviePosterItems= 0;
        mnActivity = main;
        mOnClickListener = listener;
    }

    public void setMovieList(ArrayList<Object> movies){
        mMoviePosterItems= movies.size();
        movieList = movies;
    }
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    public int getItemCount() {
        return mMoviePosterItems;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MoviePosterViewHolder viewHolder = new MoviePosterViewHolder(view);

        return viewHolder;
    }

    /**
     * Cache of the children views for a list item.
     */
    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        ImageView listItemMoviePosterView;

        public void onClick(View view){
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
        public MoviePosterViewHolder(View itemView) {
            super(itemView);
            listItemMoviePosterView = (ImageView) itemView.findViewById(R.id.image_movie_poster);
            itemView.setOnClickListener(this);
        }


        void bind(int listIndex){

             if(listIndex>=0)
                Picasso.with(mnActivity)
                       .load(((MovieObject)movieList.get(listIndex)).getPosterPath())
                       .into(listItemMoviePosterView);

        }
    }
}
