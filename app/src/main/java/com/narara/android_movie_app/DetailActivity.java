package com.narara.android_movie_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.narara.android_movie_app.models.Result;
import com.narara.android_movie_app.ui.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Result result = (Result) getIntent().getSerializableExtra("result");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, DetailFragment.newInstance(result))
                    .commit();
        }

    }

}
