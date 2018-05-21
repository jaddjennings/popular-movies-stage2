package com.jennings.jadd.popular_movies_stage2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jennings.jadd.popular_movies_stage2.models.MovieObject;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.movie_title_tv) TextView tv_title;
    @BindView(R.id.movie_plot) TextView tv_movie_plot;
    @BindView(R.id.movie_release_date_tv) TextView tv_movie_release_date;
    @BindView(R.id.vote_average) TextView tv_vote_average;
    @BindView(R.id.image_movie_poster_detail) ImageView posterDetail;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
     /**   ToggleButton toggle = new ToggleButton(this);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle.setButtonDrawable(android.R.drawable.btn_star_big_on);
                } else {
                    toggle.setButtonDrawable(android.R.drawable.btn_star_big_off);
                }
            }
        });
        **/



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
    @OnClick(R.id.imageButton)
    public void toggleButton(ImageButton b) {
        /**use persistent data to **/
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
            tv_movie_release_date.setText(mvObj.getRelease_date());
        }
        if(String.valueOf(mvObj.getVote_average()).isEmpty() ){
            tv_vote_average.setText("N/A");
        }
        else{
            tv_vote_average.setText(String.valueOf(mvObj.getVote_average()));
        }
    }


    private void closeOnError() {
        finish();
    }
}