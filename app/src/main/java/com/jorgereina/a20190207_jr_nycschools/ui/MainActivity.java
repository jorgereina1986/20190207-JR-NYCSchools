package com.jorgereina.a20190207_jr_nycschools.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jorgereina.a20190207_jr_nycschools.R;
import com.jorgereina.a20190207_jr_nycschools.SchoolViewModel;
import com.jorgereina.a20190207_jr_nycschools.data.School;
import com.jorgereina.a20190207_jr_nycschools.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private SchoolViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        loadListFragment();

        viewModel = ViewModelProviders.of(this).get(SchoolViewModel.class);

    }

    private void loadListFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isNetworkAvailable()) {
            hideOfflineViews();
            if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
                ListFragment listFragment = ListFragment.newInstance();
                fragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, listFragment, "listFragment")
                        .commit();
            }
        } else {
            showOfflineViews();
        }
    }

    private void showOfflineViews() {
        binding.noInternetTv.setVisibility(View.VISIBLE);
        binding.wifiLogo.setVisibility(View.VISIBLE);
    }

    private void hideOfflineViews() {
        binding.noInternetTv.setVisibility(View.GONE);
        binding.wifiLogo.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onItemClick(final int position) {

        viewModel.getSchools().observe(this, new Observer<List<School>>() {
            @Override
            public void onChanged(@Nullable List<School> schools) {
                Log.d(TAG, "onChanged: " + schools.get(position));
                viewModel.setSchool(schools.get(position));
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DetailsFragment.getInstance())
                .addToBackStack(null)
                .commit();
    }
}
