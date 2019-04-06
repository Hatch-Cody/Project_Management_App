package com.example.project_management_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * This class creates new tasks from a popup window and then adds them to the database
 */
public class tasks_newTask extends AppCompatActivity {
    private static final String TAG = "DatabaseInformation";

    Dialog myDialog;
    RecyclerView recyclerView;
    ArrayList<Task> list;
    TaskAdapter adapter;
    private Button createTaskButton;
    private EditText newTaskName;
    private EditText newTaskPriority;
    private EditText newTaskAssignTo;
    private EditText newTaskAssignDate;
    private EditText newTaskDueDate;
    private EditText newTaskDescription;

    // database references
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference reference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_viewer);
        myDialog = new Dialog(this);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // list view to display tasks from database
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child("Task");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(tasks_newTask.this, "Connected to database", Toast.LENGTH_SHORT).show();

                list = new ArrayList<Task>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Task t = dataSnapshot1.getValue(Task.class);
                    list.add(t);
                }
                adapter = new TaskAdapter(tasks_newTask.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(tasks_newTask.this, "Unable to connect to database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void newTask(View v) {
        startActivity(new Intent(tasks_newTask.this, Add_Task.class));
    }
}
