package com.example.querygenie.presentation.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.querygenie.R;
import com.example.querygenie.domain.query.QueryService;

public class homepageFragment extends Fragment {
    TextView answerView;
    ProgressBar progressBar;

    public homepageFragment() {
    }

    public static homepageFragment newInstance() {
        homepageFragment fragment = new homepageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void sendQuery(String text) {
        Intent intent = new Intent(getActivity(), QueryService.class);
        intent.putExtra("query", text);
        requireActivity().startService(intent);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("RESPONSE".equals(intent.getAction())) {
                progressBar.setVisibility(View.GONE);
                String response = intent.getStringExtra("response");
                answerView.setText(response);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("RESPONSE");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        EditText editText = view.findViewById(R.id.query);
        Button sendBut = view.findViewById(R.id.send);
        progressBar = view.findViewById(R.id.loading);
        answerView = view.findViewById(R.id.ans);

        progressBar.setVisibility(View.GONE);

        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                sendQuery(text);
                answerView.setText("");
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}