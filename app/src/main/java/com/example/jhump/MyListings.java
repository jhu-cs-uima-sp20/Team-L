package com.example.jhump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MyListings extends Fragment {
    public ArrayList<Item> myItems;
    public ItemAdapter adapter;
    private ListView listingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //SharedPreferences sharedPref = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String defaultName = "John Doe";
        View root = inflater.inflate(R.layout.fragment_my_listings, container, false);
        getActivity().getActionBar().setTitle("My Listings");
        myItems = new ArrayList<>();
        for(Item item: NavigationDrawer.listingItem) {
            if(item.getSeller().compareTo(defaultName) == 0) {
                myItems.add(item);
            }
        }
        listingList = (ListView)root.findViewById(R.id.my_listings_list);
        adapter = new ItemAdapter(getActivity(), R.layout.listing_item_layout, myItems);
        listingList.setAdapter(adapter);
        registerForContextMenu(listingList);
        adapter.notifyDataSetChanged();

        listingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ItemDescription.class);
                Item item = myItems.get(position);
                intent.putExtra("listing", item.getName());
                intent.putExtra("seller", item.getSeller());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("condition", item.getCondition());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("sold", item.isSold());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        return root;
    }
}
