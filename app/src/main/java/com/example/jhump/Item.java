package com.example.jhump;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Item implements Parcelable {
    private String name = "";
    private ArrayList<Bitmap> picture;
    private String seller = "";
    private String condition = "";
    private String category = "";
    private double price = 0.0;
    private String description = "";
    private boolean sold;
    private String id;
    private String sellerID;

    private static long nextId = 0;
    //layout must say us.dollars
    //pass in user data
    //are all mandated? If not, store temp values

    public Item(String name, ArrayList<Bitmap> picture, String seller, String sellerID, String condition,
                String category, String description, double price, boolean sold) {
        this.name = name;
        this.seller = seller;
        this.picture = picture;
        this.condition = condition;
        this.category = category;
        this.price = price;
        this.description = description;
        this.sold = sold;
        this.id = String.format("%04d", ++nextId);
        this.sellerID = sellerID;
    }

    protected Item(Parcel in) {
        name = in.readString();
        picture = in.createTypedArrayList(Bitmap.CREATOR);
        seller = in.readString();
        condition = in.readString();
        category = in.readString();
        price = in.readDouble();
        description = in.readString();
        id = in.readString();
        sold = in.readByte() != 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() { return this.name; }

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

    public double getPrice() {
        return this.price;
    }

    public String getSellerID() { return this.sellerID; }

    public ArrayList<Bitmap> getPicture() {
        return this.picture;
    }

    public String getId() { return this.id; }

    public void setSellerID(String sellerID) { this.sellerID = sellerID; }

    public void setName(String name) {
        this.name = name;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(picture);
        dest.writeString(seller);
        dest.writeString(condition);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeByte((byte) (sold ? 1 : 0));
    }
}
