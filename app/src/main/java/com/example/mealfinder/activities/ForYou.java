package com.example.mealfinder.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ForYou extends AppCompatActivity implements View.OnClickListener {
    private ImageButton fyBackBtn;
    private Button fyDetailBtn;
    private Button fyAddToListBtn;
    private FirebaseAuth mAuth;
    private Recipe recipe;
    private TextView fyItemTitle;
    private TextView fyRecLikes;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_you);
        db = FirebaseDatabase.getInstance();
        Intent i = getIntent();
        recipe = (Recipe) i.getSerializableExtra("rec");
        mAuth = FirebaseAuth.getInstance();
        fyBackBtn = findViewById(R.id.fyBackBtn);
        fyDetailBtn = findViewById(R.id.fyDetailBtn);
        fyAddToListBtn = findViewById(R.id.fyAddToListBtn);
        fyItemTitle = findViewById(R.id.fyItemTitle);
        fyRecLikes = findViewById(R.id.fyRecLikes);
        fyBackBtn.setOnClickListener(this);
        fyDetailBtn.setOnClickListener(this);
        fyAddToListBtn.setOnClickListener(this);

        fyItemTitle.setText(recipe.getName());
        fyRecLikes.setText(recipe.getLikes() + "");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fyBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }

        if (v.getId() == R.id.fyDetailBtn) {
            Intent i = new Intent(this, RecipeDetails.class);
            i.putExtra("rec", recipe);
            startActivity(i);
        }

        if (v.getId() == R.id.fyAddToListBtn) {
            Intent i = new Intent(this, EditRecipe.class);
            db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("savedRecipes").child(recipe.getId()).setValue(recipe.getId());
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Storing data in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("lastLogout", System.currentTimeMillis());
        editor.putBoolean("isAppOn", false);

        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isAppOn", true);

        editor.apply();
    }
}