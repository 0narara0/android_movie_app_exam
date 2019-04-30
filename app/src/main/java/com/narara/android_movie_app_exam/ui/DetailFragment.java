package com.narara.android_movie_app_exam.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.narara.android_movie_app_exam.MainActivity;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentDetailBinding;

import java.util.List;


public class DetailFragment extends Fragment {
    public static final String KEY_MOVIE = "MOVIE";
    private Result mResult;


    public DetailFragment() {
    }

    public static DetailFragment newInstance(Result result) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_MOVIE, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResult = (Result) getArguments().getSerializable(KEY_MOVIE);
        } else {
            throw new IllegalArgumentException(" result를 반드시 가져야함");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDetailBinding binding = DataBindingUtil.bind(view);

        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        binding.setDetail(mResult);

        // 체크박스
        model.favorites().observe(this, favorites -> {
            if (favorites != null && favorites.contains(mResult)) {
                binding.favoriteCheck.setChecked(true);
            }
            binding.favoriteCheck.setOnCheckedChangeListener((buttonView, isChecked)
                    -> model.completeChanged(mResult, isChecked));
        });

        // ShareButton
        binding.buttonShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mResult.getTitle());
            startActivity(Intent.createChooser(shareIntent, "movie"));
        });

    }


}

