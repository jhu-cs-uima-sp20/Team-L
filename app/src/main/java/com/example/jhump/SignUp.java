package com.example.jhump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView password;
    TextView confirmPassword;
    TextView number;
    Button signUp;
    SharedPreferences userLogin;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp = findViewById(R.id.signup);

    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!email.getText().toString().endsWith("@jhu.edu")
                    || !email.getText().toString().endsWith("@jh.edu")) {
                Toast.makeText(getApplicationContext(), "Please enter a JHU email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a password.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Passwords entered do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(name.getText().toString(), email.getText().toString(),
                    password.getText().toString(), number.getText().toString(), new ArrayList<Item>());
            //add new user to database

            userLogin = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
            userLogin.edit().putBoolean("logged", true).apply();
            Intent listings = new Intent(SignUp.this, NavigationDrawer.class);
            startActivity(listings);
            //how often should database be updated?
            //EVENTUALLY, should check that jhu email is not already in database
            //should phone number contain a toast?
            //back button also needed on this page, not in design doc
        }
    });
    }

}
