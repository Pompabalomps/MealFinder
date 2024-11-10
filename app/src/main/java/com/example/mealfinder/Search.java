package com.example.mealfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Search extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private Button searchRecentButton1;
    private ImageButton searchBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mAuth = FirebaseAuth.getInstance();
        searchRecentButton1 = findViewById(R.id.searchRecentBtn1);
        searchBackBtn = findViewById(R.id.searchBackBtn);
        searchRecentButton1.setOnClickListener(this);
        searchBackBtn.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() { }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchRecentBtn1) {
            Intent i = new Intent(this, RecipeDetails.class);
            startActivity(i);
        }

        if (v.getId() == R.id.searchBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }
}