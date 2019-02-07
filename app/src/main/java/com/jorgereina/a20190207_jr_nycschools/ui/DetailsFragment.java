package com.jorgereina.a20190207_jr_nycschools.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgereina.a20190207_jr_nycschools.R;
import com.jorgereina.a20190207_jr_nycschools.SchoolViewModel;
import com.jorgereina.a20190207_jr_nycschools.data.School;
import com.jorgereina.a20190207_jr_nycschools.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

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

        SchoolViewModel viewModel = ViewModelProviders.of(getActivity()).get(SchoolViewModel.class);
        viewModel.getSchool().observe(getViewLifecycleOwner(), new Observer<School>() {
            @Override
            public void onChanged(@Nullable School school) {
                binding.setSchool(school);
            }
        });
    }
}
