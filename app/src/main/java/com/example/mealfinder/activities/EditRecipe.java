package com.example.mealfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealfinder.R;

public class EditRecipe extends AppCompatActivity implements View.OnClickListener {
    private ImageButton editRBackBtn;
    private Button addListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe);
        editRBackBtn = findViewById(R.id.editRBackBtn);
        addListBtn = findViewById(R.id.addListBtn);
        editRBackBtn.setOnClickListener(this);
        addListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editRBackBtn) {
            finish();
        }

        if (v.getId() == R.id.addListBtn) {
            Intent i = new Intent(this, RecipeList.class);
            startActivity(i);
        }
    }
}