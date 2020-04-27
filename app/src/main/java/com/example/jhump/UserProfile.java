package com.example.jhump;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        dbref = db.getReference("users");
        String seller = getIntent().getStringExtra("sellerID");
       /* final User[] contact = new User[1];
        dbref.child("sellerID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               contact[0] = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("User Class", "Failed to read value.", databaseError.toException());
            }
        });

        TextView email = findViewById(R.id.email);
        email.setText(contact[0].getEmail());
        TextView name = findViewById(R.id.name);
        name.setText(contact[0].getName());
        TextView number = findViewById(R.id.number);
        number.setText(contact[0].getName());
        items = new ArrayList<>();
        items.addAll(contact[0].getListing());
        listView = findViewById(R.id.my_profile_listings);
        itemAdapter = new ItemAdapter(getApplicationContext(), R.layout.listing_item_layout, items);
        listView.setAdapter(itemAdapter);
        registerForContextMenu(listView);

        //pull user profile picture
*/
        findViewById(R.id.facebook_user_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/alison.lee.9440"; //Hardcoded Facebook link, for now.
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

}