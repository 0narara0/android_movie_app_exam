package com.narara.android_movie_app_exam;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.narara.android_movie_app_exam.databinding.FragmentMovieBinding;
import com.narara.android_movie_app_exam.models.Movie;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.utils.MovieAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;


public class MovieFragment extends Fragment {

    private MovieAdapter mMovieAdapter;
    private FragmentMovieBinding mBinding;
    private MovieViewModel mModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding = DataBindingUtil.bind(view);
        mModel = ViewModelProviders.of(this).get(MovieViewModel.class);

//        mBinding.setViewModel(mModel);
////        mBinding.setLifecycleOwner(this);

        mMovieAdapter = new MovieAdapter(new MovieAdapter.OnMovieItemSelectedListener() {
            @Override
            public void onItemSelect(Result result) {
                DetailFragment detailFragment = new DetailFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container, detailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mBinding.recyclerView.setAdapter(mMovieAdapter);


        mModel.results.observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {

                mBinding.recyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.setItems(results);
                mMovieAdapter.notifyDataSetChanged();

            }
        });

    }


}
