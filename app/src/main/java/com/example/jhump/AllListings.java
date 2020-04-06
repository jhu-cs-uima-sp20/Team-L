package com.example.jhump;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllListings factory method to
 * create an instance of this fragment.
 */
public class AllListings extends Fragment {

    private ListView listingList;
    private Context context;

    public AllListings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //NavigationDrawer navigationDrawer = (NavigationDrawer) getActivity();
        View view =  inflater.inflate(R.layout.fragment_all_listings, container, false);

        listingList = (ListView)view.findViewById(R.id.all_listings_list);
        NavigationDrawer.aa = new ItemAdapter(getActivity(),R.layout.listing_item_layout, NavigationDrawer.listingItem);
        listingList.setAdapter(NavigationDrawer.aa);

        registerForContextMenu(listingList);

        double price = 50;

        Item test = new Item("Listing Name", null, "Seller Name", "new", "Textbook", "", price, false);
        NavigationDrawer.listingItem.add(test);

        NavigationDrawer.aa.notifyDataSetChanged();

        // program a short click on the list item
        listingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Selected #" + id, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
