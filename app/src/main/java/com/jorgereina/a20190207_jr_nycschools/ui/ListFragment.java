package com.jorgereina.a20190207_jr_nycschools.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgereina.a20190207_jr_nycschools.R;
import com.jorgereina.a20190207_jr_nycschools.SchoolViewModel;
import com.jorgereina.a20190207_jr_nycschools.data.School;
import com.jorgereina.a20190207_jr_nycschools.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private SchoolAdapter adapter;
    private List<School> schools = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private SchoolViewModel viewModel;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ItemClickListener) {
            itemClickListener = (ItemClickListener) context;
        } else {
            throw new ClassCastException("Class cast not valid");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SchoolViewModel.class);
        if (schools != null || schools.isEmpty()) {
            viewModel.getSchools().observe(getActivity(), new Observer<List<School>>() {
                @Override
                public void onChanged(@Nullable List<School> schools) {
                    adapter.setSchools(schools);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.schoolsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SchoolAdapter(itemClickListener);
        binding.schoolsRv.setAdapter(adapter);
        return binding.getRoot();
    }


    interface ItemClickListener {
        void onItemClick(int position);
    }
}