package com.example.jhump;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllListings factory method to
 * create an instance of this fragment.
 */
public class AllListings extends Fragment {

    private ListView listingList;
    private Context context;
    private FirebaseFirestore db;
    public AllListings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String TAG = "itemList";
        View view =  inflater.inflate(R.layout.fragment_all_listings, container, false);
        //this.setTitle("All Listings");
        db = FirebaseFirestore.getInstance();
        db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

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
                Item item = (Item) parent.getItemAtPosition(position);
                //Item item = NavigationDrawer.listingItem.get(position);
                intent.putExtra("listing", item.getName());
                intent.putExtra("seller", item.getSeller());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("condition", item.getCondition());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("price", item.getPrice());
                intent.putExtra("sold", item.isSold());

                /*ArrayList<String> pics = new ArrayList<>();
                for (int i = 0; i < item.getPicture().size(); i++) {
                    pics.add(saveToInternalStorage(item.getPicture().get(i), i));
                }

                intent.putStringArrayListExtra("picLocation", pics);*/
                String filename = "bitmap.jpg";
                FileOutputStream stream = null;
                try {
                    stream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                item.getPicture().get(0).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                item.getPicture().get(0).recycle();
                intent.putExtra("pics", filename /*bStream.toByteArray()*/);

                startActivity(intent);
            }
        });
        return view;
    }

    private String saveToInternalStorage(Bitmap picture, int i) {
        ContextWrapper cw = new ContextWrapper(getActivity());
        File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(dir, "image" + i + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            picture.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (Exception e){
                    e.printStackTrace();
            }
        }
        return dir.getAbsolutePath();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
