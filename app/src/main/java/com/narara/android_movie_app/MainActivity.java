package com.narara.android_movie_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.narara.android_movie_app.databinding.ActivityMainBinding;
import com.narara.android_movie_app.ui.FavoriteFragment;
import com.narara.android_movie_app.ui.OpenFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");


        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

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
//                case R.id.alarm_menu:
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frag_container, AlarmFragment.newInstance())
//                            .commit();
//                    return true;

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
