package com.example.mealfinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.mealfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Login Activity";
    Button loginBtn;
    Button signupScreenBtn;
    private FirebaseAuth mAuth;
    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private TextView tvLoginFail;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        signupScreenBtn = findViewById(R.id.signupScreenBtn);
        tvLoginFail = findViewById(R.id.tvLoginFail);
        loginBtn.setOnClickListener(this);
        signupScreenBtn.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null)
        {
            reload(currentUser);
        }
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "Sign In SUCCESS");
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        } else {
            Log.d(TAG, "Sign In FAILURE");
            tvLoginFail.setText("Email/Password is incorrect");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                signIn(email, password);
            }
        }
        if (v.getId() == R.id.signupScreenBtn) {
            Intent i = new Intent(this, Signup.class);
            startActivity(i);
        }
    }

    private void reload(FirebaseUser user) {
        mAuth.signOut();
    }
}