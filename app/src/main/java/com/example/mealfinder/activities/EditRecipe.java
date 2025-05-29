package com.example.mealfinder.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import com.example.mealfinder.R;
import com.example.mealfinder.objects.Recipe;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class EditRecipe extends AppCompatActivity implements View.OnClickListener {
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
    private FirebaseStorage stor;
    private UploadTask uploadTask;
    private static Uri downloadUri;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;
    private static Uri img1downloadUrl;
    private static Uri img2downloadUrl;
    private static Uri img3downloadUrl;
    private Bitmap bitmap;
    private static int currentIB;
    private Recipe rec;
    private LinearLayout LLsaveRecipe;
    private Drawable enabledBG;
    private Drawable disabledBG;
    private final String[] u = new String[1];
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        stor = FirebaseStorage.getInstance();
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
        saveRecipeBtn.setEnabled(false);
        Resources res = getResources();
        enabledBG = ResourcesCompat.getDrawable(res, R.drawable.button_gradient2_enabled, null);
        disabledBG = ResourcesCompat.getDrawable(res, R.drawable.button_gradient2_disabled, null);
        LLsaveRecipe = findViewById(R.id.LLsaveRecipe);
        LLsaveRecipe.setBackground(disabledBG);
        saveRecipeBtn.setAlpha(0.25f);
        img1downloadUrl = Uri.EMPTY;
        img2downloadUrl = Uri.EMPTY;
        img3downloadUrl = Uri.EMPTY;

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
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }

        if (v.getId() == R.id.saveRecipeBtn) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String[] tags = etRecipeTags.getText().toString().split(",");
            rec = new Recipe(etRecipeName.getText().toString(),
                    username,
                    currentUser.getUid().toString(),
                    etRecipeDesc.getText().toString(),
                    etRecipeInstr.getText().toString(),
                    !img1downloadUrl.equals(Uri.EMPTY) ? img1downloadUrl.toString() : "",
                    !img2downloadUrl.equals(Uri.EMPTY) ? img2downloadUrl.toString() : "",
                    !img3downloadUrl.equals(Uri.EMPTY) ? img3downloadUrl.toString() : "",
                    Arrays.asList(tags));
            db.getReference().child("recipes").child(rec.getId()).setValue(rec);
            Intent i = new Intent(this, RecipeDetails.class);
            i.putExtra("rec", rec);
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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            switch (currentIB) {
                case 1:
                    setPic(addImageIb1);
                    uploadFullImage();
//                    uploadCompressedImage();
                    break;
                case 2:
                    setPic(addImageIb2);
                    uploadFullImage();
//                    uploadCompressedImage();
                    break;
                case 3:
                    setPic(addImageIb3);
                    uploadFullImage();
//                    uploadCompressedImage();
                    break;
                default:
            }
        }
    }

    private void readUsername(MyCallback myCallback) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db.getReference().child("users")
                .child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

//    private void uploadCompressedImage() {
//        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
//                .build();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Matrix matrix = new Matrix();
//        matrix.postRotate(0);
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
//        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
//        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        Uri file = Uri.fromFile(new File(currentPhotoPath));
//        final StorageReference reverseRef = stor.getReference().child("compressed_images").child(file.getLastPathSegment());
//        UploadTask uploadTask = reverseRef.putBytes(data, metadata);
//        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//
//                return reverseRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    downloadUri = task.getResult();
//                    switch (currentIB) {
//                        case 1:
//                            img1downloadUrl = downloadUri;
//                            break;
//                        case 2:
//                            img2downloadUrl = downloadUri;
//                            break;
//                        case 3:
//                            img3downloadUrl = downloadUri;
//                            break;
//                        default:
//                    }
//                    saveRecipeBtn.setEnabled(true);
//                    LLsaveRecipe.setBackground(enabledBG);
//                    saveRecipeBtn.setAlpha(1f);
//                }
//            }
//        });
//    }

    private void uploadFullImage() {
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();
        Uri file = Uri.fromFile(new File(currentPhotoPath));
        final StorageReference reverseRef = stor.getReference().child("images").child(file.getLastPathSegment());
        uploadTask = reverseRef.putFile(file, metadata);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return reverseRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    switch (currentIB) {
                        case 1:
                            img1downloadUrl = downloadUri;
                            break;
                        case 2:
                            img2downloadUrl = downloadUri;
                            break;
                        case 3:
                            img3downloadUrl = downloadUri;
                            break;
                        default:
                    }
                    saveRecipeBtn.setEnabled(true);
                    LLsaveRecipe.setBackground(enabledBG);
                    saveRecipeBtn.setAlpha(1f);
                }
            }
        });
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