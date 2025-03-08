package com.example.querygenie.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.querygenie.R;
import com.example.querygenie.domain.query.ManageQuery;

public class ItemQueryFragment extends Fragment {
    ManageQuery query;

    private static final String ARG_QUERY = "QueryID";

    private int mQueryID;

    public ItemQueryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQueryID = getArguments().getInt(ARG_QUERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_query, container, false);

        TextView roleText = view.findViewById(R.id.role);
        TextView goalText = view.findViewById(R.id.goal);
        TextView environmentText = view.findViewById(R.id.environment);
        TextView queryText = view.findViewById(R.id.query);
        TextView answerText = view.findViewById(R.id.answer);

        LinearLayout backLayout = (LinearLayout) view.findViewById(R.id.back);

        query = new ManageQuery(getActivity());
        query.selectQuery(mQueryID);

        roleText.setText(query.getRole());
        goalText.setText(query.getGoal());
        environmentText.setText(query.getEnvironment());
        queryText.setText(query.getTextQuery());
        answerText.setText(query.getTextAnswer());

        backLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_itemQueryFragment_to_historyFragment);
            }
        });

        return view;
    }
}