package com.narara.android_movie_app_exam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.narara.android_movie_app_exam.databinding.ActivityMainBinding;
import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.ui.FavoriteListFragment;
import com.narara.android_movie_app_exam.ui.HomeFragment;
import com.narara.android_movie_app_exam.ui.SearchFragment;

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
                            .replace(R.id.frag_container, FavoriteListFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.alarm_menu:
                    return true;

            }
            return false;
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container, new SearchFragment())
                        .addToBackStack(null)
                        .commit();
                return true;


            case R.id.action_sort:
                return true;
        }
        return true;
    }*/
}
