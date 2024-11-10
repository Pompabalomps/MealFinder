package com.example.mealfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView tvProfileEmail;
    private ImageButton profileBackBtn;
    private Button logoutProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        mAuth = FirebaseAuth.getInstance();
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
        tvProfileEmail.setText(user.getEmail().toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profileBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }

        if (v.getId() == R.id.logoutProfileBtn) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }
}