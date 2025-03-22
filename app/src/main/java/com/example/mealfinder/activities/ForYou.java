package com.example.mealfinder.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ForYou extends AppCompatActivity implements View.OnClickListener {
    private ImageButton fyBackBtn;
    private Button fyDetailBtn;
    private Button fyAddToListBtn;
    private FirebaseAuth mAuth;
    private Recipe recipe;
    private TextView fyItemTitle;
    private TextView fyRecLikes;
    private FirebaseDatabase db;
    private ImageView ivFyRecImg1_1;
    private ImageView ivFyRecImg2_1;
    private ImageView ivFyRecImg2_2;
    private ImageView ivFyRecImg3_1;
    private ImageView ivFyRecImg3_2;
    private ImageView ivFyRecImg3_3;
    private ImageButton moreFyBtn;

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
        ivFyRecImg1_1 = findViewById(R.id.ivFyRecImg1_1);
        ivFyRecImg2_1 = findViewById(R.id.ivFyRecImg2_1);
        ivFyRecImg2_2 = findViewById(R.id.ivFyRecImg2_2);
        ivFyRecImg3_1 = findViewById(R.id.ivFyRecImg3_1);
        ivFyRecImg3_2 = findViewById(R.id.ivFyRecImg3_2);
        ivFyRecImg3_3 = findViewById(R.id.ivFyRecImg3_3);
        moreFyBtn = findViewById(R.id.moreFyBtn);
        fyBackBtn.setOnClickListener(this);
        fyDetailBtn.setOnClickListener(this);
        fyAddToListBtn.setOnClickListener(this);
        moreFyBtn.setOnClickListener(this);

        fyItemTitle.setText(recipe.getName());
        fyRecLikes.setText(recipe.likes() + "");

        ArrayList<String> images = new ArrayList<String>();
        if (!recipe.getImg1().isEmpty())
            images.add(recipe.getImg1());
        if (!recipe.getImg2().isEmpty())
            images.add(recipe.getImg2());
        if (!recipe.getImg3().isEmpty())
            images.add(recipe.getImg3());

        switch (images.size()) {
            case 1:
                Glide.with(ivFyRecImg1_1.getContext())
                        .load(images.get(0))
                        .into(ivFyRecImg1_1);
                ivFyRecImg1_1.setVisibility(View.VISIBLE);
                ivFyRecImg2_1.setVisibility(View.GONE);
                ivFyRecImg2_2.setVisibility(View.GONE);
                ivFyRecImg3_1.setVisibility(View.GONE);
                ivFyRecImg3_2.setVisibility(View.GONE);
                ivFyRecImg3_3.setVisibility(View.GONE);
                break;
            case 2:
                Glide.with(ivFyRecImg2_1.getContext())
                        .load(images.get(0))
                        .into(ivFyRecImg2_1);
                Glide.with(ivFyRecImg2_2.getContext())
                        .load(images.get(1))
                        .into(ivFyRecImg2_2);
                ivFyRecImg1_1.setVisibility(View.GONE);
                ivFyRecImg2_1.setVisibility(View.VISIBLE);
                ivFyRecImg2_2.setVisibility(View.VISIBLE);
                ivFyRecImg3_1.setVisibility(View.GONE);
                ivFyRecImg3_2.setVisibility(View.GONE);
                ivFyRecImg3_3.setVisibility(View.GONE);
                break;
            case 3:
                Glide.with(ivFyRecImg3_1.getContext())
                        .load(images.get(0))
                        .into(ivFyRecImg3_1);
                Glide.with(ivFyRecImg3_2.getContext())
                        .load(images.get(1))
                        .into(ivFyRecImg3_2);
                Glide.with(ivFyRecImg3_3.getContext())
                        .load(images.get(2))
                        .into(ivFyRecImg3_3);
                ivFyRecImg1_1.setVisibility(View.GONE);
                ivFyRecImg2_1.setVisibility(View.GONE);
                ivFyRecImg2_2.setVisibility(View.GONE);
                ivFyRecImg3_1.setVisibility(View.VISIBLE);
                ivFyRecImg3_2.setVisibility(View.VISIBLE);
                ivFyRecImg3_3.setVisibility(View.VISIBLE);
                break;
            default:
                ivFyRecImg1_1.setVisibility(View.GONE);
                ivFyRecImg2_1.setVisibility(View.GONE);
                ivFyRecImg2_2.setVisibility(View.GONE);
                ivFyRecImg3_1.setVisibility(View.GONE);
                ivFyRecImg3_2.setVisibility(View.GONE);
                ivFyRecImg3_3.setVisibility(View.GONE);
        }
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
            Intent i = new Intent(this, RecipeList.class);
            db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("savedRecipes").child(recipe.getId()).setValue(recipe.getId());
            startActivity(i);
        }

        if (v.getId() == R.id.moreFyBtn) {
            AtomicBoolean screenOpened = new AtomicBoolean(false); // Track screen opening

            db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("savedRecipes")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> savedRecipes = new ArrayList<>();
                            for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                                savedRecipes.add(recipeSnapshot.getValue(String.class));
                            }

                            db.getReference().child("recipes").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (screenOpened.get()) return; // Prevent multiple openings
                                    ArrayList<Recipe> recipes;
                                    recipes = new ArrayList<>();
                                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                                        Recipe rec = recipeSnapshot.getValue(Recipe.class);
                                        if (!rec.getUserId().equals(mAuth.getCurrentUser().getUid()) &&
                                                !savedRecipes.contains(rec.getId())) {
                                            recipes.add(rec);
                                        }
                                    }

                                    if (recipes.isEmpty()) {
                                        // Show Toast only if screen has NOT been opened before
                                        if (!screenOpened.get()) {
                                            Toast.makeText(ForYou.this, "No new recipes to show!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (screenOpened.compareAndSet(false, true)) {
                                        // Open ForYou activity only once
                                        Intent i = new Intent(ForYou.this, ForYou.class);
                                        i.putExtra("rec", getRandom(recipes));
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


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

    public static Recipe getRandom(ArrayList<Recipe> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }
}