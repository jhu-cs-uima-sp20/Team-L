package com.example.jhump;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {
    protected ItemAdapter itemAdapter;
    protected ArrayList<Item> items;
    ListView listView;
    List<Bitmap> imageList;

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        //OnClickListener for Facebook icon, which opens URL to user's Facebook page.
        ImageView facebook = (ImageView) view.findViewById(R.id.facebook_my_profile);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/alison.lee.9440"; //Hardcoded Facebook link, for now.
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ImageView editButton = view.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });

        items = new ArrayList<>();
        listView = view.findViewById(R.id.my_profile_listings);
        itemAdapter = new ItemAdapter(getActivity(), R.layout.listing_item_layout, items);
        listView.setAdapter(itemAdapter);
        registerForContextMenu(listView);

        //hard coded listing for speed demo.
        Bitmap itemPic = BitmapFactory.decodeResource(getResources(), R.drawable.discrete_math_cover);
        imageList = new ArrayList<>();
        imageList.add(itemPic);
        Item newItem = new Item("Discrete Mathematics", imageList, "Sold by you", "Good",
                "Textbooks", "Calculus 1 textbook.", 20.0, false);
        itemAdapter.add(newItem);
        itemAdapter.notifyDataSetChanged();

        /*listView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemDetail.class);
                startActivity(intent);
            }
        });*/

        return view;
    }
}
