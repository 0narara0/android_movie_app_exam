package com.narara.android_movie_app_exam.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentSearchBinding;
import com.narara.android_movie_app_exam.utils.MovieAdapter;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding mBinding;
    private MovieViewModel mModel;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mBinding = DataBindingUtil.bind(view);
        mModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        final MovieAdapter adapter = new MovieAdapter(result ->
                SearchFragment.this.requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, DetailFragment.newInstance(result))
                        .addToBackStack(null)
                        .commit());
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mModel.results.observe(this, results -> {
            mBinding.recyclerView.setAdapter(adapter);
            adapter.setItems(results);
        });
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {
                    mModel.fetchSearch(s);
                    mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            boolean isScrollable = recyclerView.canScrollVertically(1);
                            if (!isScrollable) {
                                mModel.fetchSearch(s, mModel.currentPage + 1);
                            }

                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mBinding.searchView.setOnCloseListener(() -> false);
    }

}
