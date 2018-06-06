package com.jennings.jadd.popular_movies_stage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jennings.jadd.popular_movies_stage2.models.MovieReviewObject;
import com.jennings.jadd.popular_movies_stage2.models.MovieTrailerObject;

import java.util.ArrayList;


public class MovieTrailerReviewAdapter extends RecyclerView.Adapter <MovieTrailerReviewAdapter.MovieTrailerReviewViewHolder>{

    private static final String TAG = MovieTrailerReviewAdapter.class.getSimpleName();

    private int mMovieTrailerReviewItems;
    private Context mnActivity;

    private ArrayList<Object> movieTrailerReviewList;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public MovieTrailerReviewAdapter( Context main, ListItemClickListener listener){
        mMovieTrailerReviewItems= 0;
        mnActivity = main;
        mOnClickListener = listener;
    }


    public void setMovieTrailerReviewList(ArrayList<Object> mvTrlRevList){
        mMovieTrailerReviewItems= mvTrlRevList.size();
        movieTrailerReviewList = mvTrlRevList;
    }
    public ArrayList<Object> getMovieTrailerReviewList(){
        return movieTrailerReviewList;
    }
    public void onBindViewHolder(MovieTrailerReviewViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    public int getItemCount() {
        return mMovieTrailerReviewItems;
    }

    @Override
    public MovieTrailerReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_detail_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MovieTrailerReviewViewHolder viewHolder = new MovieTrailerReviewViewHolder(view);

        return viewHolder;
    }

    /**
     * Cache of the children views for a list item.
     */
    class MovieTrailerReviewViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener /****/ {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        ImageButton listItemMovieTrailerPlayView;
        TextView listItemMovieTrailerNameView;
        TextView listItemMovieReviewView;


       public void onClick(View view){
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }  /** **/

        public MovieTrailerReviewViewHolder(View itemView) {
            super(itemView);
            listItemMovieTrailerPlayView = (ImageButton) itemView.findViewById(R.id.img_movie_trailer_play);
            listItemMovieTrailerNameView = (TextView) itemView.findViewById(R.id.movie_trailer_name_text);
            listItemMovieReviewView = (TextView) itemView.findViewById(R.id.movie_review_text);
            listItemMovieTrailerPlayView.setOnClickListener(this);
        }


        void bind(int listIndex){
            Object currentIndex = movieTrailerReviewList.get(listIndex);
            if(currentIndex.getClass() == MovieTrailerObject.class){
                listItemMovieTrailerNameView.setText ( ((MovieTrailerObject)currentIndex).getName());
                ViewGroup parent = (ViewGroup) listItemMovieReviewView.getParent();
                if(parent!=null)
                    parent.removeView(listItemMovieReviewView);
            }
            else{
                listItemMovieReviewView.setText ( ((MovieReviewObject)currentIndex).getAuthor() + " : " +((MovieReviewObject)currentIndex).getContent() );
                ViewGroup parent = (ViewGroup) listItemMovieTrailerNameView.getParent();
                if(parent!=null) {
                    parent.removeView(listItemMovieTrailerNameView);
                    parent.removeView(listItemMovieTrailerPlayView);
                }
            }
        }
    }
}
