package com.example.mealfinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mealfinder.R;
import com.example.mealfinder.adapters.RecentsAdapter;
import com.example.mealfinder.adapters.RecipeAdapter;
import com.example.mealfinder.objects.Recipe;
import com.example.mealfinder.objects.SearchQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private ImageButton searchBackBtn;
    private RecyclerView rvSearch;
    private FirebaseDatabase db;
    private ArrayList<SearchQuery> recents;
    private ImageButton ibSearchBtn;
    private EditText etSearch;
    private ImageView ivRecents;
    private View.OnClickListener onItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        searchBackBtn = findViewById(R.id.searchBackBtn);
        rvSearch = findViewById(R.id.rvSearch);
        ibSearchBtn = findViewById(R.id.ibSearchBtn);
        etSearch = findViewById(R.id.etSearch);
        ivRecents = findViewById(R.id.ivRecents);
        searchBackBtn.setOnClickListener(this);
        ibSearchBtn.setOnClickListener(this);

        ivRecents.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);

        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                SearchQuery recent = recents.get(position);
                etSearch.setText(recent.getQuery());
            }
        };

        db.getReference().child("recents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recents = new ArrayList<>();
                for (DataSnapshot recentSnapshot : dataSnapshot.getChildren()) {
                    SearchQuery query = recentSnapshot.getValue(SearchQuery.class);
                    if (query.getSearchId().startsWith(mAuth.getCurrentUser().getUid())) {
                        recents.add(query);
                    }
                }

                if (!recents.isEmpty()) {
                    ivRecents.setVisibility(View.VISIBLE);
                }

                RecentsAdapter recentsAdapter = new RecentsAdapter(recents);
                rvSearch.setAdapter(recentsAdapter);
                recentsAdapter.setmOnItemClickListener(onItemClickListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        recents = new ArrayList<SearchQuery>();
//        recents.add(new SearchQuery("Smoked Brisket", mAuth.getCurrentUser().getUid()));
//        recents.add(new SearchQuery("neapolitan pizza", mAuth.getCurrentUser().getUid()));
//        recents.add(new SearchQuery("Honey lime chicken wings", mAuth.getCurrentUser().getUid()));

//        RecentsAdapter recentsAdapter = new RecentsAdapter(recents);
//        rvSearch.setAdapter(recentsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() { }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchBackBtn) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
        if (v.getId() == R.id.ibSearchBtn) {
            SearchQuery query = new SearchQuery(etSearch.getText().toString(), mAuth.getCurrentUser().getUid());
            db.getReference().child("recents").child(query.getSearchId()).setValue(query);
            etSearch.setText("");
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