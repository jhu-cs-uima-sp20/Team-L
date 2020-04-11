package com.example.jhump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EditProfile extends AppCompatActivity {
    ImageView profilePicture;
    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Edit Profile");
        profilePicture = findViewById(R.id.edit_profile_picture);
        profilePicture.setColorFilter(new PorterDuffColorFilter(Color.argb(180, 255,
                255, 255), PorterDuff.Mode.SRC_OVER));
        save = findViewById(R.id.edit_profile_save);
        cancel = findViewById(R.id.edit_profile_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
