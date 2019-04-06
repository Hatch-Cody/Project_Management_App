package com.example.project_management_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> dataCatch = new ArrayList<>();
    private Query query;
    private Gson gson = new Gson();
    private Profile userProfile = new Profile();
    private FirebaseAuth mAuth;
    private String email = "";
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userProfilesRef;
    private String tag = "MAIN_ACTIVITY";
    // instance of firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Dialog myDialog;
    private RecyclerView recyclerView;
    private List<Task> tasksList;
    TaskAdapter taskAdapter;
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

                Log.d(tag, "AUHTSTATE CHANGED");
                if(firebaseAuth.getCurrentUser() == null){
                    Log.d(tag, "User Not logged in, starting: LOGIN_ACTIVITY");
                    startLogin();
                }else{
                    invalidateOptionsMenu();
                    Log.d(tag, "Menu Invalidated.");

                }
            }
        };
        ////////////////////////////////////////////////////////////////////////////////////////////


        // TESTING FOR TOP PRIORITY TASKS TO DISPLAY ON THE HOME SCREEN NEEDS TO BE CLEANED ONCE
        // EVERYTHING ELSE IS DONE
        displayPriorityTaks();
        tasksList = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksList);
        db = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
        db.collection("Tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(tag, "Error: " + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    Log.d(tag, "Inside for loop of onEvent");

                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Log.d(tag, "Inside if statement of onEvent in tasks_newTask");
                        Task task =new Task();
                        task = doc.getDocument().toObject(Task.class);

                        if(task.getPriority().equals("1")) {
                            Log.d(tag, "Inside if statement of onEvent in tasks_newTask after task assign when priority == 1");
                            tasksList.add(task);
                        }
                        Log.d(tag, "Inside if statement of onEvent in tasks_newTask ofter tasksList.add(task)");

                        taskAdapter.notifyDataSetChanged();
                        Log.d(tag, "Reading into recyclerView");
                    }

                }
            }
        });
    }
    //END OF TESTING

    protected void displayPriorityTaks(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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

        final String tag = "Menu";
        Log.d(tag, "Creating new menu...");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        userProfilesRef = FirebaseDatabase.getInstance().getReference("userProfiles");
        final Handler handler = new Handler();

        //Queries the database for user
        Log.d(tag, "->" + email + "<-| being queried...");
        query = userProfilesRef.orderByChild("userName").equalTo(email);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    dataCatch.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String pID = snapshot.child("json").getValue(String.class);
                        dataCatch.add(pID);
                        Log.d(tag, "Snapshot Recorded");
                    }
                }
                else{
                    Log.d(tag, "Snapshot NOT Recorded");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(tag, "ERROR, VALUE EVENT LISTENER FAILED:");
                Log.d(tag, databaseError.toString());
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
        //dataCatch.add("NO PROFILE FOUND");
        if(!dataCatch.isEmpty()) {
            userProfile = gson.fromJson(dataCatch.get(0), Profile.class);
            //Log.d(tag, "->" + dataCatch.get(0) + "<-| json received from database");
        }

        TextView email_drawer_header = (TextView) findViewById(R.id.email_drawer_header);
        TextView userName_drawer_header = (TextView) findViewById(R.id.userName_drawer_header);
        ImageView profile_pic_header; ////////////////////////////Will pass profile pic when set up

        BackgroundMenuThread menuSet = new BackgroundMenuThread(email_drawer_header, userName_drawer_header, handler, MainActivity.this, userProfile);
        menuSet.run();

        //checks if menu updated before tick, and if so, resets the menu once more
        if(email_drawer_header.getText().toString().equals("NotLoggedIn@noMail.com")){
            invalidateOptionsMenu();
        }
        Log.d(tag, "Menu Created.");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            email = i.getStringExtra("email").trim();
        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            String json = i.getStringExtra("json");
            //UPLOAD NEW PROFILE HERE



        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_task_list) {
            startActivity(new Intent(MainActivity.this, tasks_newTask.class));

        } else if (id == R.id.nav_new_task) {

            startActivity(new Intent(MainActivity.this, Add_Task.class));

        } else if (id == R.id.nav_groups) {
            Intent i = new Intent(MainActivity.this, Profile_page.class);
            String json = gson.toJson(userProfile);
            i.putExtra("json", json);
            startActivityForResult(i, 3);
        } else if (id == R.id.nav_add_group) {


        } else if (id == R.id.nav_messages) {
            invalidateOptionsMenu();

        } else if (id == R.id.nav_settings) {

        }else if (id == R.id.nav_login) {
            //startActivityForResult(new Intent(MainActivity.this, Login_Activity.class), 1);
            signOut();

        } else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy(){
        signOut();
        super.onDestroy();
    }

    public void startLogin(){
        startActivityForResult(new Intent(MainActivity.this, Login_Activity.class), 1);
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
        Log.d("SIGN OUT()", "User Signed Out.");
    }

}




