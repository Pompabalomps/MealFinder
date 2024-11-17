package com.example.mealfinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mealfinder.R;
import com.google.firebase.auth.FirebaseAuth;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Main Activity";
    private ImageButton ibMainSearch;
    private ImageButton ibMainProfile;
    private ImageButton ibMainImage;
    private FirebaseAuth mAuth;
    private Button mainFyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAuth = FirebaseAuth.getInstance();
        ibMainSearch = findViewById(R.id.ibMainSearch);
        ibMainProfile = findViewById(R.id.ibMainProfile);
        ibMainImage = findViewById(R.id.ibMainImage);
        mainFyBtn = findViewById(R.id.mainFyBtn);
        ibMainSearch.setOnClickListener(this);
        ibMainProfile.setOnClickListener(this);
        ibMainImage.setOnClickListener(this);
        mainFyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ibMainSearch) {
            Intent i = new Intent(this, Search.class);
            startActivity(i);
        }

        if (v.getId() == R.id.ibMainProfile) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
        }

        if (v.getId() == R.id.ibMainImage) {
            Intent i = new Intent(this, ImageUpload.class);
            startActivity(i);
        }

        if (v.getId() == R.id.mainFyBtn) {
            Intent i = new Intent(this, ForYou.class);
            startActivity(i);
        }
    }
}