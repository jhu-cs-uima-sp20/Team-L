package com.example.jhump;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

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
    private Switch sold;
    FirebaseDatabase db;
    private DatabaseReference dbref;
    SharedPreferences userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listings);
        getSupportActionBar().setTitle("Edit Listing");
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference();
        Intent edit = getIntent();
        post = findViewById(R.id.post);
        cancel = findViewById(R.id.cancel);
        listingName = findViewById(R.id.listing);
        gallery = findViewById(R.id.pic);
        condition = findViewById(R.id.condition);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        condition = findViewById(R.id.condition);
        category = findViewById(R.id.category);
        sold = findViewById(R.id.soldSwitch);
        listingName.setText(edit.getStringExtra("listing"));
        String itemID = edit.getStringExtra("ID");
        //edit.getStringExtra("category");
        //edit.getStringExtra("condition");
        description.setText(edit.getStringExtra("description"));
        price.setText(edit.getStringExtra("price"));
        sold.setChecked(edit.getBooleanExtra("sold", false));
        sold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        String sellerID = edit.getStringExtra("sellerID");
        //change picture
        //buttons
    }

}
