package com.narara.android_movie_app_exam.ui;


import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app_exam.MainActivity;
import com.narara.android_movie_app_exam.R;
import com.narara.android_movie_app_exam.SplashActivity;
import com.narara.android_movie_app_exam.databinding.FragmentAlarmBinding;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AlarmFragment extends Fragment {
    private MovieViewModel mModel;

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;


    public AlarmFragment() {
        // Required empty public constructor
    }


    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        mModel = ViewModelProviders.of(this).get(MovieViewModel.class);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAlarmBinding binding = FragmentAlarmBinding.bind(view);

//        List<Result> dateList = new ArrayList<>();
//        dateList = mModel.results.getValue();
//        Collections.sort(dateList, (o1, o2) -> o1.getRelease_date().compareTo(o2.getRelease_date()));
//        //movieAdapter.setItems(resultList);


        binding.buttonCreate.setOnClickListener(v -> {
            showNotification(requireContext(), "미개봉 영화", 1);

        });
        binding.buttonRemove.setOnClickListener(v -> {
            notificationHide();
        });
        binding.buttonAlarm.setOnClickListener(v -> showAlarmDialog(v));

    }

    public void showAlarmDialog(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getChildFragmentManager(), "timePicker");
        showNotification(requireContext(), "미개봉 영화", 1);
    }

    public static void showNotification(Context context, String content, int id) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] vibrate = {0, 100, 200, 300};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("영화 알림")
                .setContentText(content)
                .setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setColor(Color.RED)
                .setVibrate(vibrate)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(id, builder.build());
    }


    public void notificationHide() {
        // 알림 해제
        NotificationManagerCompat.from(requireContext()).cancel(1);
    }
}
