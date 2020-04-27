package com.example.jhump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.net.URI;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ItemAdapter extends ArrayAdapter<Item> {

    private int resource;

    public ItemAdapter(Context ctx, int res, ArrayList<Item> items)
    {
        super(ctx, res, items);
        resource = res;
    }

    Item item;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        Item item = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView listingNameView = itemView.findViewById(R.id.listing_name);
        TextView priceView = itemView.findViewById(R.id.listing_price);
        TextView sellerView = itemView.findViewById(R.id.listing_seller);
        TextView conditionView = itemView.findViewById(R.id.listing_condition);
        ImageView imageView = itemView.findViewById(R.id.listing_image);
        ImageView soldView = itemView.findViewById(R.id.sold_image);

        listingNameView.setText(item.getName());
        priceView.setText("$" + Double.toString(item.getPrice()) + "0");
        sellerView.setText("Sold by: " + item.getSeller());
        conditionView.setText("Condition: " + item.getCondition());
//        Uri link = Uri.parse(item.getPicture().get(0));
        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
//        imageView.setImageURI(link);
        if (item.isSold()) {
            soldView.setImageResource(R.drawable.sold);
            soldView.bringToFront();
        }
        //change width/height
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(item.getPicture().get(0), 80, 100, false));

        return itemView;
    }

}
