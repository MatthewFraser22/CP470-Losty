package com.matthewfraser.cp470_losty;

import android.graphics.Bitmap;

public class ItemModel {

    private Bitmap image;
    private String name;
    private String brand;
    private String color;
    private String desc;

    public ItemModel(Bitmap image, String name, String brand, String color, String desc) {
        this.image = image;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.desc = desc;
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
}
