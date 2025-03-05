package com.example.querygenie.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.querygenie.R;
import com.example.querygenie.data.DBHelper;
import com.example.querygenie.data.model.QueryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements BaseAdapterInterface {
    private final LayoutInflater inflater;
    private final DBHelper dbPattern;
    private boolean isFav;
    private ArrayList<QueryModel> items;

    public HistoryAdapter(Context context, boolean isFav, String searchQuery) {
        this.dbPattern = new DBHelper(context);
        this.inflater = LayoutInflater.from(context);
        reloadData(isFav, searchQuery);
    }

    public void reloadData(boolean isFav, String searchQuery) {
        this.isFav = isFav;
        items = dbPattern.filterLikeHistory(isFav, searchQuery);
        notifyDataSetChanged();
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
        holder.count_text.setText(String.valueOf(queryModel.getCount()));
        if (isliked)
            holder.favourite_but.setImageResource(R.drawable.heartactive);
        else
            holder.favourite_but.setImageResource(R.drawable.heart);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("QueryID", items.get(position).getId());

            Navigation.findNavController(view)
                    .navigate(R.id.action_historyFragment_to_itemQueryFragment, bundle);
        });

        holder.favourite_but.setOnClickListener(view -> {
            queryModel.setIsliked(!queryModel.getIsliked());
            dbPattern.updateQuery(queryModel);
            if (isFav) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            } else {
                if (queryModel.getIsliked())
                    holder.favourite_but.setImageResource(R.drawable.heartactive);
                else holder.favourite_but.setImageResource(R.drawable.heart);
            }
        });

        holder.delete_but.setOnClickListener(view -> {
            dbPattern.deleteQuery(items.get(position).getId());
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView query_text;
        final TextView count_text;
        final TextView date_text;
        final ImageButton favourite_but;
        final Button use_but;
        final ImageButton delete_but;

        ViewHolder(View view) {
            super(view);
            query_text = view.findViewById(R.id.textQuery);
            count_text = view.findViewById(R.id.count);
            date_text = view.findViewById(R.id.date);
            favourite_but = view.findViewById(R.id.favouriteBut);
            use_but = view.findViewById(R.id.useBut);
            delete_but = view.findViewById(R.id.deleteBut);
        }
    }
}