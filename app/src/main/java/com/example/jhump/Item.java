package com.example.jhump;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private List<Bitmap> picture;
    private String seller;
    private String condition;
    private String category;
    private double price;
    private String description;
    private boolean sold = false;
    //layout must say us.dollars
    //arrayList of BufferedImages
    //pass in user data
    //are all mandated? If not, store temp values
    /*try {
    File file = new File();
    picture = new BufferedImage
    }
    */
    public Item(List<Bitmap> picture, String seller, String condition,
                String category, String description, double price, boolean sold) {
        this.seller = seller;
        this.picture = picture;
        this.condition = condition;
        this.category = category;
        this.price = price;
        this.description = description;
        this.sold = sold;
    }

    public boolean isSold() {
        return sold;
    }

    public String getSeller() {
        return this.seller;
    }

    public String getCondition() {
        return this.condition;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {return this.price;}

    public List<Bitmap> getPicture() {
        return this.picture;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setPrice(double price) {this.price = price;}

    //add only?
    //add and subtract pics?
    //subtract everytime and then add back
    public void setPicture(Bitmap pic) {
        this.picture.add(pic);
    }

    @Override
    public String toString() {
        return "";
    }

}
