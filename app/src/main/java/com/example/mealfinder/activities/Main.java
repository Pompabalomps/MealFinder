package com.example.mealfinder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mealfinder.R;
import com.example.mealfinder.adapters.*;
import com.example.mealfinder.objects.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Main Activity";
    private ImageButton ibMainSearch;
    private ImageButton ibMainProfile;
    private ImageButton ibMainImage;
    private FirebaseAuth mAuth;
    private Button mainFyBtn;
    private RecyclerView rvMain;

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

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Hamburger", "Alon", "1234", "Meat patty between two breads", "cook the meat patty then add between breads", null, null, null, new String[]{"burger", "meat", "tasty"}));
        recipes.add(new Recipe("Pizza", "Tal", "5678", "flatbread with sauce and cheese", "make the flatbread, spread sauce and cheese then put in the oven", null, null, null, new String[]{"Pizza", "cheese", "sharing"}));

        rvMain = findViewById(R.id.rvMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMain.setLayoutManager(layoutManager);

        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
        rvMain.setAdapter(recipeAdapter);
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