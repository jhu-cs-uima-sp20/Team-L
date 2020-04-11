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
    private ArrayList<Bitmap> pics;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        this.setTitle("Item Description");

        Intent intent = getIntent();
        viewPager = findViewById(R.id.viewPager);
        Button contact = findViewById(R.id.contact);
        TextView nameView = findViewById(R.id.name);
        TextView priceView = findViewById(R.id.price);
        TextView descriptionView = findViewById(R.id.description);
        TextView sellerView = findViewById(R.id.seller);
        TextView conView = findViewById(R.id.condition);
        TextView catView = findViewById(R.id.category);
        TextView subjectView = findViewById(R.id.subject);

        sellerView.setText("Seller: " + intent.getStringExtra("item seller"));
        nameView.setText(intent.getStringExtra("item_name"));
        conView.setText("Condition: " + intent.getStringExtra("condition"));
        catView.setText("Category: " + intent.getStringExtra("category"));
        priceView.setText("Price: $" + intent.getDoubleExtra("price", 20));
        descriptionView.setText("Description: " + intent.getStringExtra("description"));
        subjectView.setText("Subject: " + intent.getStringExtra("subject"));


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
