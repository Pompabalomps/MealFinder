package com.example.mealfinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mealfinder.R;
import com.example.mealfinder.adapters.*;
import com.example.mealfinder.objects.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Main Activity";
    private ImageButton ibMainSearch;
    private ImageButton ibMainProfile;
    private FirebaseAuth mAuth;
    private Button mainFyBtn;
    private RecyclerView rvMain;
    private TextView tvMainHello;
    private FirebaseDatabase db;
    private Button moreMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ibMainSearch = findViewById(R.id.ibMainSearch);
        ibMainProfile = findViewById(R.id.ibMainProfile);
        mainFyBtn = findViewById(R.id.mainFyBtn);
        tvMainHello = findViewById(R.id.tvMainHello);
        moreMainBtn = findViewById(R.id.moreMainBtn);
        ibMainSearch.setOnClickListener(this);
        ibMainProfile.setOnClickListener(this);
        mainFyBtn.setOnClickListener(this);
        moreMainBtn.setOnClickListener(this);

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
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload(currentUser);
        } else {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
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

        if (v.getId() == R.id.mainFyBtn) {
            Intent i = new Intent(this, ForYou.class);
            startActivity(i);
        }

        if (v.getId() == R.id.moreMainBtn) {
            Intent i = new Intent(this, RecipeList.class);
            startActivity(i);
        }
    }

    private void reload(FirebaseUser user) {
        final String[] username = new String[1];
        db.getReference().child("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    username[0] = ((Map<String, Object>)task.getResult().getValue()).get("username").toString();
                    Log.d(TAG, "username = " + username[0]);
                    tvMainHello.setText("Hello " + username[0] + "!");
                }
            }
        });
        db.getReference().child("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Object[] image = new File[1];
                    image[0] = ((Map<String, Object>)task.getResult().getValue()).get("image");
                    if (image == null) {

                    }
                }
            }
        });
    }
}