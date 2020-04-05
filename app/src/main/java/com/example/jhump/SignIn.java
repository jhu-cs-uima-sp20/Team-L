package com.example.jhump;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    Button signIn;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //if email not in database, toast
        //if email is in database, call password
        //if password doesnt match, toast
        //EVENTUALLY, forget password
        //delete account from database?
        //back button also needed on this page, not in design doc

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().endsWith("@jhu.edu")
                        || !email.getText().toString().endsWith("@jh.edu")) {
                    Toast.makeText(getApplicationContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (!email.getText().toString() is not in User class) {
                    Toast.makeText(getApplicationContext(), "Sign Up. Email does not exist.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
            }
        });
    }
}
