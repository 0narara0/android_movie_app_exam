package com.narara.android_movie_app.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app.DetailActivity;
import com.narara.android_movie_app.R;
import com.narara.android_movie_app.databinding.FragmentMovieBinding;
import com.narara.android_movie_app.utils.MovieAdapter;
import com.narara.android_movie_app.viewmodels.MovieViewModel;


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


        if (getArguments() != null && getArguments().getString("id") != null) {
            String id = getArguments().getString("id");
            mModel.fetch(id, 1);
        }

        mBinding.setViewModel(mModel);
        mBinding.setLifecycleOwner(this);

        final MovieAdapter movieAdapter = new MovieAdapter(result -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
        });
        mBinding.recyclerView.setAdapter(movieAdapter);
        mModel.results.observe(this, results -> {
            movieAdapter.setItems(results);
            mBinding.swipeRefreshLayout.setRefreshing(false);
        });

        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));


        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollable = mBinding.recyclerView.canScrollVertically(1);
                if (!isScrollable) {
                    if (getArguments() != null) {
                        String id = getArguments().getString("id");
                        mModel.fetch(id, mModel.currentPage + 1);

                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mBinding.fab.setOnClickListener(view -> {
            mModel.sort();
        });

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // SwipeRefreshLayout
        mBinding.swipeRefreshLayout.setOnRefreshListener(() -> {
            if (getArguments() != null) {
                String id = getArguments().getString("id");
                mModel.fetch(id, 1);
            }
        });

    }


}
