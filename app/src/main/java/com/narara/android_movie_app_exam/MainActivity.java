package com.narara.android_movie_app_exam;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.narara.android_movie_app_exam.databinding.ActivityMainBinding;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.ui.FavoriteFragment;
import com.narara.android_movie_app_exam.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Result mResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolBar);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.home_menu:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, HomeFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.favorite_menu:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container, FavoriteFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.alarm_menu:
                    return true;

            }
            return false;
        });
    }


}
