package com.example.jhump;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int resource;

    public ItemAdapter(Context ctx, int res, ArrayList<Item> items)
    {
        super(ctx, res, items);
        resource = res;
    }

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
        ImageView imageView = itemView.findViewById(R.id.listing_image);

        listingNameView.setText(item.getName());
        priceView.setText("$" + Double.toString(item.getPrice()) + "0");
        sellerView.setText(item.getSeller());
        imageView.setImageBitmap(item.getPicture().get(0));

        return itemView;
    }
}
