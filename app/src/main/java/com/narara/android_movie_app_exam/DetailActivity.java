package com.narara.android_movie_app_exam;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.ui.DetailFragment;
import com.narara.android_movie_app_exam.ui.MovieFragment;
import com.narara.android_movie_app_exam.ui.OpenFragment;
import com.narara.android_movie_app_exam.ui.TimePickerFragment;
import com.narara.android_movie_app_exam.utils.MovieAdapter;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Result result = (Result) getIntent().getSerializableExtra("result");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container, DetailFragment.newInstance(result))
                .commit();

    }

}
