package com.example.project_management_app;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Attaches user profile info to the main menu.
 */
public class BackgroundMenuThread extends Thread {
    private TextView email_textView,  user_textView;
    private ImageView user_profile_pic;
    private Handler handler;
    private Context context;        //in case we want to add context functionality
    private Profile userProfile;

    /**
     * Constructor.
     * @param email_textView refers to email field on menu header.
     * @param user_textView refers to user name field on menu header.
     * @param h handler
     * @param c context (Main.this)
     * @param profile user's profile from main
     */
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
        final String u = (userProfile.getFirstName() + " " + userProfile.getLastName());

        handler.post(new Runnable() {
            @Override
            public void run() {
                email_textView.setText(e);  // I'm being told there is an error and its starting
                user_textView.setText(u);  // here on line 42.
                Log.d(tag, "Header set, Terminating Thread.");
            }
        });
    }
}
