package com.narara.android_movie_app.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app.AlarmReceiver;
import com.narara.android_movie_app.R;
import com.narara.android_movie_app.databinding.FragmentDetailBinding;
import com.narara.android_movie_app.models.Result;
import com.narara.android_movie_app.viewmodels.MovieViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;


public class DetailFragment extends Fragment {
    public static final String KEY_MOVIE = "MOVIE";
    private Result mResult;
    private FragmentDetailBinding mBinding;


    public DetailFragment() {
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
            throw new IllegalArgumentException(" result 를 반드시 가져야함");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mBinding = DataBindingUtil.bind(view);

        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        mBinding.setDetail(mResult);


        // 체크박스
        model.favorites().observe(this, favorites -> {
            // result 와 db 비교
            if (favorites != null && favorites.contains(mResult)) {
                mBinding.favoriteCheck.setChecked(true);
            }
            mBinding.favoriteCheck.setOnCheckedChangeListener((buttonView, isChecked)
                    -> model.completeChanged(mResult, isChecked));
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ShareButton
        mBinding.buttonShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mResult.getTitle());
            startActivity(Intent.createChooser(shareIntent, "movie"));
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_alarm, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_alarm) {
            registerAlarm();
        }
        return true;
    }

    private Calendar setAlarmTime(int hour, int minutes, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    public void registerAlarm() {
        AlarmManager alarm_manager = (AlarmManager) requireContext().getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 07);
        calendar.set(Calendar.MINUTE, 24);
        calendar.set(Calendar.SECOND, 30);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date strDate = sdf.parse(mResult.getRelease_date());
            if (strDate.after(new Date())) {
                Intent intent = new Intent(requireActivity(), AlarmReceiver.class);
                intent.putExtra("date", mResult.getRelease_date());
                intent.putExtra("text", mResult.getTitle());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(requireActivity(), 0, intent, 0);

                alarm_manager.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

