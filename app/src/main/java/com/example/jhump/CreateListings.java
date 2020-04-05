package com.example.jhump;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateListings extends AppCompatActivity {
    private Button newItem;
    private Button cancel;
    private List<Bitmap> pics;
    //wherever displaying, use:
    // ImageView picture;
    //imageview.setImageBitmap(b);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final Context context = getApplicationContext();
        newItem = findViewById(R.id.post);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //below is to get pics from gallery
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateListings.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                String listing_name = findViewById(R.id.listing).toString();
                String seller = "default";
                String condition = "default";
                String category = "default";
                String description = findViewById(R.id.description).toString();
                double price = Double.parseDouble(findViewById(R.id.price).toString());
                Item addItem = new Item(listing_name , pics, seller, condition, category, description, price, false);

            }
        });
    }

    //for launching user gallery to select multiple images
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1) {
            pics = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageURI = clipData.getItemAt(i).getUri();
                    try {
                        InputStream is = getContentResolver().openInputStream(imageURI);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        pics.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageURI = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(imageURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    pics.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
