package com.matthewfraser.cp470_losty;

import android.graphics.Bitmap;

public class ItemModel {

    private Bitmap image;
    private String name;
    private String brand;
    private String color;
    private String desc;
    private String userID;
    private String postID;

    public ItemModel(Bitmap image, String name, String brand, String color, String desc, String userID, String postID) {
        this.image = image;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.desc = desc;
        this.userID = userID;
        this.postID = postID;
    }

    public Bitmap getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getBrand() {
        return brand;
    }
    public String getColor() {
        return color;
    }
    public String getDesc() {
        return desc;
    }
    public String getUserID() { return userID; }
    public String getPostID() { return postID; }
}
