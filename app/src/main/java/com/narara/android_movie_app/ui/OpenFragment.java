package com.narara.android_movie_app.ui;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.narara.android_movie_app.R;
import com.narara.android_movie_app.databinding.FragmentOpenBinding;

public class OpenFragment extends Fragment {

    public OpenFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    public static OpenFragment newInstance() {
        OpenFragment fragment = new OpenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentOpenBinding binding = DataBindingUtil.bind(view);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_movie, MovieFragment.newInstance("popular"))
                .addToBackStack(null)
                .commit();

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int id = binding.tabs.getSelectedTabPosition();
                switch (id) {
                    case 0:
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_movie, MovieFragment.newInstance("popular"))
                                .commit();
                        break;
                    case 1:
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_movie, MovieFragment.newInstance("now"))
                                .commit();
                        break;
                    case 2:
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_movie, MovieFragment.newInstance("top"))
                                .commit();
                        break;
                    case 3:
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_movie, MovieFragment.newInstance("upcoming"))
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_top, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container, new SearchFragment())
                        .addToBackStack(null)
                        .commit();
                return true;

        }
        return false;
    }


}
