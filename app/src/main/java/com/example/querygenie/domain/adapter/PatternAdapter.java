package com.example.querygenie.domain.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.example.querygenie.data.model.PatternModel;
import com.example.querygenie.presentation.viewmodel.SharedViewModel;

import java.util.ArrayList;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.ViewHolder> implements BaseAdapterInterface {
    private final LayoutInflater inflater;
    private final DBHelper dbPattern;
    private boolean isFav;
    private ArrayList<PatternModel> items;

    public PatternAdapter(Context context, boolean isFav, SharedViewModel sharedViewModel) {
        this.dbPattern = new DBHelper(context);
        this.inflater = LayoutInflater.from(context);
        reloadData(isFav, sharedViewModel);
    }

    public void reloadData(boolean isFav, SharedViewModel sharedViewModel) {
        this.isFav = isFav;
        items = dbPattern.filterLikePatterns(isFav, sharedViewModel.getSearchText());
        notifyDataSetChanged();
    }

    @Override
    public PatternAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pattern_item, parent, false);
        return new PatternAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatternAdapter.ViewHolder holder, int position) {
        PatternModel patternModel = items.get(position);
        boolean isliked = patternModel.getIsliked();
        holder.name_text.setText(patternModel.getName());
        holder.role_text.setText(patternModel.getRole());
        holder.goal_text.setText(patternModel.getGoal());
        holder.environment_text.setText(patternModel.getEnvironment());
        if (isliked)
            holder.favourite_but.setImageResource(R.drawable.heartactive);
        else
            holder.favourite_but.setImageResource(R.drawable.heart);

        holder.favourite_but.setOnClickListener(view -> {
            patternModel.setIsliked(!patternModel.getIsliked());
            dbPattern.updatePattern(patternModel);

            if (isFav) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            } else {
                if (patternModel.getIsliked())
                    holder.favourite_but.setImageResource(R.drawable.heartactive);
                else holder.favourite_but.setImageResource(R.drawable.heart);
            }
        });

        holder.delete_but.setOnClickListener(view -> {
            dbPattern.deletePattern(items.get(position).getId());
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        });

        holder.use_but.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PatternID", items.get(position).getId());
            bundle.putBoolean("IsEdit", false);

            Navigation.findNavController(view)
                    .navigate(R.id.action_patternsFragment_to_homepageFragment, bundle);
        });

        holder.edit_but.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("PatternID", items.get(position).getId());
            bundle.putBoolean("IsEdit", true);

            Navigation.findNavController(view)
                    .navigate(R.id.action_patternsFragment_to_homepageFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name_text;
        final TextView role_text;
        final TextView goal_text;
        final TextView environment_text;
        final ImageButton favourite_but;
        final Button use_but;
        final ImageButton edit_but;
        final ImageButton delete_but;

        ViewHolder(View view) {
            super(view);
            name_text = view.findViewById(R.id.name);
            role_text = view.findViewById(R.id.role);
            goal_text = view.findViewById(R.id.goal);
            environment_text = view.findViewById(R.id.environment);
            favourite_but = view.findViewById(R.id.favouriteBut);
            use_but = view.findViewById(R.id.useBut);
            edit_but = view.findViewById(R.id.editBut);
            delete_but = view.findViewById(R.id.deleteBut);
        }
    }
}