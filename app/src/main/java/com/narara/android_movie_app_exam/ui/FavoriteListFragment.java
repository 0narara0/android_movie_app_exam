package com.narara.android_movie_app_exam.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentFavoriteListBinding;
import com.narara.android_movie_app_exam.utils.MovieAdapter;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

public class FavoriteListFragment extends Fragment {

    private FragmentFavoriteListBinding mBinding;
    private MovieViewModel mModel;

    public FavoriteListFragment() {
        // Required empty public constructor
    }


    public static FavoriteListFragment newInstance() {
        FavoriteListFragment fragment = new FavoriteListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        mBinding = DataBindingUtil.bind(view);
        mModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        MovieAdapter adapter = new MovieAdapter(model ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_movie, DetailFragment.newInstance(model))
                        .addToBackStack(null)
                        .commit());

            mModel.results.observe(this, results -> {
            mBinding.recyclerView.setAdapter(adapter);
            mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            adapter.setItems(results);
            adapter.notifyDataSetChanged();

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
