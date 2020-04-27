package com.example.jhump;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
    public Uri imguri;
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
        price.setText(Double.toString(edit.getDoubleExtra("price", 0.0)));
        sold.setChecked(edit.getBooleanExtra("sold", false));
        sold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        //change picture
        //buttons
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                db = FirebaseDatabase.getInstance();
                dbref = db.getReference();
                String name = userLogin.getString("name", "John Doe");
                String sellerID = userLogin.getString("id", "John Doe");
                String links = imguri.toString();
                Item newItem = new Item(listingName.getText().toString(), links, name,
                        sellerID, textCon, textCat, description.getText().toString(),
                        Double.parseDouble(price.getText().toString()), false);
                dbref.child("listings").child(newItem.getId()).setValue(newItem);
                NavigationDrawer.aa.add(newItem);
                break;
        }

    }

}
