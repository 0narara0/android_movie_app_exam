package com.narara.android_movie_app_exam.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app_exam.DetailActivity;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentMovieBinding;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.utils.MovieAdapter;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

import java.util.Collections;
import java.util.List;


public class MovieFragment extends Fragment {
    public interface OnMovieItemClickListener {
        void onMovieItemClicked(Result result);
    }

    private OnMovieItemClickListener mListener;

    public void setOnMovieItemClickListener(OnMovieItemClickListener listener) {
        this.mListener = listener;
    }

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
                mModel.fetchPopular(1);
            } else if (getArguments().getString("id").equals("now")) {
                mModel.fetchNow(1);
            } else if (getArguments().getString("id").equals("top")) {
                mModel.fetchTop(1);
            } else if (getArguments().getString("id").equals("upcoming")) {
                mModel.fetchUpcoming(1);
            }
        }

        mBinding.setViewModel(mModel);
        mBinding.setLifecycleOwner(this);

        final MovieAdapter movieAdapter = new MovieAdapter(new MovieAdapter.OnMovieItemSelectedListener() {
            @Override
            public void onItemSelect(Result result) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });


        mBinding.recyclerView.setAdapter(movieAdapter);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollable = mBinding.recyclerView.canScrollVertically(1);
                if (!isScrollable) {
                    if (getArguments() != null) {
                        if (getArguments().getString("id").equals("popular")) {
                            mModel.fetchPopular(mModel.currentPage + 1);
                        } else if (getArguments().getString("id").equals("now")) {
                            mModel.fetchNow(mModel.currentPage + 1);
                        } else if (getArguments().getString("id").equals("top")) {
                            mModel.fetchTop(mModel.currentPage + 1);
                        } else if (getArguments().getString("id").equals("upcoming")) {
                            mModel.fetchUpcoming(mModel.currentPage + 1);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        mModel.results.observe(this, results -> {

            movieAdapter.setItems(results);
            mBinding.recyclerView.setAdapter(movieAdapter);
            mBinding.swipeRefreshLayout.setRefreshing(false);

        });

        mBinding.fab.setOnClickListener(view -> {
            List<Result> resultList = mModel.results.getValue();
            Collections.sort(resultList, (o1, o2) -> o1.getRelease_date().compareTo(o2.getRelease_date()));
            movieAdapter.setItems(resultList);
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
                if (getArguments() != null) {
                    if (getArguments().getString("id").equals("popular")) {
                        mModel.fetchPopular(1);
                    } else if (getArguments().getString("id").equals("now")) {
                        mModel.fetchNow(1);
                    } else if (getArguments().getString("id").equals("top")) {
                        mModel.fetchTop(1);
                    } else if (getArguments().getString("id").equals("upcoming")) {
                        mModel.fetchUpcoming(1);
                    }
                }
            }
        });

    }


}
