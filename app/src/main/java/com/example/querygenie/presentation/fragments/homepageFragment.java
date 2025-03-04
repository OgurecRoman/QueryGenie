package com.example.querygenie.presentation.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.querygenie.R;
import com.example.querygenie.domain.query.ManageQuery;


public class homepageFragment extends Fragment {
    private ManageQuery query;
    private TextView answerView;
    private ProgressBar progressBar;

    private static final String ARG_PATTERN = "PatternID";
    private static final String ARG_EDIT = "IsEdit";

    private int mPatternId;
    private boolean mIsEdit;

    public homepageFragment() {
    }

    public static historyFragment newInstance(String param1, String param2) {
        historyFragment fragment = new historyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATTERN, param1);
        args.putString(ARG_EDIT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPatternId = getArguments().getInt(ARG_PATTERN);
            mIsEdit = getArguments().getBoolean(ARG_EDIT);
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("RESPONSE".equals(intent.getAction())) {
                progressBar.setVisibility(View.GONE);
                String response = intent.getStringExtra("response");
                query.setTextAnswer(response);
                query.addQuery();
                answerView.setText(response);
            }
        }
    };

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.namePattern);

        final EditText nameText = new EditText(getActivity());

        FrameLayout container = new FrameLayout(getActivity());
        container.setPadding(50, 20, 50, 20);
        container.addView(nameText);

        builder.setView(container);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                query.setNamePattern(nameText.getText().toString());
                query.addPattern();
                Toast.makeText(getActivity(), "Шаблон сохранен", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

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

        EditText editQuery = view.findViewById(R.id.query);
        EditText editRole = view.findViewById(R.id.role);
        EditText editGoal = view.findViewById(R.id.goal);
        EditText editEnvironment = view.findViewById(R.id.environment);

        Button sendBut = view.findViewById(R.id.send);
        Button saveBut = view.findViewById(R.id.saveBut);
        ImageButton deleteQueryBut = view.findViewById(R.id.butDeleteQuery);
        ImageButton deletePatternBut = view.findViewById(R.id.butDeletePattern);

        progressBar = view.findViewById(R.id.loading);
        answerView = view.findViewById(R.id.ans);

        progressBar.setVisibility(View.GONE);

        query = new ManageQuery(getActivity());

        if (mPatternId > 0){
            query.installPattern(mPatternId);
            editRole.setText(query.getRole());
            editGoal.setText(query.getGoal());
            editEnvironment.setText(query.getEnvironment());

            if (mIsEdit){
                saveBut.setText(R.string.save_changes);
            }
        }

        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.setTextQuery(editQuery.getText().toString());
                query.setRole(editRole.getText().toString());
                query.setGoal(editGoal.getText().toString());
                query.setEnvironment(editEnvironment.getText().toString());
                query.sendQuery(getActivity());
                answerView.setText("");
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.setRole(editRole.getText().toString());
                query.setGoal(editGoal.getText().toString());
                query.setEnvironment(editEnvironment.getText().toString());
                if (mIsEdit){
                    query.updatePattern(mPatternId);
                    saveBut.setText(R.string.save);
                    Toast.makeText(getActivity(), "Шаблон обновлен", Toast.LENGTH_SHORT).show();
                }
                else
                    showInputDialog();
            }
        });

        deleteQueryBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editQuery.setText("");
            }
        });

        deletePatternBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRole.setText("");
                editEnvironment.setText("");
                editGoal.setText("");
            }
        });

        return view;
    }
}