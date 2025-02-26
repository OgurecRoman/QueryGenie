package com.example.querygenie.domain.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.querygenie.R;
import com.example.querygenie.data.DBPattern;
import com.example.querygenie.data.model.PatternModel;

import java.util.ArrayList;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final DBPattern dbPattern;
    private ArrayList<PatternModel> items;

    public PatternAdapter(Context context) {
        this.dbPattern = new DBPattern(context);
        this.inflater = LayoutInflater.from(context);
        items = dbPattern.selectAll();
    }

    @Override
    public PatternAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if (isliked) {
            holder.favourite_but.setImageResource(R.drawable.heartactive);
        }

        holder.favourite_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patternModel.setIsliked(!patternModel.getIsliked());
                dbPattern.update(patternModel);
                if (patternModel.getIsliked())
                    holder.favourite_but.setImageResource(R.drawable.heartactive);
                else holder.favourite_but.setImageResource(R.drawable.heart);
            }
        });

        holder.delete_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbPattern.delete(items.get(position).getId());
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });

        holder.use_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("PatternID", items.get(position).getId());
                bundle.putBoolean("IsEdit", false);

                Navigation.findNavController(view)
                        .navigate(R.id.action_patternsFragment_to_homepageFragment, bundle);
            }
        });

        holder.edit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("PatternID", items.get(position).getId());
                bundle.putBoolean("IsEdit", true);

                Navigation.findNavController(view)
                        .navigate(R.id.action_patternsFragment_to_homepageFragment, bundle);
            }
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