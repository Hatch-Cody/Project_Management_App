package com.example.project_management_app;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile_page extends AppCompatActivity {
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
    myDialog= new Dialog(this);
    }

    public void CreatePopup(View v){
        TextView close;
        Button createUser;
        myDialog.setContentView(R.layout.new_user_popup);
        close = (TextView) myDialog.findViewById(R.id.close);
        createUser = (Button) myDialog.findViewById(R.id.createUser);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}
