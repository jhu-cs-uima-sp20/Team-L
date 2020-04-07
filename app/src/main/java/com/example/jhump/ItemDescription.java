package com.example.jhump;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class ItemDescription extends AppCompatActivity {



    private String seller;
    private String listing;
    private double price;
    private String description;
    private String condition;
    private String category;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);
        this.setTitle("Item Description");

        Intent intent = getIntent();
        seller = intent.getStringExtra("seller");
        listing = intent.getStringExtra("name");
        description = intent.getStringExtra("deadline");
        price = intent.getDoubleExtra("price", 0);
        position = intent.getIntExtra("position", 0);

        TextView nameView = findViewById(R.id.itemName);
        TextView priceView = findViewById(R.id.price);
        TextView descriptionView = findViewById(R.id.description);
        TextView sellerView = findViewById(R.id.seller);

        sellerView.setText("John Doe");
        nameView.setText(listing);
        priceView.setText(Double.toString(price));
        descriptionView.setText(description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //taskView.setText(assignee);
    }
//        seller = root.findViewById(R.id.seller);
//        condition = root.findViewById(R.id.condition);
//        category = root.findViewById(R.id.category);
//        description = root.findViewById(R.id.description);
//        price = root.findViewById(R.id.price);
//        name = root.findViewById(R.id.name);
//        Button contact = root.findViewById(R.id.condition);
//        //Item item;
//        //show sold? or not sold?
//        seller.setText(item.getSeller());
//        condition.setText(item.getCondition());
//        category.setText(item.getCategory());
//        description.setText(item.getDescription());
//        price.setText("$" + item.getPrice());
//        name.setText(item.getName());

//        contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent getUser = new Intent(getActivity(), MyProfile.class);
//                startActivity(getUser);
//            }
//        });
//
//        return root;
//    }

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
