package com.narara.android_movie_app.ui;


import android.app.PendingIntent;
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

import com.narara.android_movie_app.R;
import com.narara.android_movie_app.SplashActivity;
import com.narara.android_movie_app.databinding.FragmentAlarmBinding;
import com.narara.android_movie_app.utils.MovieAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AlarmFragment extends Fragment {

    public AlarmFragment() {
    }

    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAlarmBinding binding = FragmentAlarmBinding.bind(view);
        MovieAdapter adapter = new MovieAdapter();
        binding.recyclerView.setAdapter(adapter);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar time = Calendar.getInstance();
        String todayDate = format.format(time.getTime());

        binding.buttonCreate.setOnClickListener(v -> {
            showNotification(requireContext(), "영화 알림", 1);

        });
        binding.buttonRemove.setOnClickListener(v -> {
            notificationHide();
        });
        binding.buttonAlarm.setOnClickListener(this::showAlarmDialog);

    }

    public void showAlarmDialog(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getChildFragmentManager(), "timePicker");

    }

    public static void showNotification(Context context, String content, int id) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] vibrate = {0, 100, 200, 300};
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("영화 알림")
                .setContentText(content)
                .setSound(RingtoneManager.getActualDefaultRingtoneUri(
                        context, RingtoneManager.TYPE_NOTIFICATION))
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
