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
import android.widget.Toast;

import com.example.mealfinder.R;
import com.example.mealfinder.adapters.*;
import com.example.mealfinder.objects.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Main Activity";
    private ImageButton ibMainSearch;
    private ImageButton ibMainProfile;
    private FirebaseAuth mAuth;
    private Button mainFyBtn;
    private RecyclerView rvMain;
    private FirebaseDatabase db;
    private Button moreMainBtn;
    private ArrayList<Recipe> recipes;
    private View.OnClickListener onItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ibMainSearch = findViewById(R.id.ibMainSearch);
        ibMainProfile = findViewById(R.id.ibMainProfile);
        mainFyBtn = findViewById(R.id.mainFyBtn);
        moreMainBtn = findViewById(R.id.moreMainBtn);
        ibMainSearch.setOnClickListener(this);
        ibMainProfile.setOnClickListener(this);
        mainFyBtn.setOnClickListener(this);
        moreMainBtn.setOnClickListener(this);

//        recipes = new ArrayList<>();
//        recipes.add(new Recipe("Hamburger", "Alon", "1234", "Meat patty between two breads", "cook the meat patty then add between breads", null, null, null, Arrays.asList(new String[]{"burger", "meat", "tasty"})));
//        recipes.add(new Recipe("Pizza", "Tal", "5678", "flatbread with sauce and cheese", "make the flatbread, spread sauce and cheese then put in the oven", null, null, null, Arrays.asList(new String[]{"Pizza", "cheese", "sharing"})));

        Intent i = new Intent(this, EditRecipe.class);
        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                Recipe recipe = recipes.get(position);
                i.putExtra("rec", recipe);
                startActivity(i);
            }
        };

        rvMain = findViewById(R.id.rvMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMain.setLayoutManager(layoutManager);



        db.getReference().child("recipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipes = new ArrayList<>();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    recipes.add(recipeSnapshot.getValue(Recipe.class));
                }
                RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
                rvMain.setAdapter(recipeAdapter);
                recipeAdapter.setmOnItemClickListener(onItemClickListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d(TAG, "finished loading recycler view");
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
        return;
    }
}