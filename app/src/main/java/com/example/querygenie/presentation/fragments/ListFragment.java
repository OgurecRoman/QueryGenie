package com.example.querygenie.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.querygenie.R;
import com.example.querygenie.data.model.SharedViewModel;
import com.example.querygenie.domain.adapter.PatternAdapter;

public class ListFragment extends Fragment {
    private boolean isFav;
    private PatternAdapter adapter;
    private SharedViewModel sharedViewModel;

    public ListFragment() {
    }

    public static ListFragment newInstance(int position) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFav", position == 1);
        fragment.setArguments(args);
        return fragment;
    }


    public void refreshAdapter() {
        if (adapter != null) {
            Log.d("AAA", "RELOAD AAAAAAAA " + isFav);
            adapter.reloadData(isFav, "");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFav = getArguments().getBoolean("isFav");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        TextView messageText = view.findViewById(R.id.message);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new PatternAdapter(requireContext(), isFav, "");
        recyclerView.setAdapter(adapter);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String searchQuery) {
                if (adapter != null) {
                    adapter.reloadData(isFav, searchQuery);
                }
            }
        });

        return view;
    }
}
