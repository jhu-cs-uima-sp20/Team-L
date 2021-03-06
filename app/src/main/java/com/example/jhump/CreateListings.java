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
import android.os.Messenger;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    private static final int KITKAT_VALUE = 1002;
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
    private StorageReference Ref;
    SharedPreferences userLogin;
    public Uri imguri;
    public String links = "";
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, 1);

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getContext().getContentResolver().takePersistableUriPermission(imguri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
    }

    // only listing name, price, condition and category are required
    public boolean checkAllInput() {
        String listing = listingName.getText().toString();
        String check_price = price.getText().toString();

        return (!listing.isEmpty() && !(check_price.isEmpty()))
                && !textCat.equals("N/A") && !textCon.equals("N/A");
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        userLogin = this.getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        String id = userLogin.getString("id", "John Doe");
        switch (view.getId()) {
            case R.id.post:
                links = imguri.toString();
                if (!checkAllInput()) {
                    CharSequence text = "Please complete all required fields.";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    return;
                }
                db = FirebaseDatabase.getInstance();
                dbref = db.getReference();
                String name = userLogin.getString("name", "John Doe");
                String sellerID = userLogin.getString("id", "John Doe");
                links = imguri.toString();
                //links = "blank";
                Item newItem = new Item(listingName.getText().toString(), links, name,
                        sellerID, textCon, textCat, description.getText().toString(),
                        Double.parseDouble(price.getText().toString()), false );
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                if (imguri != null) {
                    Ref = storageRef.child(newItem.getId());
                    Ref.putFile(imguri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    links = url;
                                    //Log.d("download link",url);
                                }
                            });
                        }
                    });
                    newItem.setPicture(links);

//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    // Get a URL to the uploaded content
//                                    //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
//                                    //Toast.makeText(getContext(), "uploaded!", Toast.LENGTH_LONG).show();
//                                    Upload upload = new Upload(newItem.getId().toString(), taskSnapshot.getUploadSessionUri().toString());
//                                    String uploadid = dbref.push().getKey();
//                                    dbref.child(uploadid).setValue(upload);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle unsuccessful uploads
//                                    // ...
//                                }
//                            });
                }

                dbref.child("listings").child(newItem.getId()).setValue(newItem);
                dbref.child("users").child(id).child("listings").child(newItem.getId()).setValue(newItem);
                //add listing to user arraylist of items
                NavigationDrawer.aa.add(newItem);
                //transaction.replace(R.id.createListings, new AllListings());
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


    private String getExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
//
//    private Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }

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
