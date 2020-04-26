package com.example.jhump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class MyListings extends Fragment {
    public ArrayList<Item> myItems;
    public MyItemAdapter adapter;
    private ListView listingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //SharedPreferences sharedPref = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String defaultName = "John Doe";
        View root = inflater.inflate(R.layout.fragment_my_listings, container, false);
        //getActivity().getActionBar().setTitle("My Listings");
        myItems = new ArrayList<>();
        for(Item item: NavigationDrawer.listingItem) {
            if(item.getSeller().compareTo(defaultName) == 0) {
                 myItems.add(item);
            }
        }
        listingList = (ListView)root.findViewById(R.id.my_listings_list);
        adapter = new MyItemAdapter(getActivity(), R.layout.my_item_view, myItems);
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
                intent.putExtra("price", item.getPrice());
                intent.putExtra("sold", item.isSold());
                intent.putExtra("sellerID", item.getSellerID());
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_with_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

class MyItemAdapter extends ArrayAdapter<Item> {
    private int resource;

    public MyItemAdapter(Context ctx, int res, ArrayList<Item> items)
    {
        super(ctx, res, items);
        resource = res;
    }

    Item item;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LinearLayout itemView;
        final Item item = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }
        Button button = itemView.findViewById(R.id.edit);
        Switch sold = itemView.findViewById(R.id.soldSwitch);
        TextView listingNameView = itemView.findViewById(R.id.listing_name);
        TextView priceView = itemView.findViewById(R.id.listing_price);
        TextView sellerView = itemView.findViewById(R.id.listing_seller);
        ImageView imageView = itemView.findViewById(R.id.listing_image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(parent.getContext(), EditListings.class);
                edit.putExtra("listing", item.getName());
                edit.putExtra("seller", item.getSeller());
                edit.putExtra("category", item.getCategory());
                edit.putExtra("condition", item.getCondition());
                edit.putExtra("description", item.getDescription());
                edit.putExtra("price", item.getPrice());
                edit.putExtra("sold", item.isSold());
                edit.putExtra("sellerID", item.getSellerID());
                parent.getContext().startActivity(edit);
            }
        });
        listingNameView.setText(item.getName());
        priceView.setText("$" + Double.toString(item.getPrice()) + "0");
        sellerView.setText(item.getSeller());
        //change width/height
        imageView.setImageBitmap(Bitmap.createScaledBitmap(item.getPicture().get(0), 80, 100, false));

        return itemView;
    }
}
