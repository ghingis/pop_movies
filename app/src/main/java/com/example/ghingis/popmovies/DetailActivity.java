package com.example.ghingis.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghingis.popmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private ImageView ivBackdrop;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvRate;
    private TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intentThatStartedThisActivity = getIntent();

        ivPoster = (ImageView)findViewById(R.id.iv_detail_poster);
        ivBackdrop = (ImageView)findViewById(R.id.iv_detail_backdrop);
        tvTitle = (TextView)findViewById(R.id.tv_detail_title);
        tvReleaseDate = (TextView)findViewById(R.id.tv_detail_release_date);
        tvRate = (TextView)findViewById(R.id.tv_detail_rate);
        tvOverview = (TextView)findViewById(R.id.tv_detail_overview);

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("com.example.ghingis.popmovies.MovieModel")) {
                MovieModel movie = intentThatStartedThisActivity.getParcelableExtra("com.example.ghingis.popmovies.MovieModel");

                Picasso.with(getApplicationContext())
                        .load(NetworkUtils.IMAGE_URL+movie.getPosterPath())
                        .placeholder(R.drawable.art_clouds)
                        .fit()
                        .into(ivPoster);

                Picasso.with(getApplicationContext())
                        .load(NetworkUtils.IMAGE_BACKDROP_URL+movie.getBackdropPath())
                        .placeholder(R.drawable.art_clouds)
                        .fit()
                        .into(ivBackdrop);

                tvTitle.setText(movie.getOriginalTitle());

                tvReleaseDate.setText(movie.getReleaseDate());

                /* It just felt right either way */
                tvRate.setText(String.valueOf(movie.getRating()) + "/10");

                tvOverview.setText(movie.getOverview());
            }
        }

    }

}
