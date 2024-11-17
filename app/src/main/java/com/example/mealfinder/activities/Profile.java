package com.example.mealfinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private String TAG = "Profile Activity";
    private TextView tvProfileEmail;
    private ImageButton profileBackBtn;
    private Button logoutProfileBtn;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        profileBackBtn = findViewById(R.id.profileBackBtn);
        logoutProfileBtn = findViewById(R.id.logoutProfileBtn);
        profileBackBtn.setOnClickListener(this);
        logoutProfileBtn.setOnClickListener(this);
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
                    tvProfileEmail.setText("Hey " + username[0] + "!");
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
}