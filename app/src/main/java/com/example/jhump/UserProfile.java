package com.example.jhump;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class UserProfile extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);

        //OnClickListener for Facebook icon, which opens URL to user's Facebook page.
        view.findViewById(R.id.facebook_user_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/alison.lee.9440"; //Hardcoded Facebook link, for now.
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return view;
    }

*/
}
