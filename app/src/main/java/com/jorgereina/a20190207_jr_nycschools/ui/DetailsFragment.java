package com.jorgereina.a20190207_jr_nycschools.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgereina.a20190207_jr_nycschools.R;
import com.jorgereina.a20190207_jr_nycschools.SchoolViewModel;
import com.jorgereina.a20190207_jr_nycschools.data.School;
import com.jorgereina.a20190207_jr_nycschools.data.Score;
import com.jorgereina.a20190207_jr_nycschools.databinding.FragmentDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private FragmentDetailsBinding binding;
    private List<Score> scoreList = new ArrayList<>();

    public DetailsFragment() {
    }

    public static DetailsFragment getInstance() {
        return new DetailsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SchoolViewModel viewModel = ViewModelProviders.of(getActivity()).get(SchoolViewModel.class);

        //TODO: There should be a better way to get the scores
        viewModel.getScores().observe(getViewLifecycleOwner(), new Observer<List<Score>>() {
            @Override
            public void onChanged(@Nullable List<Score> scores) {
                Log.d(TAG, "onChanged: " + scores.get(0).getDbn());
                scoreList = scores;
            }
        });

        viewModel.getSchool().observe(getViewLifecycleOwner(), new Observer<School>() {
            @Override
            public void onChanged(@Nullable final School school) {
                binding.setSchool(school);

                for (Score score : scoreList) {
                    if (school.getDbn().equals(score.getDbn())) {
                        Log.d(TAG, "onChanged: score: " + score.getMathScore());
                        binding.detailsMathScoresTv.setText(score.getMathScore());
                        binding.detailsReadingScoresTv.setText(score.getReadingScore());
                        binding.detailsWritingScoresTv.setText(score.getWritingScore());
                    }
                }

            }
        });


    }
}
