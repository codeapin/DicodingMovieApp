package com.codeapin.dicodingmovieapp.ui.movielist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.data.remote.model.MovieItem;
import com.codeapin.dicodingmovieapp.data.remote.service.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daprin on 29/08/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private List<MovieItem> mMovieItemList;
    private MovieOnClickListener mMovieOnClickListener;

    public MovieAdapter(List<MovieItem> mMovieItemList) {
        this.mMovieItemList = mMovieItemList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (mMovieItemList != null) {
            holder.bind(mMovieItemList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mMovieItemList != null ? mMovieItemList.size() : 0;
    }

    public void setData(List<MovieItem> movieItemList){
        mMovieItemList = movieItemList;
        notifyDataSetChanged();
    }

    public void setMovieOnClickListener(MovieOnClickListener listener){
        mMovieOnClickListener = listener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_image)
        ImageView ivMovieImage;
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_rating)
        TextView tvRating;


        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final MovieItem movieItem) {
            String date = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date d = sdf.parse(movieItem.getReleaseDate());
                sdf.applyPattern("yyyy");
                date = sdf.format(d);
            } catch (ParseException ex) {
                Log.e(TAG, ex.getMessage(), ex);
            }

            tvMovieTitle.setText(movieItem.getTitle());
            tvYear.setText(date);
            tvRating.setText(String.valueOf(movieItem.getVoteAverage()));
            Glide.with(ivMovieImage.getContext())
                    .load(ApiClient.BASE_IMAGE_URL_W500 + movieItem.getPosterPath())
                    .into(ivMovieImage);

            itemView.setOnClickListener(v -> {
                if(mMovieOnClickListener != null){
                    mMovieOnClickListener.onClick(movieItem);
                }
            });
        }
    }

    public interface MovieOnClickListener{
        void onClick(MovieItem item);
    }
}
