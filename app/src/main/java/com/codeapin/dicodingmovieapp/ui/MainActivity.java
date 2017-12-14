package com.codeapin.dicodingmovieapp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.data.model.APIResponse;
import com.codeapin.dicodingmovieapp.util.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_MOVIE_LIST = "MovieList";
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.sr_home)
    SwipeRefreshLayout srHome;
    @BindView(R.id.tv_error_message)
    TextView tvError;

    private MovieAdapter mMovieAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private APIResponse mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mMovieList = savedInstanceState.getParcelable(EXTRA_MOVIE_LIST);
        }

        init();
    }

    private void init() {
        mMovieAdapter = new MovieAdapter(null);
        mMovieAdapter.setMovieOnClickListener(item -> MovieDetailActivity.start(MainActivity.this, item));
        rvHome.setAdapter(mMovieAdapter);
        rvHome.setLayoutManager(new GridLayoutManager(this, 2));
        srHome.setOnRefreshListener(this);
        if (mMovieList != null) {
            mMovieAdapter.setData(mMovieList.getResults());
            return;
        }
        retrieveItems();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private void searchMovie(String keyword) {
        Single<APIResponse> single = ApiClient.getApiService().getMovies(keyword);
        subscribeRequest(single);
    }

    private void retrieveItems() {
        Single<APIResponse> single = ApiClient.getApiService().getMovies();
        subscribeRequest(single);
    }

    private void subscribeRequest(Single<APIResponse> single) {
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mCompositeDisposable.add(disposable);
                    srHome.setRefreshing(true);
                })
                .doFinally(() -> srHome.setRefreshing(false))
                .subscribe(
                        apiResponse -> {
                            mMovieAdapter.setData(apiResponse.getResults());
                            mMovieList = apiResponse;
                            if(apiResponse.getResults().size() > 0){
                                tvError.setVisibility(View.GONE);
                            }else{
                                tvError.setVisibility(View.VISIBLE);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "retrieveItems: ", throwable);
                        }
                );

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE_LIST, mMovieList);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    @Override
    public void onRefresh() {
        retrieveItems();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchMovie(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
