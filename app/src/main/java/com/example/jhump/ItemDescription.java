package com.example.jhump;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;



public class ItemDescription extends Fragment {



    private ViewPager view;
    private int dotCount;
    private ImageView[] dots;
    private LinearLayout slideDots;
    private TextView seller;
    private TextView condition;
    private TextView category;
    private TextView description;
    private TextView price;
    private TextView name;

    public ItemDescription() {
        // Required empty public constructor
    }

    /** TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static ItemDescription newInstance(String param1, String param2) {
        ItemDescription fragment = new ItemDescription();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
**/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_item_description, container, false);
        view = (ViewPager) root.findViewById(R.id.viewPager);
        slideDots = root.findViewById(R.id.SliderDots);
        ViewPageAdapter vpa = new ViewPageAdapter(getContext());
        view.setAdapter(vpa);
        dotCount = vpa.getCount();
        dots = new ImageView[dotCount];
        for(int i = 0; i < dotCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            slideDots.addView(dots[i], params);
            //getApplicationcontext
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
        view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        seller = root.findViewById(R.id.seller);
        condition = root.findViewById(R.id.condition);
        category = root.findViewById(R.id.category);
        description = root.findViewById(R.id.description);
        price = root.findViewById(R.id.price);
        name = root.findViewById(R.id.name);
        Button contact = root.findViewById(R.id.condition);
        /*Item item;
        //show sold? or not sold?
        seller.setText(item.getSeller());
        condition.setText(item.getCondition());
        category.setText(item.getCategory());
        description.setText(item.getDescription());
        price.setText("$" + item.getPrice());
        name.setText(item.getName());
*/
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
