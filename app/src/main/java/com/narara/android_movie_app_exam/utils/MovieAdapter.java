package com.narara.android_movie_app_exam.utils;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.ItemMovieBinding;
import com.narara.android_movie_app_exam.models.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public interface OnMovieItemSelectedListener {
        void onItemSelect(Result model);
    }


    private OnMovieItemSelectedListener mListener;

    private List<Result> mItems = new ArrayList<>();


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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final Result item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onItemSelect(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Result result = mItems.get(position);
        // TODO : 데이터를 뷰홀더에 표시하시오


//        String url = result.getPoster_path();
//        String posterPath = "https://image.tmdb.org/t/p/w500" + url;
//        Glide.with(holder.itemView)
//                .load(posterPath)
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(holder.binding.movieImage);


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