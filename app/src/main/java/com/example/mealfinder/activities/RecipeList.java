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
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.mealfinder.R;
import com.example.mealfinder.workers.MyWorker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RecipeList extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private String TAG = "Recipe List Activity";
    private TextView tvListTitle;
    private Button recipe_item1Btn;
    private ImageButton listBackBtn;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String[] username = new String[1];
        db.getReference().child("users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
        if (v.getId() == R.id.recipe_item1Btn) {
            Intent i = new Intent(this, RecipeDetails.class);
            startActivity(i);
        }

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