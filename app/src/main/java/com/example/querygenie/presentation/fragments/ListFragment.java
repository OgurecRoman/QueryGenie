package com.example.querygenie.presentation.fragments;

import android.os.Bundle;
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
import com.example.querygenie.presentation.viewmodel.SharedViewModel;
import com.example.querygenie.domain.adapter.BaseAdapterInterface;
import com.example.querygenie.domain.adapter.HistoryAdapter;
import com.example.querygenie.domain.adapter.PatternAdapter;

public class ListFragment extends Fragment {
    private BaseAdapterInterface adapter;
    private SharedViewModel sharedViewModel;
    private boolean isFav;

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

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        if (sharedViewModel.isHistory()) {
            adapter = new HistoryAdapter(requireContext(), isFav, "");
        } else {
            adapter = new PatternAdapter(requireContext(), isFav, "");
        }

        recyclerView.setAdapter((RecyclerView.Adapter<?>) adapter);

        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String searchQuery) {
                if (adapter != null) {
                    adapter.reloadData(isFav, searchQuery);
                    if (((RecyclerView.Adapter<?>) adapter).getItemCount() == 0)
                        messageText.setVisibility(View.VISIBLE);
                    else
                        messageText.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }
}
