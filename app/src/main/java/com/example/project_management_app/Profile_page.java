package com.example.project_management_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Profile_page extends AppCompatActivity {
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        myDialog= new Dialog(this);
    }
}
