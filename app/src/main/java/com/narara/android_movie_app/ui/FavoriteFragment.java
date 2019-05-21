package com.narara.android_movie_app.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app.DetailActivity;
import com.narara.android_movie_app.R;
import com.narara.android_movie_app.databinding.FragmentFavoriteBinding;
import com.narara.android_movie_app.databinding.ItemFavoriteBinding;
import com.narara.android_movie_app.models.Result;
import com.narara.android_movie_app.utils.ItemTouchHelperAdapter;
import com.narara.android_movie_app.utils.ListDiffCallback;
import com.narara.android_movie_app.utils.SimpleItemTouchHelperCallback;
import com.narara.android_movie_app.utils.SwipeControllerActions;
import com.narara.android_movie_app.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private boolean isSwapped = false;
    private FragmentFavoriteBinding mBinding;
    private MovieViewModel mModel;
    private FavoriteAdapter mAdapter;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
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
        mModel = ViewModelProviders.of(requireActivity())
                .get(MovieViewModel.class);

        mAdapter = new FavoriteAdapter(model -> {
            // DetailFragment 이동
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("result", model);
            startActivity(intent);
        });

        // 선생님 code

       /* ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Result favorite = mAdapter.mItems.get(viewHolder.getAdapterPosition());
                mModel.deleteFavorite(favorite);
                mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mBinding.recyclerView);*/


        ItemTouchHelperAdapter itemTouchHelperAdapter = new ItemTouchHelperAdapter() {
            @Override
            public boolean onItemMove(int fromPosition, int toPosition) {

                mAdapter.onItemMove(fromPosition, toPosition);

                isSwapped = true;
                return false;
            }


            @Override
            public void onItemDismiss(int position) {
                Result favorite = mAdapter.mItems.get(position);
                mModel.deleteFavorite(favorite);
                mAdapter.onItemDismiss(position);
            }

        };
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemTouchHelperAdapter);
        SimpleItemTouchHelperCallback simpleItemTouchHelperCallback = new SimpleItemTouchHelperCallback
                (new SwipeControllerActions() {
                    @Override
                    public void onLeftClicked(int position) {
                        super.onLeftClicked(position);

                    }

                    @Override
                    public void onRightClicked(int position) {
                        mAdapter.mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                    }
                });
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mBinding.recyclerView);


        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));

        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                simpleItemTouchHelperCallback.onDraw(canvas);
            }
        });

        mModel.getFavorites().observe(this, items -> {
            mAdapter.updateItems(items);
            mModel.resultList = items;
            mModel.filteredResults.setValue(items);

            mBinding.fab.setOnClickListener(v -> {
                Collections.sort(items, (o1, o2) ->
                        o1.getRelease_date().compareTo(o2.getRelease_date()));
                mAdapter.updateItems(items);
            });


        });

        mModel.filteredResults.observe(this, results -> {
            mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    mBinding.fab.setEnabled(false);
                    if (!TextUtils.isEmpty(query)) {
                        mModel.search(query);
                        mAdapter.setItems(mModel.filteredResults.getValue());
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!TextUtils.isEmpty(newText)) {
                        mModel.search(newText);
                        mAdapter.setItems(mModel.filteredResults.getValue());
                    }
                    return true;
                }
            });


            mBinding.searchView.setOnCloseListener(() -> {
                mAdapter.setItems(mModel.resultList);
                return false;
            });
        });
    }


    public static class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>
            implements ItemTouchHelperAdapter {


        interface OnFavoriteClickListener {
            void onFavoriteClicked(Result model);
        }

        private OnFavoriteClickListener mListener;

        private List<Result> mItems = new ArrayList<>();
        // Collections.synchronizedList(new ArrayList());


        public FavoriteAdapter(OnFavoriteClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Result> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        public List<Result> getItems() {
            return mItems;
        }

        public void updateItems(List<Result> items) {
//            mItems.clear();
//            mItems.addAll(items);
//            notifyDataSetChanged();

            // synchronized(mItems)
            new Thread(() -> {
                final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                        new ListDiffCallback(this.mItems, items));

                mItems.clear();
                mItems.addAll(items);

                new Handler(Looper.getMainLooper()).post(() -> result.dispatchUpdatesTo(this));
            }).start();
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mItems, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mItems, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
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

    @Override
    public void onPause() {
        super.onPause();

        if (isSwapped) {
            mModel.update(mAdapter.getItems());
        }
    }
}
