package com.example.mealfinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity implements View.OnClickListener {
    private Button followBtn;
    private Button addToList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        Intent i = getIntent();
        Recipe rec = (Recipe) i.getSerializableExtra("rec");
        followBtn = findViewById(R.id.followBtn);
        addToList = findViewById(R.id.addToListBtn);
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
        followBtn.setOnClickListener(this);
        addToList.setOnClickListener(this);
        recipeDetailsBackBtn.setOnClickListener(this);

        tvRecipeName.setText(rec.getName());
        tvRecipeCreatorName.setText(rec.getCreatorName());
        tvRecipeLikeCounter.setText("" + rec.getLikes());
        ArrayList<String> images = new ArrayList<String>();
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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.followBtn) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
        }
        if (v.getId() == R.id.addToListBtn) {
            Intent i = new Intent(this, EditRecipe.class);
            startActivity(i);
        }
        if (v.getId() == R.id.recipeDetailsBackBtn) {
            finish();
        }
    }

    private void setPic(ImageView takePhotoIv, String photoPath) {
        int targetW = takePhotoIv.getWidth();
        int targetH = takePhotoIv.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
//        takePhotoIv.setRotation(90);
        takePhotoIv.setImageBitmap(bitmap);
    }
}