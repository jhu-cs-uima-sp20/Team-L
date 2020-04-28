package com.example.jhump;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {
    private ImageView profilePicture;
    private ImageView edit;
    private Button save;
    private Button cancel;
    private TextView email;
    private EditText phoneNumber;
    private List<Bitmap> pics;
    private static Context context;

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
        edit = findViewById(R.id.change_profile_picture);
        context = getApplicationContext();

        Intent intent = getIntent();
        email = findViewById(R.id.user_email);
        TextView userEmail = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNum_editText);
        TextView name = findViewById(R.id.edit_profile_name);
        email.setText(intent.getStringExtra("email"));
        phoneNumber.setText(intent.getStringExtra("number"));
        name.setText(intent.getStringExtra("name"));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emailChange = findViewById(R.id.email);
                emailChange.setText(email.getText());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1) {
            pics = new ArrayList<>();
            assert data != null;
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageURI = clipData.getItemAt(i).getUri();
                    try {
                        InputStream is = this.getContentResolver().openInputStream(imageURI);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        pics.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageURI = data.getData();
                try {
                    assert imageURI != null;
                    InputStream is = this.getContentResolver().openInputStream(imageURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    pics.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
