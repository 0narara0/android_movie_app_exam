package com.narara.android_movie_app_exam.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.narara.android_movie_app_exam.R;

import android.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String imageUrl) {

        String posterPath = "https://image.tmdb.org/t/p/w500" + imageUrl;
        Glide.with(imageView)
                .load(posterPath)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
