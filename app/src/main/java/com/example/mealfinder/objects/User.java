package com.example.mealfinder.objects;

import android.net.Uri;

import java.io.File;
import java.util.Date;

public class User {
    private String id;
    private String username;
    private String email;
    private int followNum;
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

    public int getFollowNum() {
        return followNum;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public User() {
        this.id = null;
        this.username = null;
        this.email = null;
        this.followNum = 0;
        this.lastLogin = new Date(0);
    }
    public User(String id, String username, String email, int followNum, Date lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.followNum = followNum;
        this.lastLogin = lastLogin;
    }
}
