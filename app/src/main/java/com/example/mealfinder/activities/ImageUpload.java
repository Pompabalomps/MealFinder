package com.example.mealfinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mealfinder.R;

public class ImageUpload extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_upload);
        imageBackBtn = findViewById(R.id.imageBackBtn);
        imageBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }
}