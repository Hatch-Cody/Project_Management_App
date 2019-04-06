package com.example.project_management_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class Profile_page extends AppCompatActivity {
    Dialog myDialog;
    private TextView name1, name2, position, email, phoneNum, bio;
    private Profile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        myDialog = new Dialog(this);


        name1 = (TextView) findViewById(R.id.textView13);
        name2 = (TextView) findViewById(R.id.textView15);
        position = (TextView) findViewById(R.id.textView9);

        email = (TextView) findViewById(R.id.textView12);
        phoneNum = (TextView) findViewById(R.id.textView8);
        bio = (TextView) findViewById(R.id.textView14);

        Gson gson = new Gson();

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String json = intent.getString("json");
            userProfile = gson.fromJson(json, Profile.class);
            String name = (userProfile.getFirstName() + " " + userProfile.getLastName());
            name2.setText(name);
            name1.setText(name);
            position.setText(userProfile.getPosition());
            email.setText(userProfile.getEmail());
            phoneNum.setText(userProfile.getPhoneNumber());
            bio.setText(userProfile.getBio());
        }
    }
}

