package com.example.project_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.UUID;
import java.util.Vector;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Profile userProfile = new Profile();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    private String tag = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////////////////LOGIN/////////////////////////////////////////////////
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                ////////////////////////////////////////////////////////////CALLING TWICE?????
                Log.d(tag, "AUHTSTATE CHANGED");
                if(firebaseAuth.getCurrentUser() == null){
                    Log.d(tag, "User Not logged in, starting: LOGIN_ACTIVITY");
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                }else{
                    userProfile.setEmail("");
                    userProfile.setUserName("Logged in User");
                    Log.d(tag, "User Profile Loaded to App...");

                    invalidateOptionsMenu();
                    Log.d(tag, "Menu Invalidated.");

                }
            }
        };

    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String tag = "Menu";
        Log.d(tag, "Creating new menu...");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final Handler handler = new Handler();

        TextView email_drawer_header = (TextView) findViewById(R.id.email_drawer_header);
        TextView userName_drawer_header = (TextView) findViewById(R.id.userName_drawer_header);
        ImageView profile_pic_header; ////////////////////////////Will pass profile pic when set up

        BackgroundMenuThread menuSet = new BackgroundMenuThread(email_drawer_header, userName_drawer_header, handler, MainActivity.this, userProfile);
        menuSet.run();
        Log.d(tag, "Menu Created.");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task_list) {
            // Handle the actions for selected menu items
            Log.i("Menu Button pushes", "Changing to task list");
            startActivity(new Intent(MainActivity.this, tasks_newTask.class));

        } else if (id == R.id.nav_new_task) {
            Log.i("Menu Button pushes", "Changing to new task");

        } else if (id == R.id.nav_groups) {
            Log.i("Menu Button pushes", "Changing to groups");
            startActivity(new Intent(MainActivity.this, Profile_page.class));
        } else if (id == R.id.nav_add_group) {
            Log.i("Menu Button pushes", "Changing to add group");

        } else if (id == R.id.nav_messages) {
            Log.i("Menu Button pushes", "Changing to messages");

        } else if (id == R.id.nav_settings) {
            Log.i("Menu Button pushes", "Changing to settings");

        }else if (id == R.id.nav_login) {
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
        } else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void upLoadTasks(View view){
        Task t = new Task("Clean Room", 5, Boolean.FALSE,
                0, "", "", "Tuesday",
                "Friday", "123");

        mDataBase.child("Tasks").child(t.getTaskId()).setValue(t);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        signOut();
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "User Logged Out", Toast.LENGTH_LONG).show();
        Log.d("SIGN OUT()", "User Signed Out.");
    }

}




