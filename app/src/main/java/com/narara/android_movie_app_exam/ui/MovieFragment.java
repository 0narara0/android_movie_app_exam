package com.narara.android_movie_app_exam.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app_exam.utils.MovieAdapter;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentMovieBinding;
import com.narara.android_movie_app_exam.models.Result;

import java.util.List;


public class MovieFragment extends Fragment {

    private FragmentMovieBinding mBinding;
    private MovieViewModel mModel;

    public MovieFragment() {
    }

    public static MovieFragment newInstance(String id) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        mModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        if (getArguments() != null) {
            if (getArguments().getString("id").equals("popular")) {
                mModel.fetchPopular();
            } else if (getArguments().getString("id").equals("now")) {
                mModel.fetchNow();
            } else if (getArguments().getString("id").equals("top")) {
                mModel.fetchTop();
            } else if (getArguments().getString("id").equals("upcoming")) {
                mModel.fetchUpcoming();
            }
        }

        mBinding.setViewModel(mModel);
        mBinding.setLifecycleOwner(this);

        final MovieAdapter movieAdapter = new MovieAdapter(new MovieAdapter.OnMovieItemSelectedListener() {
            @Override
            public void onItemSelect(Result result) {
                MovieFragment.this.requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_movie, DetailFragment.newInstance(result))
                        .addToBackStack(null)
                        .commit();
            }
        });
        mBinding.recyclerView.setAdapter(movieAdapter);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        mModel.results.observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {

                movieAdapter.setItems(results);
                mBinding.recyclerView.setAdapter(movieAdapter);
                mBinding.swipeRefreshLayout.setRefreshing(false);
                movieAdapter.notifyDataSetChanged();

            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // SwipeRefreshLayout
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mModel.fetchUpcoming();
            }
        });
    }


}
