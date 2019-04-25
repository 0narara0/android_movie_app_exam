package com.narara.android_movie_app_exam.ui;

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

import com.narara.android_movie_app_exam.MainActivity;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentDetailBinding;


public class DetailFragment extends Fragment {
    public static final String KEY_MOVIE = "MOVIE";
    Result mResult;


    public DetailFragment() {
        // Required empty public constructor

        // 메뉴를 가진다고 알려줌
        setHasOptionsMenu(true);
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
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDetailBinding binding = DataBindingUtil.bind(view);

        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        binding.setDetail(mResult);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, mResult.getTitle());
                startActivity(Intent.createChooser(shareIntent, "movie"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

