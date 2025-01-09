package com.example.mealfinder.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EditRecipe extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Edit Recipe Activity";
    private ImageButton editRBackBtn;
    private Button saveRecipeBtn;
    private EditText etRecipeName;
    private EditText etRecipeDesc;
    private EditText etRecipeInstr;
    private EditText etRecipeTags;
    private ImageButton addImageIb1;
    private ImageButton addImageIb2;
    private ImageButton addImageIb3;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private final String[] u = new String[1];
    private String username;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;
    private Bitmap bitmap;
    private int currentIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        editRBackBtn = findViewById(R.id.editRBackBtn);
        saveRecipeBtn = findViewById(R.id.saveRecipeBtn);
        etRecipeName = findViewById(R.id.etRecipeName);
        etRecipeDesc = findViewById(R.id.etRecipeDesc);
        etRecipeInstr = findViewById(R.id.etRecipeInstr);
        etRecipeTags = findViewById(R.id.etRecipeTags);
        addImageIb1 = findViewById(R.id.addImageIb1);
        addImageIb2 = findViewById(R.id.addImageIb2);
        addImageIb3 = findViewById(R.id.addImageIb3);
        editRBackBtn.setOnClickListener(this);
        saveRecipeBtn.setOnClickListener(this);
        addImageIb1.setOnClickListener(this);
        addImageIb2.setOnClickListener(this);
        addImageIb3.setOnClickListener(this);

        readUsername(s -> {
            u[0] = s;
            username = u[0];
        });

        String s= "Tags (Separate With , )";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.666666f), 0,5, 0); // set size
        etRecipeTags.setHint(ss1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editRBackBtn) {
            finish();
        }

        if (v.getId() == R.id.saveRecipeBtn) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String[] tags = etRecipeTags.getText().toString().split(",");
            Recipe recipe = new Recipe(etRecipeName.getText().toString(), username, currentUser.getUid().toString(), etRecipeDesc.getText().toString(), etRecipeInstr.getText().toString(), "img1","img2","img3", Arrays.asList(tags));
            db.getReference().child("recipes").child(recipe.getId()).setValue(recipe);
            Intent i = new Intent(this, RecipeList.class);
            startActivity(i);
        }

        if (v.getId() == R.id.addImageIb1) {
            currentIB = 1;
            dispatchTakePictureIntent();
        }
        if (v.getId() == R.id.addImageIb2) {
            currentIB = 2;
            dispatchTakePictureIntent();
        }
        if (v.getId() == R.id.addImageIb3) {
            currentIB = 3;
            dispatchTakePictureIntent();
        }
    }

    private void readUsername(MyCallback myCallback) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db.getReference().child("users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    myCallback.onCallback(((Map<String, Object>)task.getResult().getValue()).get("username").toString());
                }
            }
        });
    }

    private interface MyCallback {
        void onCallback(String s);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            switch (currentIB) {
                case 1:
                    setPic(addImageIb1);
                    break;
                case 2:
                    setPic(addImageIb2);
                    break;
                case 3:
                    setPic(addImageIb3);
                    break;
                default:
            }
        }
    }

    private void setPic(ImageButton takePhotoIb) {
        int targetW = takePhotoIb.getWidth();
        int targetH = takePhotoIb.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//        takePhotoIb.setRotation(90);
        takePhotoIb.setImageBitmap(bitmap);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.mealfinder.activities.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}