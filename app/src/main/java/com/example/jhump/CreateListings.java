package com.example.jhump;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CreateListings extends Fragment {
    private Button newItem;
    private Button cancel;
    private List<Bitmap> pics;
    private Button gallery;
    //whereever displaying, use:
    // ImageView picture;
    //imageview.setImageBitmap(b);
    //do we want subject?
    private Spinner category;
    private Spinner condition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.activity_create_listings, container, false);
        newItem = root.findViewById(R.id.post);
        cancel = root.findViewById(R.id.cancel);
        gallery = root.findViewById(R.id.pic);
        category = root.findViewById(R.id.category);
        condition = root.findViewById(R.id.condition);
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
        ArrayAdapter<String> catAdapt = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, cat);
        ArrayAdapter<String> conAdapt = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, con);
        condition.setAdapter(conAdapt);
        category.setAdapter(catAdapt);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryItem[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryItem[0] = "Textbook";
                //or throw TOAST?
            }
        });

        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conditionItem[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryItem[0] = "Used";
                //or throw TOAST?
            }
        });
        String itemName = ((TextView) root.findViewById(R.id.listing)).getText().toString();
        String description = ((TextView) root.findViewById(R.id.description)).getText().toString();
        double price = Double.parseDouble(((TextView) root.findViewById(R.id.price)).getText().toString());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(new CreateListings());
                trans.commit();
                manager.popBackStack();
                //pop this layout from stack
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //below is to get pics from gallery
                //was previously createlistings.this before class was made into a frag <---for Chioma
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

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //throw toast if nothing is input
                String seller;
                //are we allowing to create a new listing that is already marked as sold?
                //Item addItem = new Item(name, pics, seller, conditionItem, categoryItem, description, price, false);
            }
        });
        return root;
    }

    //should launch camera later on
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
                        InputStream is = getActivity().getContentResolver().openInputStream(imageURI);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        pics.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Uri imageURI = data.getData();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(imageURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    pics.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
