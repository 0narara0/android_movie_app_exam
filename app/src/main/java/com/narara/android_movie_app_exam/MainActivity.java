package com.narara.android_movie_app_exam;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.narara.android_movie_app_exam.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 서치뷰의 내용으로 검색을 수행할 때 호출됨
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 서치뷰의 글자가 변경될 때마다 호출됨
                return true;
            }
        });

    }

}
