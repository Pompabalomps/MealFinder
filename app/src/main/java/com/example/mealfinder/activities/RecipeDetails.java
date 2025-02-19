package com.example.mealfinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;

public class RecipeDetails extends AppCompatActivity implements View.OnClickListener {
    private Button follow;
    private Button addToList;
    private ImageButton recipeDetailsBackBtn;
    private TextView tvRecipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        Intent i = getIntent();
        Recipe rec = (Recipe) i.getSerializableExtra("rec");
        follow = findViewById(R.id.follow);
        addToList = findViewById(R.id.addToList);
        recipeDetailsBackBtn = findViewById(R.id.recipeDetailsBackBtn);
        tvRecipeName = findViewById(R.id.tvRecipeDetailsName);
        follow.setOnClickListener(this);
        addToList.setOnClickListener(this);
        recipeDetailsBackBtn.setOnClickListener(this);

        tvRecipeName.setText(rec.getName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.follow) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
        }
        if (v.getId() == R.id.addToList) {
            Intent i = new Intent(this, EditRecipe.class);
            startActivity(i);
        }
        if (v.getId() == R.id.recipeDetailsBackBtn) {
            finish();
        }
    }
}