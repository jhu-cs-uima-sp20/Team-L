package com.example.jhump;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class ItemDescription extends AppCompatActivity {
    private ImageView viewPager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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


        String seller = "Seller: " + intent.getStringExtra("seller");
        String condition = "Condition: " + intent.getStringExtra("condition");
        String category = "Category: " + intent.getStringExtra("category");
        String price = "Price: $" + intent.getDoubleExtra("price", 0.00);
        String desc = "Description: " + intent.getStringExtra("description");
        //String subject = "Subject: " + intent.getStringExtra("subject");
        final String sellerID = intent.getStringExtra("subject");
        sellerView.setText(seller);
        nameView.setText(intent.getStringExtra("listing"));
        conView.setText(condition);
        catView.setText(category);
        priceView.setText(price);
        descriptionView.setText(desc);
        String link = intent.getStringExtra("picture");
        viewPager.setImageURI(Uri.parse((link)));
        //subjectView.setText(subject);


       /* String filename = getIntent().getStringExtra("pics");
        Bitmap bit = null;
        FileInputStream is = null;
        try {
            is = this.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bit = BitmapFactory.decodeStream(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewPager.setImageBitmap(bit);
*/
        //should we place a pic that shows only if this item is sold?
        contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent getUser = new Intent(ItemDescription.this, UserProfile.class);
                getUser.putExtra("sellerID", sellerID);
                startActivity(getUser);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //taskView.setText(assignee);
    }




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
