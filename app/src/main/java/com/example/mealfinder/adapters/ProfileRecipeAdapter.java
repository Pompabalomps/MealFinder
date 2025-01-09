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

public class ProfileRecipeAdapter extends RecyclerView.Adapter<ProfileRecipeAdapter.ProfileRecipeViewHolder> {
    private ArrayList<Recipe> profileRecipes;

    public ProfileRecipeAdapter(ArrayList<Recipe> profileRecipes) {
        this.profileRecipes = profileRecipes;
    }

    @NonNull
    @Override
    public ProfileRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View profileRecipeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recipe_profile_item, parent, false);
        return new ProfileRecipeViewHolder(profileRecipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecipeViewHolder holder, int position) {
        Recipe profileRecipe = profileRecipes.get(position);
        holder.tvProfileRecipeName.setText(profileRecipe.getName());
        holder.tvProfileRecipeLikes.setText(String.valueOf(profileRecipe.getRating()));
//        holder.ivProfileRecipe.setImageResource(holder.tvProfileRecipeName.getResources().getIdentifier(profileRecipe.getImg1(), "drawable", holder.tvProfileRecipeName.getContext().getOpPackageName()));
    }

    @Override
    public int getItemCount() {
        return profileRecipes.size();
    }

    public static class ProfileRecipeViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView ivProfileRecipe;
        public TextView tvProfileRecipeName;
        public TextView tvProfileRecipeLikes;

        public ProfileRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
//            ivProfileRecipe = itemView.findViewById(R.id.ivProfileRecipe);
            tvProfileRecipeName = itemView.findViewById(R.id.tvProfileRecipeName);
            tvProfileRecipeLikes = itemView.findViewById(R.id.tvProfileRecipeLikes);
        }
    }
}
