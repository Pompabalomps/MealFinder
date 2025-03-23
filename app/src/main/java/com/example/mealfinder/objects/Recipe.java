package com.example.mealfinder.objects;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recipe implements Serializable {
    private String id; // Recipe id (userId+createDate)
    private Date createDate; // Recipe creation date
    private String name; // Recipe name
    private String creatorName; // Recipe creator name
    private String userId; // Recipe creator's user ID
    private String desc; // Recipe description
    private String steps; // Steps for recipe
    private String img1; // images
    private String img2;
    private String img3;
    private List<String> tags; // Array of recipe tags
    private List<String> userLikes;


    public Recipe(String name, String creatorName, String userId, String desc, String steps, String img1, String img2, String img3, List<String> tags){
        this.createDate = new Date();
        this.id = userId + createDate.getTime();
        this.name = name;
        this.creatorName = creatorName;
        this.userId = userId;
        this.desc = desc;
        this.steps = steps;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tags = tags;
        this.userLikes = new ArrayList<>();
    }

    public Recipe() {
        this.createDate = new Date();
        this.id = "" + createDate.getTime();
        this.name = "";
        this.creatorName = "";
        this.userId = "";
        this.desc = "";
        this.steps = "";
        this.img1 = "";
        this.img2 = "";
        this.img3 = "";
        this.tags = null;
        this.userLikes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public int likes() {
        return this.userLikes.size();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getUserLikes() {
        return userLikes;
    }
}
