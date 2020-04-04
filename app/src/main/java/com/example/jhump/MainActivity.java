package com.example.jhump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SharedPreferences userLogin;
    SharedPreferences.Editor user;
    Button signIn;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLogin = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        //need a boolean for now to skip signin/signup pages
        //here is also where we'd want to connect to database with user information for signin
        //so populate item and user Item listing after sign in

        if (userLogin.getBoolean("logged", false)) {
            Intent listings = new Intent(MainActivity.this, NavigationDrawer.class);
            startActivity(listings);
            /*this is just for now, we should update the listings when they sign in since they could have accessed
            app from separate device and want the listings on this device.
            This update when database is initiated*/

        }
        //TODO fix the error here
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
    }

}
