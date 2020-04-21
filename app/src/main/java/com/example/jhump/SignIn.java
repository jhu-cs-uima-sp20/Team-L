package com.example.jhump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignIn extends AppCompatActivity {
    Button signIn;
    TextView email;
    TextView password;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //if email not in database, toast
        //if email is in database, call password
        //if password doesn't match, toast
        //EVENTUALLY, forget password
        //delete account from database?
        //back button also needed on this page, not in design doc

        mdbase = FirebaseDatabase.getInstance();
        dbref = mdbase.getReference();

        signIn = (Button) findViewById(R.id.signin);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.your_password);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (!email.getText().toString().endsWith("@jhu.edu")
                        || !email.getText().toString().endsWith("@jh.edu")) {
                    Toast.makeText(getApplicationContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                 */
                /*if (!email.getText().toString() is not in User class) {
                    Toast.makeText(getApplicationContext(), "Sign Up. Email does not exist.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
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
