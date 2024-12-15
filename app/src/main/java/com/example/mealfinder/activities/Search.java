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
import com.example.mealfinder.adapters.RecentsAdapter;
import com.example.mealfinder.adapters.RecipeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private ImageButton searchBackBtn;
    private RecyclerView rvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mAuth = FirebaseAuth.getInstance();
        searchBackBtn = findViewById(R.id.searchBackBtn);
        rvSearch = findViewById(R.id.rvSearch);
        searchBackBtn.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);

        ArrayList<String> recents = new ArrayList<>();
        recents.add("Smoked brisket");
        recents.add("neapolitan pizza");
        recents.add("Honey lime chicken wings");

        RecentsAdapter recentsAdapter = new RecentsAdapter(recents);
        rvSearch.setAdapter(recentsAdapter);
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
        if (v.getId() == R.id.searchBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }
}