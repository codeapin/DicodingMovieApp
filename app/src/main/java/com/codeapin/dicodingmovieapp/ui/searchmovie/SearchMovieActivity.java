package com.codeapin.dicodingmovieapp.ui.searchmovie;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.data.remote.model.ApiResponse;
import com.codeapin.dicodingmovieapp.data.remote.model.MovieItem;
import com.codeapin.dicodingmovieapp.data.remote.service.ApiClient;
import com.codeapin.dicodingmovieapp.ui.moviedetails.MovieDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchMovieActivity extends AppCompatActivity {

    private static final String TAG = SearchMovieActivity.class.getSimpleName();
    public static final String EXTRA_MOVIE_LIST = "MOVIE_LIST";

    @BindView(R.id.sv_movies)
    SearchView svMovies;
    @BindView(R.id.rv_popular_movie)
    RecyclerView rvPopularMovie;
    @BindView(R.id.sr_movie_search)
    SwipeRefreshLayout srMovieSearch;

    private SearchMovieAdapter searchMoviewAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiResponse apiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        ButterKnife.bind(this);

        setupRecyclerView();
        svMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                retrieveItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        if (savedInstanceState != null) {
            apiResponse = savedInstanceState.getParcelable(EXTRA_MOVIE_LIST);
            if (apiResponse != null) {
                searchMoviewAdapter.setData(apiResponse.getResults());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE_LIST, apiResponse);
    }

    private void retrieveItems(String q) {
        ApiClient.getApiService().getMovies(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    compositeDisposable.add(disposable);
                    srMovieSearch.setEnabled(true);
                    srMovieSearch.setRefreshing(true);
                })
                .doFinally(() -> {
                    srMovieSearch.setRefreshing(false);
                    srMovieSearch.setEnabled(false);
                })
                .subscribe(
                        apiResponse -> {
                            this.apiResponse = apiResponse;
                            searchMoviewAdapter.setData(apiResponse .getResults());
                        },
                        throwable -> Log.d(TAG, "retrieveItems: ", throwable));

    }

    private void setupRecyclerView() {
        searchMoviewAdapter = new SearchMovieAdapter();
        searchMoviewAdapter.setItemViewClickListener(item -> MovieDetailActivity.start(this, item));
        rvPopularMovie.setAdapter(searchMoviewAdapter);
        rvPopularMovie.setLayoutManager(new LinearLayoutManager(this));
    }
}
