package com.example.querygenie.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.querygenie.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

public class TabsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patterns, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        List<String> tabTitles = Arrays.asList("Все", "Избранное");

        return view;
    }
}
