package com.example.jhump;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserProfile extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference dbref;
    protected ItemAdapter itemAdapter;
    protected ArrayList<Item> items;
    ListView listView;
    ArrayList<String> imageList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Information");
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference().child("users");
        final String seller = getIntent().getStringExtra("sellerID");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot pair : dataSnapshot.getChildren()) {
                    String email = pair.child("email").getValue(String.class);
                    String jhed = email.substring(0, email.indexOf('@'));
                    if (jhed.equals(seller)) {
                        String name = pair.child("name").getValue(String.class);
                        ArrayList<Item> items = new ArrayList<>();
                        for (DataSnapshot listing: pair.child("listings").getChildren()) {
                            items.add(listing.getValue(Item.class));
                        }

                        TextView email_view = findViewById(R.id.email);
                        email_view.setText(email);
                        TextView name_view = findViewById(R.id.my_profile_name);
                        name_view.setText(name);
                        listView = findViewById(R.id.my_profile_listings);
                        itemAdapter = new ItemAdapter(getApplicationContext(), R.layout.listing_item_layout, items);
                        listView.setAdapter(itemAdapter);
                        registerForContextMenu(listView);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });

        //pull user profile picture
        findViewById(R.id.facebook_my_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/"; //Hardcoded Facebook link, for now.
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
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

