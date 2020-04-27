package com.example.jhump;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {
    protected ItemAdapter itemAdapter;
    protected ArrayList<Item> items;
    ListView listView;
    ArrayList<Bitmap> imageList;
    SharedPreferences userLogin;
    private ListView listingList;

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        userLogin = this.getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        String name = userLogin.getString("name", "John Doe");
        String email = userLogin.getString("id", "JohnDoe") + "@jhu.edu";

        listingList = (ListView)view.findViewById(R.id.my_profile_listings);

        items = new ArrayList<>();
        for(Item item: NavigationDrawer.listingItem) {
            if(item.getSeller().compareTo(name) == 0) {
                items.add(item);
            }
        }
        System.out.println(items.toString());
        itemAdapter = new ItemAdapter(getActivity(), R.layout.listing_item_layout, items);
        listingList.setAdapter(itemAdapter);
        registerForContextMenu(listingList);
        itemAdapter.notifyDataSetChanged();


        TextView name_text = (TextView) view.findViewById(R.id.my_profile_name);
        TextView email_text = (TextView) view.findViewById(R.id.email);

        name_text.setText(name);
        email_text.setText(email);

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
                TextView editButton = view.findViewById(R.id.email);
                intent.putExtra("email", editButton.getText());
                intent.putExtra("phone number", "");
                startActivity(intent);
            }
        });

        Bitmap itemPic = BitmapFactory.decodeResource(getResources(), R.drawable.discrete_math_cover);
        imageList = new ArrayList<>();
        imageList.add(itemPic);

        listingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                if (pos >= 0) {
                    Intent intent = new Intent(getActivity(), ItemDescription.class);
                    intent.putExtra("item seller", items.get(pos).getSeller());
                    intent.putExtra("item name", items.get(pos).getName());
                    intent.putExtra("condition", items.get(pos).getCondition());
                    intent.putExtra("category", items.get(pos).getCategory());
                    intent.putExtra("price", items.get(pos).getPrice());
                    intent.putExtra("description", items.get(pos).getDescription());
                    intent.putExtra("subject", "mathematics");
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
