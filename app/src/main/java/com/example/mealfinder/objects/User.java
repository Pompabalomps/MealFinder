package com.example.mealfinder.objects;

import android.net.Uri;

import com.example.mealfinder.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private String id;
    private String username;
    private String email;
    private Date lastLogin;



    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }
    public User() {
        this.id = null;
        this.username = null;
        this.email = null;
        this.lastLogin = new Date(0);
    }
    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastLogin = new Date();
    }
}
