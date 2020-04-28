package com.example.jhump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyItemAdapter extends ArrayAdapter<Item> {
    private int resource;
    private FirebaseDatabase db;
    private DatabaseReference dbref;
    private Activity context;

    public MyItemAdapter(Context ctx, int res, ArrayList<Item> items)
    {
        super(ctx, res, items);
        resource = res;
        context = (Activity) ctx;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LinearLayout itemView;
        final Item item = getItem(position);
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference();

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }
        Button button = itemView.findViewById(R.id.edit_listing_button);
        final Switch soldView = itemView.findViewById(R.id.soldSwitch);
        final TextView listingNameView = itemView.findViewById(R.id.listing_name);
        TextView priceView = itemView.findViewById(R.id.listing_price);
        TextView sellerView = itemView.findViewById(R.id.listing_seller);
        ImageView imageView = itemView.findViewById(R.id.listing_image);
        if (item.getPicture() != null) {
            Uri link = Uri.parse(item.getPicture());
            imageView.setImageURI(link);
        }

        final String id = item.getId();
        dbref.child("listings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean sold = item.isSold();
                if (listingNameView.getText().toString().equals(item.getName()) && sold) {
                    soldView.setChecked(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });


        soldView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Item temp = getItem(position);
                    if (temp != null) {
                        String id = temp.getId();
                        dbref.child("listings").child(id).child("sold").setValue(true);
                        dbref.child("users").child(item.getSellerID()).child("listings").child(id).child("sold").setValue(true);
                    }

                } else {
                    Item temp = getItem(position);
                    if (temp != null) {
                        String id = temp.getId();
                        dbref.child("listings").child(id).child("sold").setValue(false);
                        dbref.child("users").child(item.getSellerID()).child("listings").child(id).child("sold").setValue(false);
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(context, EditListings.class);
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
                context.startActivity(edit);
            }
        });

        listingNameView.setText(item.getName());
        priceView.setText("$" + Double.toString(item.getPrice()) + "0");
        sellerView.setText(item.getSeller());

        //change width/height
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(item.getPicture().get(0), 80, 100, false));

        return itemView;
    }

    public String[] getAttributes(int position) {
        Item item = getItem(position);
        String[] s = new String[10];
        s[0] = item.getName();
        s[1] = item.getSeller();
        s[2] = item.getCategory();
        s[3] = item.getCondition();
        s[4] = item.getDescription();
        s[5] = Double.toString(item.getPrice());
        s[6] = Boolean.toString(item.isSold());
        s[7] = item.getSellerID();
        s[8] = item.getId();
        s[9] = item.getPicture();
        return s;
    }
}

