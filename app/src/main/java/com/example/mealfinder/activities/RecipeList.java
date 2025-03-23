package com.example.mealfinder.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.mealfinder.R;
import com.example.mealfinder.adapters.RecipeAdapter;
import com.example.mealfinder.objects.Recipe;
import com.example.mealfinder.workers.MyWorker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RecipeList extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private String TAG = "Recipe List Activity";
    private TextView tvListTitle;
    private ImageButton listBackBtn;
    private FirebaseDatabase db;
    private RecyclerView rvRecipeList;
    private View.OnClickListener onItemClickListener;
    private ArrayList<Recipe> recipes;
    private ArrayList<String> savedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        tvListTitle = findViewById(R.id.tvListTitle);
        listBackBtn = findViewById(R.id.listBackBtn);
        listBackBtn.setOnClickListener(this);

        Intent i = new Intent(this, RecipeDetails.class);
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

        rvRecipeList = findViewById(R.id.rvRecipeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvRecipeList.setLayoutManager(layoutManager);



        db.getReference()
                .child("users")
                .child(mAuth.getCurrentUser().getUid())
                .child("savedRecipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                savedRecipes = new ArrayList<>();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    String savedRec = recipeSnapshot.getValue(String.class);
                    savedRecipes.add(savedRec);
                }
                db.getReference().child("recipes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recipes = new ArrayList<>();
                        for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                            Recipe rec = recipeSnapshot.getValue(Recipe.class);
                            if (savedRecipes.contains(rec.getId())) {
                                recipes.add(rec);
                            }
                        }
                        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
                        rvRecipeList.setAdapter(recipeAdapter);
                        recipeAdapter.setmOnItemClickListener(onItemClickListener);
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
        Log.d(TAG, "finished loading recycler view");
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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String[] username = new String[1];
        db.getReference()
                .child("users")
                .child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    username[0] = ((Map<String, Object>)task.getResult().getValue()).get("username").toString();
                    Log.d(TAG, "username = " + username[0]);
                    tvListTitle.setText(username[0] + "'s list");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.listBackBtn) {
            Intent i = new Intent(this, Main.class);
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