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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recipe_item, parent, false);
        return new RecipeViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvRecipeName.setText(recipe.getName());
        holder.tvRecipeCreator.setText(recipe.getCreatorName());
        holder.tvRecipeLikes.setText(String.valueOf(recipe.getRating()));
//        holder.ivRecipe.setImageResource(holder.tvRecipeName.getResources().getIdentifier(recipe.getImg1(), "drawable", holder.tvRecipeName.getContext().getOpPackageName()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
//        public ImageView ivRecipe;
        public TextView tvRecipeName;
        public TextView tvRecipeCreator;
        public TextView tvRecipeLikes;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
//            ivRecipe = itemView.findViewById(R.id.ivRecipe);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvRecipeCreator = itemView.findViewById(R.id.tvRecipeCreator);
            tvRecipeLikes = itemView.findViewById(R.id.tvRecipeLikes);
        }
    }
}
