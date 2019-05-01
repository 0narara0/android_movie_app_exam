package com.narara.android_movie_app_exam;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.narara.android_movie_app_exam.models.Result;
import com.narara.android_movie_app_exam.ui.DetailFragment;
import com.narara.android_movie_app_exam.ui.MovieFragment;
import com.narara.android_movie_app_exam.ui.OpenFragment;
import com.narara.android_movie_app_exam.utils.MovieAdapter;
import com.narara.android_movie_app_exam.viewmodels.MovieViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailActivity extends AppCompatActivity {

//    private Result mResult;
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(ResultEvent event) {
//        mResult = event.result;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        Bundle bundle = getIntent().getExtras();
        Result result = (Result) bundle.getSerializable("result");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container, DetailFragment.newInstance(result))
                .commit();

       Toast.makeText(this, "" + bundle.toString(), Toast.LENGTH_LONG).show();
//        Log.d(TAG, "onCreate: " + bundle);
//        DetailFragment fragment = new DetailFragment();
//        fragment.setArguments(mResult);


//        if (result != null) {
//
//        }


    }




//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }


}
