package com.example.jhump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        mdbase = FirebaseDatabase.getInstance();
        dbref = mdbase.getReference();

        email = findViewById(R.id.email);
        password = findViewById(R.id.your_password);
        confirmPassword = findViewById(R.id.check_password);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!email.getText().toString().endsWith("@jhu.edu")
                    && !email.getText().toString().endsWith("@jh.edu")) {
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

            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    String jhed = (email.getText().toString().substring(0,email.getText().toString().indexOf('@')));
                    //Email already exists --> no sign up required

                    if (dataSnapshot.child("users").hasChild(jhed)) {
                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    }
                    //add new user to database
                    else {
                        User newUser = new User(name.getText().toString(), email.getText().toString(),
                                password.getText().toString(), number.getText().toString(), new ArrayList<Item>());
                        String id = email.getText().toString().substring(0, email.getText().toString().indexOf('@'));
                        dbref.child("users").child(id).setValue(newUser);

                        userLogin = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
                        userLogin.edit().putBoolean("logged", true).apply();
                        userLogin.edit().putString("name", newUser.getName()).apply();
                        userLogin.edit().putString("id", id).apply();
                        //for ceatelisting/item description/user profile/contact
                        //To create listing with user's name and to contact user when viewing item

                        //goes directly to all listings, but maybe go into landing page again?
                        Intent listings = new Intent(SignUp.this, NavigationDrawer.class);
                        startActivity(listings);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Error: ","Failed to read value.", error.toException());
                }
            });

        }
    });
    }

}
