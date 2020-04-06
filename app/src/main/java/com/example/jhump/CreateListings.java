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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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
    private ImageButton gallery;
    //wherever displaying, use:
    // ImageView picture;
    //imageview.setImageBitmap(b);
    //do we want subject?
    private Spinner category;
    private Spinner condition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listings);
        newItem = findViewById(R.id.post);
        cancel = findViewById(R.id.cancel);
        gallery = findViewById(R.id.pic);
        category = findViewById(R.id.category);
        condition = findViewById(R.id.condition);
        final String[] categoryItem = new String[1];
        final String[] conditionItem = new String[1];
        ArrayList<String> cat = new ArrayList<>();
        cat.add("Textbook");
        cat.add("I-Clicker");
        cat.add("Lab Equipment");
        ArrayList<String> con = new ArrayList<>();
        con.add("Gently Used");
        con.add("New");
        con.add("Used");
        con.add("Damaged");
        ArrayAdapter<String> catAdapt = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cat);
        ArrayAdapter<String> conAdapt = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, con);
        //condition.setAdapter(conAdapt);
        //category.setAdapter(catAdapt);
//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                categoryItem[0] = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                categoryItem[0] = "Textbook";
//                //or throw TOAST?
//            }
//        });
//
//        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                conditionItem[0] = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                categoryItem[0] = "Used";
//                //or throw TOAST?
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //below is to get pics from gallery
//                //was previously createlistings.this before class was made into a frag <---for Chioma
//                Context context = getApplicationContext();
//                if (ActivityCompat.checkSelfPermission(context,
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(CreateListings.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
//                    return;
//                }
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);
//            }
//        });

//        newItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //throw toast if nothing is input
//                String seller = "default";
//                String itemName = ((TextView) findViewById(R.id.listing)).getText().toString();
//                String description = ((TextView) findViewById(R.id.description)).getText().toString();
//                double price = Double.parseDouble(((TextView) findViewById(R.id.price)).getText().toString());
//                Item addItem = new Item(itemName, pics, seller, conditionItem[1], categoryItem[1], description, price, false);
//            }
//        });
    }

    //should launch camera later on
    //for launching user gallery to select multiple images
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
