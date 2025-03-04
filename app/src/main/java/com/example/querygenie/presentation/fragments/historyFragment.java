package com.example.querygenie.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.querygenie.R;
import com.example.querygenie.domain.adapter.HistoryAdapter;

public class historyFragment extends Fragment {

    public historyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        EditText searchText = view.findViewById(R.id.search);
        TextView messageText = view.findViewById(R.id.message);

        ImageButton filterBut = view.findViewById(R.id.filter);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), searchText.getText().toString());
        recyclerView.setAdapter(adapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                HistoryAdapter adapter = new HistoryAdapter(getActivity(), searchText.getText().toString());
                recyclerView.setAdapter(adapter);
                if (adapter.getItemCount() == 0)
                    messageText.setVisibility(View.VISIBLE);
                else
                    messageText.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }
}