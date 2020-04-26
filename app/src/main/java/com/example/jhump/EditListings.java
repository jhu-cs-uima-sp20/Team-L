package com.example.jhump;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

class EditListings extends AppCompatActivity {
    private Button post;
    private Button cancel;
    private List<Bitmap> pics;
    private ImageButton gallery;
    private Spinner category;
    private Spinner condition;
    private EditText listingName;
    private EditText price;
    private EditText description;
    private String textCon;
    private String textCat;
    FirebaseDatabase db;
    private DatabaseReference dbref;
    SharedPreferences userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listings);
        getSupportActionBar().setTitle("Edit Listing");
        //pull all intent information through here

    }

}
