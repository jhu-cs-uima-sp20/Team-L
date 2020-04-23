package com.example.jhump;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Textbook extends Item {
    String subject;
    public Textbook(String name, ArrayList<Bitmap> picture, String seller, String condition, String category, String description, double price, boolean sold, String subject) {
        super(name, picture, seller, condition, category, description, price, sold);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
