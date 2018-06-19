package com.jennings.jadd.popular_movies_stage2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.jennings.jadd.popular_movies_stage2.Utilities.Helpers;
import com.jennings.jadd.popular_movies_stage2.database.FavoriteMovie;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class MoviePosterAdapter extends RecyclerView.Adapter <MoviePosterAdapter.MoviePosterViewHolder>{

    private static final String TAG = MoviePosterAdapter.class.getSimpleName();

    private int mMoviePosterItems;
    private Context mnActivity;
    private ArrayList<Object> movieList;
    final private ListItemClickListener mOnClickListener;
    Bitmap posterBMP;
    byte[] img;

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
    public void setFavoriteMovieList(List<FavoriteMovie> favoriteMovies){
        if(favoriteMovies !=null) {
            mMoviePosterItems = favoriteMovies.size();
            movieList = new ArrayList<Object>();
            for (int i = 0; i < favoriteMovies.size(); i++) {
                MovieObject fvMv = favoriteMovies.get(i).getMovieObject();
                fvMv.setPosterImage(favoriteMovies.get(i).getPoster());
                movieList.add(fvMv);
            }
        }
    }
    public ArrayList<Object> getMovieList(){
        return movieList;
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
            MovieObject bindMovieObj = ((MovieObject) movieList.get(listIndex));
            Uri favPoster;

             if(listIndex>=0) {
                 if(bindMovieObj.getPosterImage()!=null){
                     img = bindMovieObj.getPosterImage();
                     posterBMP = BitmapFactory.decodeByteArray(img, 0, img.length);
                     favPoster = Helpers.getImgURI(mnActivity, posterBMP);
                     Picasso.with(mnActivity)
                             .load(favPoster)
                             .into(listItemMoviePosterView);
                 }
                 else {
                     Picasso.with(mnActivity)
                             .load(bindMovieObj.getPosterPath())
                             .into(listItemMoviePosterView);
                 }
             }
        }


    }
}
