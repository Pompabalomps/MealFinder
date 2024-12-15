package com.example.mealfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealfinder.R;
import com.example.mealfinder.objects.*;

import java.util.ArrayList;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentViewHolder> {
    private ArrayList<String> recents;

    public RecentsAdapter(ArrayList<String> recents) {
        this.recents = recents;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recent_search_item, parent, false);
        return new RecentViewHolder(recentView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        String recent = recents.get(position);
        holder.tvRecentName.setText(recent);
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }

    public static class RecentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRecentName;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecentName = itemView.findViewById(R.id.tvRecentName);
        }
    }
}
