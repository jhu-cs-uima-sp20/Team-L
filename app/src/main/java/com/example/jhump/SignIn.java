package com.example.jhump;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import java.util.Objects;

public class SignIn extends AppCompatActivity {
    Button signIn;
    TextView email;
    TextView password;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    SharedPreferences userLogin;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //forget password?
        //delete account from database?
        //should maybe include a back button in action bar?
        mdbase = FirebaseDatabase.getInstance();
        dbref = mdbase.getReference();

        signIn = (Button) findViewById(R.id.signin);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.your_password);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String of email up to but not including @

                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        String jhed = (email.getText().toString().substring(0,email.getText().toString().indexOf('@')));
                        String str_password = password.getText().toString();
                        if (dataSnapshot.child("users").hasChild(jhed)) {
                            String real_pass = dataSnapshot.child("users").child(jhed).child("password").getValue(String.class);
                            if (real_pass != null && real_pass.equals(str_password)) {
                                //userLogin = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
                                //userLogin.edit().putBoolean("logged", true).apply();
                                String userName = dataSnapshot.child("users").child(jhed).child("name").getValue(String.class);
                                String id = dataSnapshot.child("users").child(jhed).getValue(String.class);
                                //userLogin.edit().putString("name", userName).apply(); <-for item description chioma
                                //userLogin.edit().putString("id", id).apply(); <-for item description/user profile
                                Intent intent = new Intent(SignIn.this, NavigationDrawer.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Incorrect username/password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
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
