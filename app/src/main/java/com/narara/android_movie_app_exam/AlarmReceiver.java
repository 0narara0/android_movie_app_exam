package com.narara.android_movie_app_exam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.narara.android_movie_app_exam.ui.AlarmFragment.showNotification;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String date = intent.getStringExtra("date");
        String text = intent.getStringExtra("text");
        String content = date + "에 " + text + "가 개봉합니다";

        // 노티
        showNotification(context, content, 1);
    }
}
