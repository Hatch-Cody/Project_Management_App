package com.example.project_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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

/**
 * Main activity for the Project Manager app.
 * Creates a recyclerView.
 * Creates a List of tasks.
 * Creates a menu and floating action button
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String tag = "MainActivity";
    private Profile userProfile = new Profile();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    private List<Task> tasksList;
    TaskAdapter taskAdapter;

    // Declare instance of database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // User login information
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(tag, "AUTHSTATE CHANGED");
                if(firebaseAuth.getCurrentUser() == null){
                    Log.d(tag, "User Not logged in, starting: LOGIN_ACTIVITY");
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                }else{
                    userProfile.setEmail("");
                    userProfile.setUserName("Logged in User");
                    Log.d(tag, "User Profile Loaded to App");

                    invalidateOptionsMenu();
                    Log.d(tag, "Menu Invalidated");

                }
            }
        };

        tasksList   = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksList);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
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

        TextView email_drawer_header    = findViewById(R.id.email_drawer_header);
        TextView userName_drawer_header = findViewById(R.id.userName_drawer_header);
        // ImageView profile_pic_header; // Will pass profile pic when set up

        BackgroundMenuThread menuSet = new BackgroundMenuThread(email_drawer_header, userName_drawer_header, handler, MainActivity.this, userProfile);
        menuSet.run();
        Log.d(tag, "Menu Created.");
        return true;
    }

    /**
     * Handle action bar item clicks
     * Action bar automatically handles clicks on the Home/Up button if
     * a parent activity is specified in AndroidManifest.xml.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            startActivity(new Intent(MainActivity.this, Add_Task.class));

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        signOut();
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
        Log.d("SIGN OUT()", "User Signed Out.");
    }

    // Floating Action Button link to create a new task
    public void newTask(View v) {
        startActivity(new Intent(MainActivity.this, Add_Task.class));
    }
}