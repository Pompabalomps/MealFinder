package com.example.mealfinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mealfinder.R;
import com.example.mealfinder.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    Button signupBtn;
    ImageButton backBtn;
    private FirebaseAuth mAuth;
    TextView tvFail;
    EditText etSignupEmail;
    EditText etSignupUsername;
    EditText etSignupPassword;
    EditText etSignupPassword2;
    private final String TAG = "Signup Activity";
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        signupBtn = findViewById(R.id.signupBtn);
        backBtn = findViewById(R.id.signupBackBtn);
        tvFail = findViewById(R.id.tvSignupFail);
        etSignupEmail = findViewById(R.id.etSignupEmail);
        etSignupUsername = findViewById(R.id.etSignupUsername);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etSignupPassword2 = findViewById(R.id.etSignupPassword2);
        signupBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signupBtn) {
            String email = etSignupEmail.getText().toString();
            String password = etSignupPassword.getText().toString();
            String password2 = etSignupPassword2.getText().toString();
            String username = etSignupUsername.getText().toString();
            if (!password.equals(password2)) {
                tvFail.setText("Password doesn't match");
            } else if (username.isEmpty()) {
                tvFail.setText("Username cannot be empty");
            } else {
                tvFail.setText("");
                createAccount(email, password);
            }
        } else if (v.getId() == R.id.signupBackBtn) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    // [END on_start_check_user]
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "Sign Up SUCCESS");
            String username = etSignupUsername.getText().toString();
            User u = new User(user.getUid(), username, user.getEmail());
            db.getReference()
                    .child("users")
                    .child(user.getUid()).setValue(u).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User data successfully added to database");
                } else {
                    Log.e(TAG, "Error adding user data", task.getException());
                }
            });
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        } else {
            Log.d(TAG, "Sign Up FAILURE");
            tvFail.setText("Sign up failed");
        }
    }

    private void reload() {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
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