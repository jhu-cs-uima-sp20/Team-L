package com.example.jhump;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class Filters extends Fragment {
    private Button textbooks;
    private Button iClickers;
    private Button labEquipment;
    private Button misc;
    private Button highToLow;
    private Button lowToHigh;
    private Button mostRecent;
    private Button newCondition;
    private Button good;
    private Button fair;
    private Button used;
    private Button reset;
    private Button apply;
    ArrayList<Item> list;
    ArrayList<Button> buttons;
    private boolean[] lookingForClicked = new boolean[4];
    private boolean[] sortByClicked = new boolean[3];
    private boolean[] conditionClicked = new boolean[4];
    Fragment fragment;

    public Filters() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filters, container, false);

        buttons = new ArrayList<Button>();
        textbooks = view.findViewById(R.id.textbooks_filter);
        buttons.add(textbooks);
        iClickers = view.findViewById(R.id.iclickers_filter);
        buttons.add(iClickers);
        labEquipment = view.findViewById(R.id.lab_equipment_filter);
        buttons.add(labEquipment);
        misc = view.findViewById(R.id.misc_filter);
        buttons.add(misc);
        highToLow = view.findViewById(R.id.high_to_low);
        buttons.add(highToLow);
        lowToHigh = view.findViewById(R.id.low_to_high);
        buttons.add(lowToHigh);
        mostRecent = view.findViewById(R.id.most_recent);
        buttons.add(mostRecent);
        newCondition = view.findViewById(R.id.new_condition);
        buttons.add(newCondition);
        good = view.findViewById(R.id.good_condition);
        buttons.add(good);
        fair = view.findViewById(R.id.fair_condition);
        buttons.add(fair);
        used = view.findViewById(R.id.used_condition);
        buttons.add(used);
        reset = view.findViewById(R.id.reset_filters);
        apply = view.findViewById(R.id.apply_filters);
        sortByClicked[2] = true;
        list = new ArrayList<>();
        fragment = this;


        textbooks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lookingForClicked[0]) {
                    lookingForClicked[0] = false;
                    textbooks.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    lookingForClicked[0] = true;
                    textbooks.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        iClickers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lookingForClicked[1]) {
                    lookingForClicked[1] = false;
                    iClickers.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    lookingForClicked[1] = true;
                    iClickers.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        labEquipment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lookingForClicked[2]) {
                    lookingForClicked[2] = false;
                    labEquipment.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    lookingForClicked[2] = true;
                    labEquipment.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        misc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lookingForClicked[3]) {
                    lookingForClicked[3] = false;
                    misc.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    lookingForClicked[3] = true;
                    misc.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        highToLow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sortByClicked[0]) {
                    sortByClicked[0] = false;
                    highToLow.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    sortByClicked[0] = true;
                    highToLow.setBackgroundResource(R.drawable.button_border_selected);
                    if (sortByClicked[1]) {
                        lowToHigh.setBackgroundResource(R.drawable.button_border_unselected);
                        sortByClicked[1] = false;
                    }
                    if (sortByClicked[2]) {
                        mostRecent.setBackgroundResource(R.drawable.button_border_unselected);
                        sortByClicked[2] = false;
                    }
                }
            }
        });

        lowToHigh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sortByClicked[1]) {
                    sortByClicked[1] = false;
                    lowToHigh.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    sortByClicked[1] = true;
                    lowToHigh.setBackgroundResource(R.drawable.button_border_selected);
                    if (sortByClicked[0]) {
                        highToLow.setBackgroundResource(R.drawable.button_border_unselected);
                        sortByClicked[0] = false;
                    }
                    if (sortByClicked[2]) {
                        mostRecent.setBackgroundResource(R.drawable.button_border_unselected);
                        sortByClicked[2] = false;
                    }
                }
            }
        });

        mostRecent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sortByClicked[2]) {
                    sortByClicked[2] = false;
                    highToLow.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    sortByClicked[2] = true;
                    mostRecent.setBackgroundResource(R.drawable.button_border_selected);
                    if (sortByClicked[0]) {
                        highToLow.setBackgroundResource(R.drawable.button_border_unselected);
                        sortByClicked[0] = false;
                    }
                    if (sortByClicked[1]) {
                        lowToHigh.setBackgroundResource(R.drawable.button_border_unselected);
                        sortByClicked[1] = false;
                    }
                }
            }
        });

        newCondition.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (conditionClicked[0]) {
                    lookingForClicked[0] = false;
                    newCondition.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    conditionClicked[0] = true;
                    newCondition.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (conditionClicked[1]) {
                    lookingForClicked[1] = false;
                    good.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    conditionClicked[1] = true;
                    good.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        fair.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (conditionClicked[2]) {
                    lookingForClicked[2] = false;
                    fair.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    conditionClicked[2] = true;
                    fair.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        used.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (conditionClicked[3]) {
                    lookingForClicked[3] = false;
                    used.setBackgroundResource(R.drawable.button_border_unselected);
                }
                else {
                    conditionClicked[3] = true;
                    used.setBackgroundResource(R.drawable.button_border_selected);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (Button b : buttons) {
                    b.setBackgroundResource(R.drawable.button_border_unselected);
                }
                for (int i = 0; i < 4; i++) {
                    lookingForClicked[i] = false;
                    conditionClicked[i] = false;
                }
                sortByClicked[0] = false;
                sortByClicked[1] = false;
                sortByClicked[2] = true;
                mostRecent.setBackgroundResource(R.drawable.button_border_selected);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (Item i : NavigationDrawer.listingItem) {
                    if (lookingForClicked[0]) {
                        if (i.getCategory().equals("Textbook"))
                            list.add(i);
                    }
                    if (lookingForClicked[1]) {
                        if (i.getCategory().equals("iClicker"))
                            list.add(i);
                    }
                    if (lookingForClicked[2]) {
                        if (i.getCategory().equals("Lab Equipment"))
                            list.add(i);
                    }
                    if (lookingForClicked[3]) {
                        if (i.getCategory().equals("Miscellaneous") || i.getCategory().equals("Misc"))
                            list.add(i);
                    }
                    if (conditionClicked[0]) {
                        if (i.getCondition().equals("New"))
                            list.add(i);
                    }
                    if (conditionClicked[1]) {
                        if (i.getCondition().equals("Good"))
                            list.add(i);
                    }
                    if (conditionClicked[2]) {
                        if (i.getCondition().equals("Fair"))
                            list.add(i);
                    }
                    if (conditionClicked[3]) {
                        if (i.getCondition().equals("Used"))
                            list.add(i);
                    }
                }
                if (sortByClicked[0]) {
                    Collections.sort(list, new SortLowToHigh());
                }
                if (sortByClicked[1]) {
                    Collections.sort(list, new SortHighToLow());
                }
                if (sortByClicked[2]) {
                    Collections.sort(list, new SortMostRecent());
                }
                NavigationDrawer.listingItem.clear();
                NavigationDrawer.listingItem.addAll(list);
                NavigationDrawer.aa.notifyDataSetChanged();
                NavigationDrawer.fromFilters = true;
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AllListings());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}

class SortMostRecent implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return a.getIDInt() - b.getIDInt();
    }
}

class SortHighToLow implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return (int) b.getPrice() - (int) a.getPrice();
    }
}

class SortLowToHigh implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return (int) a.getPrice() - (int) b.getPrice();
    }
}