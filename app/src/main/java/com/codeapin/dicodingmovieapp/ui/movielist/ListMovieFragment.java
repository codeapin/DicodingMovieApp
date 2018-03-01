package com.codeapin.dicodingmovieapp.ui.movielist;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.data.local.DatabaseContract;
import com.codeapin.dicodingmovieapp.data.local.DatabaseHelper;
import com.codeapin.dicodingmovieapp.data.local.MovieHelper;
import com.codeapin.dicodingmovieapp.data.remote.model.ApiResponse;
import com.codeapin.dicodingmovieapp.data.remote.model.MovieItem;
import com.codeapin.dicodingmovieapp.data.remote.service.ApiClient;
import com.codeapin.dicodingmovieapp.ui.moviedetails.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.provider.BaseColumns._ID;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.TABLE_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMovieFragment extends Fragment {

    public static final int SORT_ORDER_UPCOMING = 802;
    public static final int SORT_ORDER_NOW_PLAYING = 571;
    public static final int SORT_ORDER_FAVORIT = 493;

    private static final String TAG = ListMovieFragment.class.getSimpleName();
    private static final String EXTRA_MOVIE_LIST = "MovieList";
    private static final String EXTRA_SORT_ORDER = "SORT_BY";
    public static ListMovieFragment newInstance(int sort) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_SORT_ORDER, sort);
        ListMovieFragment fragment = new ListMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_home)
    RecyclerView rvHome;

    @BindView(R.id.sr_home)
    SwipeRefreshLayout srHome;
    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;
    Unbinder unbinder;

    private MovieAdapter adapter;
    private MovieHelper movieHelper;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiResponse movieList;
    private int sortOrder;

    public ListMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        sortOrder = getArguments().getInt(EXTRA_SORT_ORDER);
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelable(EXTRA_MOVIE_LIST);
        }
        setupView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sortOrder == SORT_ORDER_FAVORIT && movieHelper != null) {
            movieHelper.open();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sortOrder == SORT_ORDER_FAVORIT && movieHelper != null) {
            movieHelper.close();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeDisposable.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE_LIST, movieList);
    }

    private void setupView() {
        adapter = new MovieAdapter(null);
        movieHelper = new MovieHelper(getContext());
        adapter.setMovieOnClickListener(item -> MovieDetailActivity.start(getContext(), item));
        rvHome.setAdapter(adapter);
        rvHome.setLayoutManager(new GridLayoutManager(getContext(), 2));
        srHome.setOnRefreshListener(this::retrieveItems);
        if (movieList != null) {
            adapter.setData(movieList.getResults());
            return;
        }
        retrieveItems();
    }

    private void retrieveItems() {
        Single<ApiResponse> moviesSingle;
        switch (sortOrder) {
            case SORT_ORDER_NOW_PLAYING:
                moviesSingle = ApiClient.getApiService().getNowPlayingMovies();
                break;
            case SORT_ORDER_FAVORIT:
                moviesSingle = getMovieFromProvider();
                break;
            default:
                moviesSingle = ApiClient.getApiService().getUpcomingMovies();
                break;
        }

        moviesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    compositeDisposable.add(disposable);
                    srHome.setRefreshing(true);
                })
                .doFinally(() -> srHome.setRefreshing(false))
                .subscribe(
                        apiResponse -> {
                            adapter.setData(apiResponse.getResults());
                            movieList = apiResponse;
                            if (apiResponse.getResults().size() > 0) {
                                tvErrorMessage.setVisibility(View.GONE);
                            } else {
                                tvErrorMessage.setVisibility(View.VISIBLE);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getContext(), R.string.error_default_mesagge, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "retrieveItems: ", throwable);
                        }
                );
    }

    private Single<ApiResponse> getMovieFromProvider(){
        return Single.fromCallable(
                () -> getActivity()
                        .getContentResolver()
                        .query(DatabaseContract.MovieColumns.CONTENT_URI,
                                null, null,
                                null, null))
                .map(cursor -> {
                    List<MovieItem> movieItemList = new ArrayList<>();
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        do {
                            movieItemList.add(new MovieItem(cursor));
                            cursor.moveToNext();

                        } while (!cursor.isAfterLast());
                    }
                    cursor.close();
                    return movieItemList;
                })
                .map(ApiResponse::new);
    }
}
