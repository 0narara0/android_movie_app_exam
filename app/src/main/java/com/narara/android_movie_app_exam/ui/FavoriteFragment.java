package com.narara.android_movie_app_exam.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.databinding.FragmentFavoriteBinding;
import com.narara.android_movie_app_exam.databinding.ItemFavoriteBinding;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.utils.ListDiffCallback;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding mBinding;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mBinding = DataBindingUtil.bind(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieViewModel viewModel = ViewModelProviders.of(requireActivity())
                .get(MovieViewModel.class);

        FavoriteAdapter adapter = new FavoriteAdapter(model -> {
            // DetailFragment 이동
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, DetailFragment.newInstance(model))
                    .addToBackStack(null)
                    .commit();
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Result favorite = adapter.mItems.get(viewHolder.getAdapterPosition());
                viewModel.deleteFavorite(favorite);
            }
        });
        helper.attachToRecyclerView(mBinding.recyclerView);


        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        viewModel.favorites().observe(this, adapter::updateItems);

        mBinding.fab.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container, new SearchFragment())
                .addToBackStack(null)
                .commit());

    }

    public static class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
        interface OnFavoriteClickListener {
            void onFavoriteClicked(Result model);
        }

        private OnFavoriteClickListener mListener;

        private List<Result> mItems = new ArrayList<>();

        public FavoriteAdapter() {
        }

        public FavoriteAdapter(OnFavoriteClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Result> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        public void updateItems(List<Result> items) {
            new Thread(() -> {
                final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ListDiffCallback(this.mItems, items));

                mItems.clear();
                mItems.addAll(items);

                new Handler(Looper.getMainLooper()).post(() -> result.dispatchUpdatesTo(this));
            }).start();
        }

        @NonNull
        @Override
        public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_favorite, parent, false);
            final FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);
            view.setOnClickListener(v -> {
                if (mListener != null) {
                    final Result item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onFavoriteClicked(item);
                }
            });
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
            Result item = mItems.get(position);
            holder.binding.setFavorite(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
            ItemFavoriteBinding binding;

            public FavoriteViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemFavoriteBinding.bind(itemView);
            }
        }
    }
}
