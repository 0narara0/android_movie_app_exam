package com.narara.android_movie_app.utils;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app.R;
import com.narara.android_movie_app.databinding.ItemMovieBinding;
import com.narara.android_movie_app.models.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public interface OnMovieItemSelectedListener {
        void onItemSelect(Result model);
    }

    private OnMovieItemSelectedListener mListener;

    private List<Result> mItems = new ArrayList<>();

    public MovieAdapter() {
    }

    public MovieAdapter(OnMovieItemSelectedListener listener) {
        mListener = listener;
    }

    public void setItems(List<Result> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setOnClickListener(v -> {
            if (mListener != null) {
                final Result item = mItems.get(viewHolder.getAdapterPosition());
                mListener.onItemSelect(item);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Result result = mItems.get(position);
        holder.binding.setResult(result);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        public MovieViewHolder(@NonNull View itemView) {

            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }
    }
}
