package com.example.mealfinder.objects;

import java.io.File;

public class User {
    private String id;
    private String username;
    private String email;
    private File image;
    private int followNum;
    private int lastLogin;

    public File getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getFollowNum() {
        return followNum;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public User() {
        this.id = null;
        this.username = null;
        this.email = null;
        this.image = null;
        this.followNum = 0;
        this.lastLogin = 0;
    }
    public User(String id, String username, String email, File image, int followNum, int lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.image = image;
        this.followNum = followNum;
        this.lastLogin = lastLogin;
    }
}
