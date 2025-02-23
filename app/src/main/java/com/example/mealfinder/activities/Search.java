package com.example.mealfinder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
        searchBackBtn.setOnClickListener(this);
        ibSearchBtn.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);

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
                RecentsAdapter recipeAdapter = new RecentsAdapter(recents);
                rvSearch.setAdapter(recipeAdapter);
//                recipeAdapter.setmOnItemClickListener(onItemClickListener);
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
            finish();
        }
        if (v.getId() == R.id.ibSearchBtn) {
            SearchQuery query = new SearchQuery(etSearch.getText().toString(), mAuth.getCurrentUser().getUid());
            db.getReference().child("recents").child(query.getSearchId()).setValue(query);
            etSearch.setText("");
        }
    }
}