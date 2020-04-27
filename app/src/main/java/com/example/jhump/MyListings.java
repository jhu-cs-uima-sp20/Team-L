package com.example.jhump;

import android.app.Activity;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyListings extends Fragment {
    public ArrayList<Item> myItems;
    public MyItemAdapter adapter;
    private FirebaseDatabase db;
    private DatabaseReference dbref;
    private ListView listingList;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    SharedPreferences userLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userLogin = this.getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        String username = userLogin.getString("name", "John Doe");

        View root = inflater.inflate(R.layout.fragment_my_listings, container, false);


        myItems = new ArrayList<>();
        for(Item item: NavigationDrawer.listingItem) {
            if(item.getSeller().compareTo(username) == 0) {
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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference().child("listings");
        userLogin = this.getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        final String username = userLogin.getString("name", "John Doe");

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    final String entry = newText;

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<Item> newList = new ArrayList<Item>();

                            // If nothing put in search, reset to show all listings
                            if (entry.isEmpty()) {
                                myItems.clear();
                                for(DataSnapshot pair: dataSnapshot.getChildren()) {
                                    if (pair.hasChild("seller")) {
                                        if (pair.child("seller").getValue() != null && pair.child("seller").getValue().equals(username)) {
                                            newList.add(pair.getValue(Item.class));
                                        }
                                    }

                                }
                                myItems.addAll(newList);
                                adapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("Failed to read value.", databaseError.toException());
                        }
                    });

                    return true;
                }

                //start searching after user submits their search key word(s)
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    final String entry = query;

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            myItems.clear();
                            ArrayList<Item> newList = new ArrayList<Item>();

                            String[] tokens = entry.split(" ");

                            for(DataSnapshot pair: dataSnapshot.getChildren()) {
                                //NavigationDrawer.listingItem.add(pair.getValue(Item.class));
                                for (DataSnapshot pair2: pair.getChildren()) {
                                    for (String token: tokens) {
                                        if (pair2.getValue() != null && pair2.getValue().equals(token)) {
                                            newList.add(pair.getValue(Item.class));
                                        }
                                    }
                                }
                            }

                            myItems.addAll(newList);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("Failed to read value.", databaseError.toException());
                        }
                    });
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
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
        if (item.getPicture() != null) {
            Uri link = Uri.parse(item.getPicture());
            imageView.setImageURI(link);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getContext(), EditListings.class);
                edit.putExtra("listing", item.getName());
                edit.putExtra("seller", item.getSeller());
                edit.putExtra("category", item.getCategory());
                edit.putExtra("condition", item.getCondition());
                edit.putExtra("description", item.getDescription());
                edit.putExtra("price", item.getPrice());
                edit.putExtra("sold", item.isSold());
                edit.putExtra("sellerID", item.getSellerID());
                edit.putExtra("ID", item.getId());
                edit.putExtra("picture", item.getPicture());
                getContext().startActivity(edit);
            }
        });
        listingNameView.setText(item.getName());
        priceView.setText("$" + Double.toString(item.getPrice()) + "0");
        sellerView.setText(item.getSeller());

        //change width/height
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(item.getPicture().get(0), 80, 100, false));

        return itemView;
    }
}
