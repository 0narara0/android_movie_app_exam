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
import com.narara.android_movie_app_exam.ResultEvent;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.viewmodels.DetailViewModel;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentDetailBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class DetailFragment extends Fragment {
    public static final String KEY_MOVIE = "MOVIE";
    private Result mResult;
    private FragmentDetailBinding mBinding;


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
        mBinding = DataBindingUtil.bind(view);

        DetailViewModel model = ViewModelProviders.of(this).get(DetailViewModel.class);
        mBinding.setDetail(mResult);


        // 체크박스
        model.favorites().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> favorites) {
                                        // result 와 db 비교
                if (favorites != null && favorites.contains(mResult)) {
                    mBinding.favoriteCheck.setChecked(true);
                }
                mBinding.favoriteCheck.setOnCheckedChangeListener((buttonView, isChecked)
                        -> model.completeChanged(mResult, isChecked));
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // ShareButton
        mBinding.buttonShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mResult.getTitle());
            startActivity(Intent.createChooser(shareIntent, "movie"));
        });

    }




}

