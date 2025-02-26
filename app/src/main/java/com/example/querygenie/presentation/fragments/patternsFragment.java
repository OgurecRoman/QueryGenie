package com.example.querygenie.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.querygenie.R;
import com.example.querygenie.domain.adapter.PatternAdapter;

public class patternsFragment extends Fragment {

    public patternsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patterns, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        PatternAdapter adapter = new PatternAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}