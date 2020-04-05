package com.example.jhump;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int resource;

    public ItemAdapter(Context ctx, int res, List<Item> items)
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

        listingNameView.setText(item.getName());
        priceView.setText(Double.toString(item.getPrice()));
        sellerView.setText(item.getSeller());

        return itemView;
    }
}
