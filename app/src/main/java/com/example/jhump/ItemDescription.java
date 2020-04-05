package com.example.jhump;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDescription extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView seller;
    private TextView condition;
    private TextView category;
    private TextView description;
    private TextView price;
    private TextView name;

    public ItemDescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemDescription.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemDescription newInstance(String param1, String param2) {
        ItemDescription fragment = new ItemDescription();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_item_description, container, false);
        seller = root.findViewById(R.id.seller);
        condition = root.findViewById(R.id.condition);
        category = root.findViewById(R.id.category);
        description = root.findViewById(R.id.description);
        price = root.findViewById(R.id.price);
        name = root.findViewById(R.id.name);
        Button contact = root.findViewById(R.id.condition);
        Item item;
        //show sold? or not sold?
        seller.setText(item.getSeller());
        condition.setText(item.getCondition());
        category.setText(item.getCategory());
        description.setText(item.getDescription());
        price.setText("$" + item.getPrice());
        name.setText(item.getName());

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getUser = new Intent(getActivity(), MyProfile.class);
                startActivity(getUser);
            }
        });

        return root;
    }
}
