package com.codeapin.dicodingmovieapp.ui.searchmovie;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeapin.dicodingmovieapp.R;
import com.codeapin.dicodingmovieapp.data.remote.model.MovieItem;
import com.codeapin.dicodingmovieapp.data.remote.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {

    private List<MovieItem> dataSet = new ArrayList<>();
    private ItemViewClickListener itemViewClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieItem item = dataSet.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setData(@NonNull List<MovieItem> data) {
        dataSet = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    public void setItemViewClickListener(ItemViewClickListener listener) {
        itemViewClickListener = listener;
    }

    public interface ItemViewClickListener {
        void onItemViewClick(MovieItem item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_image)
        CircleImageView ivMovieImage;
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;
        @BindView(R.id.linearSearchMovie)
        LinearLayout linearSearchMovie;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MovieItem item) {
            Glide.with(ivMovieImage.getContext())
                    .load(ApiClient.BASE_IMAGE_URL_W185 + item.getPosterPath())
                    .into(ivMovieImage);
            tvMovieTitle.setText(item.getTitle());
            itemView.setOnClickListener(v -> {
                if (itemViewClickListener != null) {
                    itemViewClickListener.onItemViewClick(item);
                }
            });
        }
    }
}