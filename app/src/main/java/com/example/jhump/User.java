package com.example.jhump;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String email;
    private String number = "";
    private String password;
    //implement strong password? with toasts?
    //do we want number? Or optional?
    //TOASTS
    private ArrayList<Item> listing;

    public User(String name, String email, String password, String number, Item listing) {
        this.name = name;
        this.email = email;
        this.password = password;
        if(number != null) {
            this.number = number;
        }
        this.listing.add(listing);
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public ArrayList<Item> getListing() {
        return listing;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setListing(Item listing) {
        this.listing.add(listing);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "";
    }
}
