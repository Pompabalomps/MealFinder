package com.example.mealfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealfinder.R;

public class ForYou extends AppCompatActivity implements View.OnClickListener {
    private ImageButton fyBackBtn;
    private Button fyDetailBtn;
    private Button fyAddToListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_you);
        fyBackBtn = findViewById(R.id.fyBackBtn);
        fyDetailBtn = findViewById(R.id.fyDetailBtn);
        fyAddToListBtn = findViewById(R.id.fyAddToListBtn);
        fyBackBtn.setOnClickListener(this);
        fyDetailBtn.setOnClickListener(this);
        fyAddToListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fyBackBtn) {
            finish();
        }

        if (v.getId() == R.id.fyDetailBtn) {
            Intent i = new Intent(this, RecipeDetails.class);
            startActivity(i);
        }

        if (v.getId() == R.id.fyAddToListBtn) {
            Intent i = new Intent(this, EditRecipe.class);
            startActivity(i);
        }
    }
}