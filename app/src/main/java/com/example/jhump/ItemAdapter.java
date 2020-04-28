package com.example.jhump;

import android.app.DownloadManager;
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

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
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
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference Ref = storageRef.child(item.getId());
        //final String link = item.getPicture().toString();
//        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Successfully downloaded data to local file
//                // ...
//                String url = uri.toString();
//                //downloadFiles(getContext(), link, ".jpeg", DIRECTORY_DOWNLOADS, url);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                // ...
//            }
//        });
//        if (item.getPicture() != null) {
//            Uri link = Uri.parse(item.getPicture());
//            imageView.setImageURI(link);
//        }
        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
        ;
        if (item.isSold()) {
            soldView.setImageResource(R.drawable.sold);
            soldView.bringToFront();
        }
        else {
            soldView.setImageResource(R.drawable.blank);
        }
        return itemView;
    }

//    public void downloadFiles(Context context, String filename,
//                              String fileExtension, String destinationDirectory, String url) {
//        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(url);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalFilesDir(context, destinationDirectory, filename + fileExtension);
//        downloadManager.enqueue(request);
//    }

}
