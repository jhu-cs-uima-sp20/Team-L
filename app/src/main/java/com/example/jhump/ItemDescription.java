package com.example.jhump;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class ItemDescription extends AppCompatActivity {



    private String seller;
    private String listing;
    private double price;
    private String description;
    private String condition;
    private String category;
    private int position;
    private ArrayList<Bitmap> pics;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        //the code written is to implement an image slider with moving dots which has
        //already been set for activity_item_description, change design in this file or copy
        //items over to other file -chioma
        this.setTitle("Item Description");

        Intent intent = getIntent();
        Item item = (Item) getIntent().getSerializableExtra("item");
        seller = item.getSeller();
        listing = item.getName();
        description = item.getDescription();
        price = item.getPrice();
        position = getIntent().getIntExtra("position", 0);
        condition = item.getCondition();
        category = item.getCategory();
        //pics = intent.getParcelableArrayListExtra("pics");








        /*seller = intent.getStringExtra("seller");
        listing = intent.getStringExtra("name");
        description = intent.getStringExtra("deadline");
        price = intent.getDoubleExtra("price", 0);
        position = intent.getIntExtra("position", 0);
        condition = intent.getStringExtra("condition");
        category = intent.getStringExtra("category");
        //pics = intent.getParcelableArrayListExtra("pics");


         */
        viewPager = findViewById(R.id.viewPager);
        Button contact = findViewById(R.id.contact);
        TextView nameView = findViewById(R.id.itemName);
        TextView priceView = findViewById(R.id.price);
        TextView descriptionView = findViewById(R.id.description);
        TextView sellerView = findViewById(R.id.seller);
        TextView conView = findViewById(R.id.condition);
        TextView catView = findViewById(R.id.category);

        sellerView.setText("Seller: John Doe");
        nameView.setText(listing);
        String temp = "Condition: " +condition;
        conView.setText(temp);
        temp = "Category: " + category;
        catView.setText(temp);
        temp = "Price: $" + Double.toString(price);
        priceView.setText(temp);
        descriptionView.setText(description);


      /*  ViewPageAdapter vpa = new ViewPageAdapter(this, pics);
        viewPager.setAdapter(vpa);

       */
        //should we place a pic that shows only if this item is sold?
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getUser = new Intent(ItemDescription.this, MyProfile.class);
                startActivity(getUser);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //taskView.setText(assignee);
    }

//        //Item item;
//        //show sold? or not sold?


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
