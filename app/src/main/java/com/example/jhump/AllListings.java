package com.example.jhump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


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
        //this.setTitle("All Listings");
        listingList = (ListView)view.findViewById(R.id.all_listings_list);
        NavigationDrawer.aa = new ItemAdapter(getActivity(),R.layout.listing_item_layout, NavigationDrawer.listingItem);
        listingList.setAdapter(NavigationDrawer.aa);
        registerForContextMenu(listingList);

        //double price = 50;
        //Item test = new Item("Listing Name", null, "Seller Name", "new", "Textbook", "", price, false);
        //NavigationDrawer.listingItem.add(test);
        NavigationDrawer.aa.notifyDataSetChanged();

        // program a short click on the list item
        listingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ItemDescription.class);
              //  Item item = (Item) parent.getItemAtPosition(position);
                //Item item = NavigationDrawer.listingItem.get(position);
               /* intent.putExtra("listing", item.getName());
                intent.putExtra("seller", item.getSeller());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("condition", item.getCondition());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("price", item.getPrice());
                intent.putExtra("sold", item.isSold());
                intent.putExtra("position", position);

                ArrayList<Bitmap> b = (ArrayList<Bitmap>) item.getPicture();
                //intent.putExtra("item", item);
*/

                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
