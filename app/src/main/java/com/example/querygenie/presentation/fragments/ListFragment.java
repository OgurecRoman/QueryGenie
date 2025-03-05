package com.example.querygenie.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.querygenie.R;
import com.example.querygenie.domain.adapter.PatternAdapter;

public class ListFragment extends Fragment {
    private boolean isFav;
    private PatternAdapter adapter;

    public ListFragment(boolean isFavorite) {
        this.isFav = isFavorite;
    }

    public ListFragment() {}

    public void refreshAdapter(boolean isFav) {
        if (adapter != null) {
            Log.d("AAA", "RELOAD AAAAAAAA " + isFav);
            adapter.reloadData(isFav, "");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), adapter::filter);

        return view;
    }
}
