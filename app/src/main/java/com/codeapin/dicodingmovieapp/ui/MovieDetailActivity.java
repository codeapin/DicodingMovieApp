package com.codeapin.dicodingmovieapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.data.model.MovieItem;
import com.codeapin.dicodingmovieapp.util.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE_ITEM = "MovieItem";
    @BindView(R.id.expandedImage)
    ImageView expandedImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.tv_movie_poster)
    ImageView tvMoviePoster;
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_date_release)
    TextView tvDateRelease;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_pouplarity)
    TextView tvPouplarity;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.linearRoot)
    LinearLayout linearRoot;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    private MovieItem mMovieItem;

    public static void start(Context context, MovieItem movieItem) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ITEM, movieItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_MOVIE_ITEM)) {
            finish();
        }
        mMovieItem = intent.getParcelableExtra(EXTRA_MOVIE_ITEM);
        setupActionBar();
        populateFields();
    }

    private void populateFields() {
        linearRoot.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);

        Glide.with(this)
                .load(ApiClient.BASE_IMAGE_URL_W500 + mMovieItem.getBackdropPath())
                .into(expandedImage);
        Glide.with(this)
                .load(ApiClient.BASE_IMAGE_URL_W185 + mMovieItem.getPosterPath())
                .into(tvMoviePoster);

        tvOverview.setText(mMovieItem.getOverview());
        tvMovieTitle.setText(mMovieItem.getTitle());
        tvDateRelease.setText("Released: " + mMovieItem.getReleaseDate());
        tvRating.setText("Rating: " + String.valueOf(mMovieItem.getVoteAverage()) + " of " +
                String.valueOf(mMovieItem.getVoteCount()) + " vote");
        tvPouplarity.setText("Popularity: " + String.valueOf(mMovieItem.getPopularity()));
    }

    private void setupActionBar() {
        collapsingToolbar.setTitle(mMovieItem.getTitle());
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
