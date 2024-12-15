package com.example.mealfinder.objects;

import java.util.Date;

public class Recipe {
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
    private int likes; // Amount of concurrent recipe likes
    private double rating; // Recipe rating
    private String[] tags; // Array of recipe tags


    public Recipe(String name, String creatorName, String userId, String desc, String steps, String img1, String img2, String img3, String[] tags){
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
        this.likes = 0;
        this.rating = 0;
        this.tags = tags;
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

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
