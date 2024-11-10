package com.example.mealfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EditRecipe extends AppCompatActivity implements View.OnClickListener {
    private ImageButton editRBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe);
        editRBackBtn = findViewById(R.id.editRBackBtn);
        editRBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editRBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }
}