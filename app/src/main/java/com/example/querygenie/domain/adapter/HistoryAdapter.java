package com.example.querygenie.domain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.querygenie.R;
import com.example.querygenie.data.DBHelper;
import com.example.querygenie.data.model.QueryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final DBHelper dbPattern;
    private final ArrayList<QueryModel> items;

    public HistoryAdapter(Context context, String line) {
        this.dbPattern = new DBHelper(context);
        this.inflater = LayoutInflater.from(context);
        items = dbPattern.selectAllHistory();
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        QueryModel queryModel = items.get(position);
        boolean isliked = queryModel.getIsliked();
        holder.query_text.setText(queryModel.getQuery());
        holder.date_text.setText(queryModel.getDate());
        if (isliked) {
            holder.favourite_but.setImageResource(R.drawable.heartactive);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView query_text;
        final TextView date_text;
        final ImageButton favourite_but;
        final Button use_but;
        final ImageButton delete_but;

        ViewHolder(View view) {
            super(view);
            query_text = view.findViewById(R.id.textQuery);
            date_text = view.findViewById(R.id.date);
            favourite_but = view.findViewById(R.id.favouriteBut);
            use_but = view.findViewById(R.id.useBut);
            delete_but = view.findViewById(R.id.deleteBut);
        }
    }
}