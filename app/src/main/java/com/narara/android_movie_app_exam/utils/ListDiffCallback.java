package com.narara.android_movie_app_exam.utils;

import android.support.v7.util.DiffUtil;

import com.narara.android_movie_app_exam.models.Result;

import java.util.List;

public class ListDiffCallback extends DiffUtil.Callback {
    private final List<Result> oldList;
    private final List<Result> newList;

    public ListDiffCallback(List<Result> oldList, List<Result> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() ==
                newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Result oldItem = oldList.get(oldItemPosition);
        Result newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
