package com.example.jhump;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemView extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);
        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
//        String dead = intent.getStringExtra("deadline");
//        String a = intent.getStringExtra("assign");
//
//        TextView nameView = findViewById(R.id.textview7);
//        TextView deadView = findViewById(R.id.textview8);
//        TextView assignView = findViewById(R.id.textview9);
//
//        nameView.setText(name);
//        deadView.setText(dead);
//        assignView.setText(a);
    }
}
