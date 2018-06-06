package com.jennings.jadd.popular_movies_stage2;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennings.jadd.popular_movies_stage2.Utilities.MovieDetailQueryTask;
import com.jennings.jadd.popular_movies_stage2.Utilities.NetworkUtils;
import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.jennings.jadd.popular_movies_stage2.models.MovieTrailerObject;
import com.squareup.picasso.Picasso;

import java.net.URL;
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

    private int movieId;

    private ArrayList<Object> movieTrailerAndReviewList = new ArrayList<Object>();
    private RecyclerView mvTrailerReviewList;
    private Context mnContext;
    private MovieTrailerReviewAdapter mvTrailerReviewAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity == null) {
            closeOnError();
        }

        MovieObject selectedMovie;
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
    @OnClick(R.id.imageButton)
    public void toggleButton(ImageButton b) {
        /**todo:use persistent data to **/
        if(b.getBackground().equals(android.R.drawable.btn_star_big_on))
            b.setBackgroundResource(android.R.drawable.btn_star_big_off);
        else
            b.setBackgroundResource(android.R.drawable.btn_star_big_on);
      }
    private void loadUI(MovieObject mvObj){

        Picasso.with(this)
                .load(mvObj.getPosterPath())
                .into(posterDetail);

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