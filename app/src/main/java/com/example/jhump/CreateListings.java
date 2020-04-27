package com.example.jhump;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
//import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class CreateListings extends Fragment implements View.OnClickListener{

    private Button post;
    private Button cancel;
    private ImageButton gallery;
    private Spinner category;
    private Spinner condition;
    private EditText listingName;
    private EditText price;
    private EditText description;
    private String textCon;
    private String textCat;
    FirebaseDatabase db;
    private DatabaseReference dbref;
    SharedPreferences userLogin;
    public Uri imguri;
    //private static final int KITKAT_VALUE = 1002;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_create_listings, container, false);
        userLogin = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        post = root.findViewById(R.id.post);
        cancel = root.findViewById(R.id.cancel);
        listingName = root.findViewById(R.id.listing);
        gallery = root.findViewById(R.id.pic);
        condition = root.findViewById(R.id.condition);
        category = root.findViewById(R.id.category);
        description = root.findViewById(R.id.description);
        price = root.findViewById(R.id.price);
        condition = root.findViewById(R.id.condition);
        category = root.findViewById(R.id.category);
        ArrayAdapter<CharSequence> catAdapt = ArrayAdapter.createFromResource(getContext(), R.array.cat_spinner ,android.R.layout.simple_spinner_item);
        catAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(catAdapt);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                textCat = parent.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Select Category", Toast.LENGTH_LONG).show();
            }
        });
        ArrayAdapter<CharSequence> conAdapt = ArrayAdapter.createFromResource(getContext(), R.array.con_spinner ,android.R.layout.simple_spinner_item);
        conAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(conAdapt);
        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                textCon = parent.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Select Condition", Toast.LENGTH_LONG).show();
            }
        });

        gallery = root.findViewById(R.id.pic);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
//                Intent intent;
//                if (Build.VERSION.SDK_INT < 19) {
//                    intent = new Intent();
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, KITKAT_VALUE);
//                } else {
//                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, KITKAT_VALUE);
//                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 1);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);
            }
        });
        post.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data != null && data.getData() !=null){
            imguri = data.getData();
        }
    }



    public boolean checkAllInput() {
        String listing = listingName.getText().toString();
        String check_price = listingName.getText().toString();
        return (!listing.isEmpty() && !(check_price.isEmpty())) && !textCat.equals("N/A") && !textCon.equals("N/A"); //&& pics.size() > 0;
//        String listing = listingName.getText().toString();
//        boolean isDouble = true;
//        try {
//            Double.parseDouble(price.getText().toString());
//        } catch (Exception e) {
//            isDouble = false;
//        }
//        return (!listing.isEmpty() && (!textCat.equals("N/A"))
//                && !textCon.equals("N/A")  0 && isDouble);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.post:
                if (!checkAllInput()) {
                    CharSequence text = "Please complete all required fields.";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance();
                dbref = db.getReference();
                String name = userLogin.getString("name", "John Doe");
                String sellerID = userLogin.getString("id", "John Doe");
                //ArrayList<String> linksOfPics = new ArrayList<>();
                //linksOfPics.add(imguri.toString());
                String links = imguri.toString();
                //linksOfPics.add(getImageUri(getContext(), pics.get(0)).toString());
                Item newItem = new Item(listingName.getText().toString(), links, name,
                         sellerID, textCon, textCat, description.getText().toString(),
                        Double.parseDouble(price.getText().toString()), false );
                dbref.child("listings").child(newItem.getId()).setValue(newItem);
                //add listing to user arraylist of items
                NavigationDrawer.aa.add(newItem);
                transaction.remove(new CreateListings());
                transaction.commit();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.cancel:
                transaction.remove(new CreateListings());
                transaction.commit();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }


    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    @Override
//     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK && requestCode == 1 && data != null && data.getData() != null) {
//            pics = new ArrayList<>();
//            ClipData clipData = data.getClipData();
//            if (clipData != null) {
//                for (int i = 0; i < clipData.getItemCount(); i++) {
//                    Uri imageURI = clipData.getItemAt(i).getUri();
//                    try {
//                        InputStream is = getActivity().getContentResolver().openInputStream(imageURI);
//                        Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        pics.add(Bitmap.createScaledBitmap(bitmap, 80, 100, false));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                Uri imageURI = data.getData();
//                try {
//
//                    assert imageURI != null;
//                    InputStream is = getActivity().getContentResolver().openInputStream(imageURI);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//
//                    pics.add(Bitmap.createScaledBitmap(bitmap, 80, 100, false));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//          }
//    }
}
