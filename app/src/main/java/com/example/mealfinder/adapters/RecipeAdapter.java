package com.example.mealfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealfinder.R;
import com.example.mealfinder.objects.*;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipes;
    private View.OnClickListener mOnItemClickListener;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setmOnItemClickListener(View.OnClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
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
        holder.tvRecipeLikes.setText("" + recipe.likes());

        if (recipe.getImg1().isEmpty()) {
            if (recipe.getImg2().isEmpty()) {
                Glide.with(holder.ivRecipe.getContext())
                        .load(recipe.getImg3())
                        .into(holder.ivRecipe);
            } else {
                Glide.with(holder.ivRecipe.getContext())
                        .load(recipe.getImg2())
                        .into(holder.ivRecipe);
            }
        } else {
            Glide.with(holder.ivRecipe.getContext())
                    .load(recipe.getImg1())
                    .into(holder.ivRecipe);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRecipeName;
        public TextView tvRecipeCreator;
        public TextView tvRecipeLikes;
        public ImageView ivRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvRecipeCreator = itemView.findViewById(R.id.tvRecipeCreator);
            tvRecipeLikes = itemView.findViewById(R.id.tvRecipeLikes);
            ivRecipe = itemView.findViewById(R.id.ivRecipe);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
