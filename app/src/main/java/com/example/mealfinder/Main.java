package com.example.mealfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Main Activity";
    private ImageButton ibMainSearch;
    private ImageButton ibMainProfile;
    private ImageButton ibMainImage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAuth = FirebaseAuth.getInstance();
        ibMainSearch = findViewById(R.id.ibMainSearch);
        ibMainProfile = findViewById(R.id.ibMainProfile);
        ibMainImage = findViewById(R.id.ibMainImage);
        ibMainSearch.setOnClickListener(this);
        ibMainProfile.setOnClickListener(this);
        ibMainImage.setOnClickListener(this);
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
    }
}