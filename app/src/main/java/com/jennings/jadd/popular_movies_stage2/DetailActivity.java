package com.jennings.jadd.popular_movies_stage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class DetailActivity extends AppCompatActivity {

    private ImageView posterDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        posterDetail = (ImageView) findViewById(R.id.image_movie_poster_detail);
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
        loadUI(selectedMovie);

    }
    private void loadUI(MovieObject mvObj){

        Picasso.with(this)
                .load(mvObj.getPosterPath())
                .into(posterDetail);

        TextView tv = (TextView)findViewById(R.id.movie_title_tv);
        if(mvObj.getTitle().isEmpty()){
            tv.setText("N/A");
        }
        else{
            tv.setText(mvObj.getTitle());
        }
        tv = (TextView)findViewById(R.id.movie_plot);
        if(mvObj.getOverview().isEmpty()){
            tv.setText("N/A");
        }
        else{
            tv.setText(mvObj.getOverview());
        }
        tv = (TextView)findViewById(R.id.movie_release_date_tv);
        if(mvObj.getRelease_date().isEmpty()){
            tv.setText("N/A");
        }
        else{
            tv.setText(mvObj.getRelease_date());
        }
        tv = (TextView)findViewById(R.id.vote_average);
        if(String.valueOf(mvObj.getVote_average()).isEmpty() ){
            tv.setText("N/A");
        }
        else{
            tv.setText(String.valueOf(mvObj.getVote_average()));
        }
    }

    private void closeOnError() {
        finish();
    }
}