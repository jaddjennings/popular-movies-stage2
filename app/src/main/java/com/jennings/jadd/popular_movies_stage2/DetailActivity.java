package com.jennings.jadd.popular_movies_stage2;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennings.jadd.popular_movies_stage2.Utilities.Helpers;
import com.jennings.jadd.popular_movies_stage2.Utilities.MovieDetailQueryTask;
import com.jennings.jadd.popular_movies_stage2.Utilities.NetworkUtils;
import com.jennings.jadd.popular_movies_stage2.database.AppDatabase;
import com.jennings.jadd.popular_movies_stage2.database.FavoriteMovie;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.jennings.jadd.popular_movies_stage2.models.MovieTrailerObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailActivity extends AppCompatActivity implements  MovieTrailerReviewAdapter.ListItemClickListener  {


    @BindView(R.id.movie_title_tv) TextView tv_title;
    @BindView(R.id.movie_plot) TextView tv_movie_plot;
    @BindView(R.id.movie_release_date_tv) TextView tv_movie_release_date;
    @BindView(R.id.vote_average) TextView tv_vote_average;
    @BindView(R.id.image_movie_poster_detail) ImageView posterDetail;
    @BindView(R.id.image_button_favorite)     ImageButton isFavorite;

    private int movieId;

    private ArrayList<Object> movieTrailerAndReviewList = new ArrayList<Object>();
    private RecyclerView mvTrailerReviewList;
    private Context mnContext;
    private MovieTrailerReviewAdapter mvTrailerReviewAdapter;
    // Member variable for the Database
    private AppDatabase mDb;
    private MovieObject selectedMovie;
    ByteArrayOutputStream stream;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity == null) {
            closeOnError();
        }


        selectedMovie = intentThatStartedThisActivity.getParcelableExtra("MovieObject");
        if (selectedMovie == null) {
            // selectedMovie data unavailable
            closeOnError();
            return;
        }
        movieId = selectedMovie.getId();

        loadUI(selectedMovie);
        mnContext = this;
        mvTrailerReviewList = (RecyclerView) findViewById(R.id.rv_mv_trailer_review);
        mvTrailerReviewList.setLayoutManager(new LinearLayoutManager(mnContext));
        mvTrailerReviewAdapter = new MovieTrailerReviewAdapter(mnContext,this);
        mvTrailerReviewList.setHasFixedSize(true);
        mvTrailerReviewList.setAdapter(mvTrailerReviewAdapter);

        URL movieDetailReviews= NetworkUtils.buildUrlDetail(1,movieId);
        URL movieDetailTrailers= NetworkUtils.buildUrlDetail(2,movieId);

        new MovieDetailQueryTask(2,movieTrailerAndReviewList, mvTrailerReviewAdapter).execute(movieDetailTrailers);

        new MovieDetailQueryTask(1,movieTrailerAndReviewList, mvTrailerReviewAdapter).execute(movieDetailReviews);


    }
    @OnClick(R.id.image_button_favorite)
    public void toggleButton(ImageButton b) {
        /**todo:use persistent data to **/
//        if(b.getBackground().equals(android.R.drawable.btn_star_big_on)) {
        FavoriteMovie mv = mDb.favoriteMovieDao().loadMovieByMovieId(selectedMovie.getId());
        if(mv!=null) {
            mDb.favoriteMovieDao().deleteMovie(mv);

            isFavorite.setImageResource (android.R.drawable.btn_star_big_off);
        }
        else {

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    return;
                }
            };
            Picasso.with(this)
                    .load(selectedMovie.getPosterPath())
                    .into(target);
            FavoriteMovie newFavMv = new FavoriteMovie(selectedMovie);
            newFavMv.setPoster(stream.toByteArray());
            mDb.favoriteMovieDao().insertMovie(newFavMv);
            isFavorite.setImageResource(android.R.drawable.btn_star_big_on);

        }

    }
    private void loadUI(MovieObject mvObj){

        FavoriteMovie mv = mDb.favoriteMovieDao().loadMovieByMovieId(mvObj.getId());
        MovieObject favMovie=new MovieObject();
        if(mv!=null) {
            isFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            favMovie = mv.getMovieObject();
            favMovie.setPosterImage(mv.getPoster());
            byte[] img =   favMovie.getPosterImage();
            Bitmap posterBMP = BitmapFactory.decodeByteArray(img, 0, img.length);
            Uri favPoster = Helpers.getImgURI(this, posterBMP);
            Picasso.with(this)
                    .load(favPoster)
                    .into(posterDetail);
        }
        else {
            isFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            Picasso.with(this)
                    .load(mvObj.getPosterPath())
                    .into(posterDetail);
        }

        if(mvObj.getTitle().isEmpty()){
            tv_title.setText("N/A");
        }
        else{
            tv_title.setText(mvObj.getTitle());
        }

        if(mvObj.getOverview().isEmpty()){
            tv_movie_plot.setText("N/A");
        }
        else{
            tv_movie_plot.setText(mvObj.getOverview());
        }
        if(mvObj.getRelease_date().isEmpty()){
            tv_movie_release_date.setText("N/A");
        }
        else{
            tv_movie_release_date.setText(mvObj.getRelease_date().substring(0,4));
        }
        if(String.valueOf(mvObj.getVote_average()).isEmpty() ){
            tv_vote_average.setText("N/A");
        }
        else{
            tv_vote_average.setText(String.valueOf(mvObj.getVote_average()) + "/10");
        }
    }


    private void closeOnError() {
        finish();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        MovieTrailerObject selectedTrailer = (MovieTrailerObject) movieTrailerAndReviewList.get(clickedItemIndex);

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + selectedTrailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" +  selectedTrailer.getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}