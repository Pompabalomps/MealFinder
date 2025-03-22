package com.example.mealfinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends AppCompatActivity implements View.OnClickListener {
    private Button addToList;
    private Button RemoveRecipeBtn;
    private ImageButton recipeDetailsBackBtn;
    private TextView tvRecipeName;
    private TextView tvRecipeCreatorName;
    private TextView tvRecipeLikeCounter;
    private ImageView ivRecImg1_1;
    private ImageView ivRecImg2_1;
    private ImageView ivRecImg2_2;
    private ImageView ivRecImg3_1;
    private ImageView ivRecImg3_2;
    private ImageView ivRecImg3_3;
    private Bitmap bitmap;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private String recId;
    private String recDesc;
    private String recSteps;
    private LinearLayout LLAddRecipe;
    private LinearLayout LLRemoveRecipe;
    private ArrayList<String> savedRecipes;
    private TextView tvRecipeDesc;
    private Button ReadMoreBtn;
    private TextView tvRecipeSteps;
    private Button ReadMoreBtn2;
    private List<String> recUserLikes;
    private ImageButton likeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        db = FirebaseDatabase.getInstance();
        Intent i = getIntent();
        Recipe rec = (Recipe) i.getSerializableExtra("rec");
        recId = rec.getId();
        recDesc = rec.getDesc();
        recSteps = rec.getSteps();
        recUserLikes = rec.getUserLikes();
        mAuth = FirebaseAuth.getInstance();
        addToList = findViewById(R.id.addToListBtn);
        RemoveRecipeBtn = findViewById(R.id.RemoveRecipeBtn);
        recipeDetailsBackBtn = findViewById(R.id.recipeDetailsBackBtn);
        tvRecipeName = findViewById(R.id.tvRecipeDetailsName);
        tvRecipeCreatorName = findViewById(R.id.tvRecipeCreatorName);
        tvRecipeLikeCounter = findViewById(R.id.tvRecipeLikeCounter);
        ivRecImg1_1 = findViewById(R.id.ivRecImg1_1);
        ivRecImg2_1 = findViewById(R.id.ivRecImg2_1);
        ivRecImg2_2 = findViewById(R.id.ivRecImg2_2);
        ivRecImg3_1 = findViewById(R.id.ivRecImg3_1);
        ivRecImg3_2 = findViewById(R.id.ivRecImg3_2);
        ivRecImg3_3 = findViewById(R.id.ivRecImg3_3);
        LLAddRecipe = findViewById(R.id.LLAddRecipe);
        LLRemoveRecipe = findViewById(R.id.LLRemoveRecipe);
        tvRecipeDesc = findViewById(R.id.tvRecipeDesc);
        tvRecipeSteps = findViewById(R.id.tvRecipeSteps);
        ReadMoreBtn = findViewById(R.id.readMoreBtn);
        ReadMoreBtn2 = findViewById(R.id.readMoreBtn2);
        likeBtn = findViewById(R.id.likeBtn);
        addToList.setOnClickListener(this);
        RemoveRecipeBtn.setOnClickListener(this);
        recipeDetailsBackBtn.setOnClickListener(this);
        ReadMoreBtn.setOnClickListener(this);
        ReadMoreBtn2.setOnClickListener(this);
        likeBtn.setOnClickListener(this);

        LLRemoveRecipe.setVisibility(View.GONE);

        tvRecipeName.setText(rec.getName());
        tvRecipeCreatorName.setText(rec.getCreatorName());
        tvRecipeLikeCounter.setText("" + rec.likes());
        tvRecipeDesc.setText(rec.getDesc());
        tvRecipeSteps.setText(rec.getSteps());
        ArrayList<String> images = new ArrayList<>();
        if (!rec.getImg1().isEmpty())
            images.add(rec.getImg1());
        if (!rec.getImg2().isEmpty())
            images.add(rec.getImg2());
        if (!rec.getImg3().isEmpty())
            images.add(rec.getImg3());

        switch (images.size()) {
            case 1:
                Glide.with(ivRecImg1_1.getContext())
                        .load(images.get(0))
                        .into(ivRecImg1_1);
                ivRecImg1_1.setVisibility(View.VISIBLE);
                ivRecImg2_1.setVisibility(View.GONE);
                ivRecImg2_2.setVisibility(View.GONE);
                ivRecImg3_1.setVisibility(View.GONE);
                ivRecImg3_2.setVisibility(View.GONE);
                ivRecImg3_3.setVisibility(View.GONE);
                break;
            case 2:
                Glide.with(ivRecImg2_1.getContext())
                        .load(images.get(0))
                        .into(ivRecImg2_1);
                Glide.with(ivRecImg2_2.getContext())
                        .load(images.get(1))
                        .into(ivRecImg2_2);
                ivRecImg1_1.setVisibility(View.GONE);
                ivRecImg2_1.setVisibility(View.VISIBLE);
                ivRecImg2_2.setVisibility(View.VISIBLE);
                ivRecImg3_1.setVisibility(View.GONE);
                ivRecImg3_2.setVisibility(View.GONE);
                ivRecImg3_3.setVisibility(View.GONE);
                break;
            case 3:
                Glide.with(ivRecImg3_1.getContext())
                        .load(images.get(0))
                        .into(ivRecImg3_1);
                Glide.with(ivRecImg3_2.getContext())
                        .load(images.get(1))
                        .into(ivRecImg3_2);
                Glide.with(ivRecImg3_3.getContext())
                        .load(images.get(2))
                        .into(ivRecImg3_3);
                ivRecImg1_1.setVisibility(View.GONE);
                ivRecImg2_1.setVisibility(View.GONE);
                ivRecImg2_2.setVisibility(View.GONE);
                ivRecImg3_1.setVisibility(View.VISIBLE);
                ivRecImg3_2.setVisibility(View.VISIBLE);
                ivRecImg3_3.setVisibility(View.VISIBLE);
                break;
            default:
                ivRecImg1_1.setVisibility(View.GONE);
                ivRecImg2_1.setVisibility(View.GONE);
                ivRecImg2_2.setVisibility(View.GONE);
                ivRecImg3_1.setVisibility(View.GONE);
                ivRecImg3_2.setVisibility(View.GONE);
                ivRecImg3_3.setVisibility(View.GONE);
        }

        db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("savedRecipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                savedRecipes = new ArrayList<>();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    String savedRec = recipeSnapshot.getValue(String.class);
                    savedRecipes.add(savedRec);
                }

                if (savedRecipes.contains(recId)) {
                    LLAddRecipe.setVisibility(View.GONE);
                    LLRemoveRecipe.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addToListBtn) {
            Intent i = new Intent(this, RecipeList.class);
            db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("savedRecipes").child(recId).setValue(recId);
            startActivity(i);
        }
        if (v.getId() == R.id.RemoveRecipeBtn) {
            Intent i = new Intent(this, RecipeList.class);
            db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("savedRecipes").child(recId).removeValue();
            startActivity(i);
        }
        if (v.getId() == R.id.recipeDetailsBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
        if (v.getId() == R.id.readMoreBtn) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Recipe Description")
                    .setMessage(recDesc)
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            alertDialog.show();
        }
        if (v.getId() == R.id.readMoreBtn2) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Recipe Instructions")
                    .setMessage(recSteps)
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            alertDialog.show();
        }
        if (v.getId() == R.id.likeBtn) {
            if (recUserLikes.contains(mAuth.getCurrentUser().getUid())) {
                recUserLikes.remove(mAuth.getCurrentUser().getUid());
                db.getReference().child("recipes").child(recId).child("userLikes").setValue(recUserLikes);
                tvRecipeLikeCounter.setText("" + recUserLikes.size());
            } else {
                recUserLikes.add(mAuth.getCurrentUser().getUid());
                db.getReference().child("recipes").child(recId).child("userLikes").setValue(recUserLikes);
                tvRecipeLikeCounter.setText("" + recUserLikes.size());
            }
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