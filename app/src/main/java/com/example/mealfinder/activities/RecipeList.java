package com.example.mealfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecipeList extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView tvListTitle;
    private Button recipe_item1Btn;
    private ImageButton listBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        mAuth = FirebaseAuth.getInstance();
        tvListTitle = findViewById(R.id.tvListTitle);
        recipe_item1Btn = findViewById(R.id.recipe_item1Btn);
        listBackBtn = findViewById(R.id.listBackBtn);
        recipe_item1Btn.setOnClickListener(this);
        listBackBtn.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload(currentUser);
        } else {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    private void reload(FirebaseUser user) {
        tvListTitle.setText(user.getEmail().toString() + "'s list");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recipe_item1Btn) {
            Intent i = new Intent(this, RecipeDetails.class);
            startActivity(i);
        }

        if (v.getId() == R.id.listBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }
}