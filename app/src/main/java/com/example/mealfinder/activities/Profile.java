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

import com.example.mealfinder.R;
import com.example.mealfinder.adapters.ProfileRecipeAdapter;
import com.example.mealfinder.adapters.RecipeAdapter;
import com.example.mealfinder.objects.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.File;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private String TAG = "Profile Activity";
    private TextView tvProfileHello;
    private ImageButton profileBackBtn;
    private Button logoutProfileBtn;
    private FirebaseDatabase db;
    private FirebaseStorage stor;
    private RecyclerView rvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        stor = FirebaseStorage.getInstance();
        tvProfileHello = findViewById(R.id.tvProfileHello);
        profileBackBtn = findViewById(R.id.profileBackBtn);
        logoutProfileBtn = findViewById(R.id.logoutProfileBtn);
        rvProfile = findViewById(R.id.rvProfile);
        profileBackBtn.setOnClickListener(this);
        logoutProfileBtn.setOnClickListener(this);

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Hamburger", "Alon", "1234", "Meat patty between two breads", "cook the meat patty then add between breads", null, null, null, Arrays.asList(new String[]{"burger", "meat", "tasty"})));
        recipes.add(new Recipe("Lasagna", "Alon", "1234", "Pasta cake", "cook the lasagna sheets in water about halfway through to done and then layer in the oven with pasta sauce and cheese", null, null, null, Arrays.asList(new String[]{"lasagna", "pasta", "OMG"})));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProfile.setLayoutManager(layoutManager);

        ProfileRecipeAdapter profileRecipeAdapter = new ProfileRecipeAdapter(recipes);
        rvProfile.setAdapter(profileRecipeAdapter);
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
                    tvProfileHello.setText("Hey " + username[0] + "!");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profileBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }

        if (v.getId() == R.id.logoutProfileBtn) {
            mAuth.signOut();
            Intent i = new Intent(this, Login.class);
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