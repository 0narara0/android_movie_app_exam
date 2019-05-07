package com.narara.android_movie_app_exam;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.narara.android_movie_app_exam.databinding.ActivityMainBinding;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.ui.AlarmFragment;
import com.narara.android_movie_app_exam.ui.FavoriteFragment;
import com.narara.android_movie_app_exam.ui.MovieFragment;
import com.narara.android_movie_app_exam.ui.OpenFragment;
import com.narara.android_movie_app_exam.ui.TimePickerFragment;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolBar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container, OpenFragment.newInstance())
                .commit();

        createNotificationChannel();

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.home_menu:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, OpenFragment.newInstance())
                            .commit();
                    return true;
                case R.id.favorite_menu:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, FavoriteFragment.newInstance())
                            .commit();
                    return true;
                case R.id.alarm_menu:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, AlarmFragment.newInstance())
                            .commit();
                    return true;

            }
            return false;
        });


    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "default";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
