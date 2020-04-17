package com.example.jhump;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static android.app.Activity.RESULT_OK;

public class CreateListings extends Fragment implements View.OnClickListener{

    private Button post;
    private Button cancel;
    private List<Bitmap> pics;
    private ImageButton gallery;
    private Spinner category;
    private Spinner condition;
    private EditText listingName;
    private EditText price;
    private EditText description;
    private String textCon;
    private String textCat;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_create_listings, container, false);
        db = FirebaseFirestore.getInstance();
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        post.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return root;
    }

    public boolean checkAllInput(EditText listingName, EditText price) {
        String listing = listingName.getText().toString();
        String check_price = listingName.getText().toString();
        //gallery idk how to do?
        //Spinner
        return (!listing.isEmpty() && !(check_price.isEmpty()));
        //return true;
    }

    @Override
    public void onClick(View view) {
        final String TAG = "itemList";
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.post:
                //seller is hardcoded for now!
                if (!checkAllInput(listingName, price)) {
                    CharSequence text = "Please complete all required fields.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                    return;
                }
                Item newItem = new Item(listingName.getText().toString(), pics,"John Doe",
                        textCon , textCat, description.getText().toString(),
                        Double.parseDouble(price.getText().toString()), false );
                db.collection("items")
                        .add(newItem)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                NavigationDrawer.aa.add(newItem);
                transaction.replace(R.id.fragment_container, new AllListings());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.cancel:
                transaction.replace(R.id.fragment_container, new AllListings());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }


//    should launch camera later on
//    for launching user gallery to select multiple images
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
                        InputStream is = getActivity().getContentResolver().openInputStream(imageURI);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        pics.add(Bitmap.createScaledBitmap(bitmap, 80, 100, false));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageURI = data.getData();
                try {
                    assert imageURI != null;
                    InputStream is = getActivity().getContentResolver().openInputStream(imageURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    pics.add(Bitmap.createScaledBitmap(bitmap, 80, 100, false));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
