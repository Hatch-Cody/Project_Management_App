package com.example.project_management_app;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/****
 * BACKGROUND MENU THREAD:
 *
 * Will take users profile and updates menu with user specific values
 */
public class BackgroundMenuThread extends Thread {
    private TextView email_textView;
    private TextView user_textView;
    private ImageView user_profile_pic;
    private Handler handler;
    private Context context; //in case we want to add context functionality
    private Profile userProfile;

    BackgroundMenuThread(TextView email_textView, TextView user_textView, Handler h, Context c, Profile profile){
        handler = h;
        this.user_textView = user_textView;
        this.email_textView = email_textView;
        userProfile = profile;
        context = c;
    }

    @Override
    public void run(){

        final String tag = "Background Menu Thread";
        Log.d(tag, "Starting Background Menu Thread...");

        final String e = userProfile.getEmail();
        final String u = userProfile.getUserName();

        handler.post(new Runnable() {
            @Override
            public void run() {
                email_textView.setText(e);
                user_textView.setText(u);
                Log.d(tag, "Header set, Terminating Thread.");
            }
        });


    }

}
