package com.example.jhump;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{
    SharedPreferences userLogin;
    SharedPreferences.Editor user;
    Button signIn;
    Button signUp;
    private Fragment all_listings;
    private Fragment my_listings;
    private Fragment my_profile;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLogin = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        //need a boolean for now to skip signin/signup pages
        //here is also where we'd want to connect to database with user information for signin
        //so populate item and user Item listing after sign in

        System.out.println("\n hello");

        Intent listings = new Intent(MainActivity.this, NavigationDrawer.class);
        startActivity(listings);

        /*
        if (userLogin.getBoolean("logged", false)) {
            System.out.println("\n apple");
            Intent listings = new Intent(MainActivity.this, NavigationDrawer.class);
            startActivity(listings);
            /*this is just for now, we should update the listings when they sign in since they could have accessed
            app from separate device and want the listings on this device.
            This update when database is initiated
        }
        */


        /*
        //TODO fix the error here
        //TODO create xml with signIn/signUp options, which should fix the error here
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(signIn);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });

         */

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.allListings) {
            // Handle the All Listings Action
            transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the nav_host_fragment view with this fragment
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.nav_host_fragment, all_listings);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        } else if (id == R.id.createListings) {

        } else if (id == R.id.myListings) {
            // Handle the My Listings Action
            transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the nav_host_fragment view with this fragment
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.nav_host_fragment, my_listings);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();

        }  else if (id == R.id.signOut) {

        }
        else if (id == R.id.profile) {
            getSupportActionBar().setTitle("My Profile");
            transaction.replace(R.id.fragment_container, new MyProfile());
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
