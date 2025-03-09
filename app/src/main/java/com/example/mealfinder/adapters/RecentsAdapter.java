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
    private ArrayList<SearchQuery> recents;

    private View.OnClickListener mOnItemClickListener;


    public RecentsAdapter(ArrayList<SearchQuery> recents) {
        this.recents = recents;
    }

    public void setmOnItemClickListener(View.OnClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recent_search_item, parent, false);
        return new RecentViewHolder(recentView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {
        SearchQuery recent = recents.get(position);
        holder.tvRecentName.setText(recent.getQuery());
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRecentName;
        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecentName = itemView.findViewById(R.id.tvRecentName);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
