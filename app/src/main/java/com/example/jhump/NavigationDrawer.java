package com.example.jhump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Build;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment allListings;
    private Fragment createListings;
    private Fragment myListings;
    private Fragment myProfile;
    private FragmentTransaction transaction;

    public static ItemAdapter aa;
    public static ArrayList<Item> listingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        listingItem = new ArrayList<Item>();
        aa = new ItemAdapter(this, R.layout.listing_item_layout, listingItem);

        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        allListings = new AllListings();
        myListings = new MyListings();
        createListings = new CreateListings();
        myProfile = new MyProfile();

        getSupportActionBar().setTitle("All Listings");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, allListings).commit();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));

        //View header = navigationView.getHeaderView(0);
        //TextView name = header.findViewById(R.id.myName);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.allListings) {
            getSupportActionBar().setTitle("All Listings");
            transaction.replace(R.id.fragment_container, allListings);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.createListings) {
            getSupportActionBar().setTitle("Create Listing");
//            Intent intent = new Intent(this, CreateListings.class);
//            startActivity(intent);
            transaction.replace(R.id.fragment_container, createListings);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.myListings) {
            getSupportActionBar().setTitle("My Listings");
            transaction.replace(R.id.fragment_container, myListings);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.profile) {
            getSupportActionBar().setTitle("My Profile");
            transaction.replace(R.id.fragment_container, myProfile);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.signOut) {
            SharedPreferences userInfo = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
            userInfo.edit().putBoolean("logged", false).apply();
            //clear all saved info up to this point

            Intent backToMain = new Intent(NavigationDrawer.this, MainActivity.class);
            startActivity(backToMain);
            finishAffinity();
            //remove back button
        }
      /*  else if (id == R.id.profile) {
            getSupportActionBar().setTitle("My Profile");
            transaction.replace(R.id.fragment_container, new MyProfile());
            transaction.addToBackStack(null);
            transaction.commit();
        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
