package com.example.project_management_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    /*
    public void upLoadTasks(View view) {
        Task t = new Task("Clean Room", 5, Boolean.FALSE,
                0, "", "", "Tuesday",
                "Friday", "123");

        reference.child("Tasks").child(t.getTaskId()).setValue(t);
    }
    */

    // Creates a popup when the FAB (Floating Action Button) is clicked
    public void CreatePopup(View v) {
        TextView close;
        Button createTask;
        myDialog.setContentView(R.layout.new_task_popup);
        close = (TextView) myDialog.findViewById(R.id.close);
        createTask = (Button) myDialog.findViewById(R.id.newTaskFAB);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    // Reads the information from the popup into the database
    public void uploadTask() {

        /*
        Log.d(TAG, "uploadTask() called");

        createTaskButton = (Button) findViewById(R.id.createTaskButton);
        newTaskName = (EditText) findViewById(R.id.newTaskName);
        newTaskPriority = (EditText) findViewById(R.id.newTaskPriority);
        newTaskAssignTo = (EditText) findViewById(R.id.newTaskAssignTo);
        newTaskAssignDate = (EditText) findViewById(R.id.newTaskAssignDate);
        newTaskDueDate = (EditText) findViewById(R.id.newTaskDueDate);
        newTaskDescription = (EditText) findViewById(R.id.newTaskDescription);

        // declare database reference object. Must be signed in to access
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reference = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("Successfully signed out.");
                }
            }
        };

        // read from the database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // this is called whenever data at this location is updated
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to add task to database.");

                // Save the task name to the database
                String newName = newTaskName.getText().toString();
                if(!newName.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    reference.child(userID).child("Tasks").child("Task").child("taskName").setValue(newName);
                    toastMessage("Adding " + newName + " to database...");
                    //reset the text
                    newTaskName.setText("");
                }

                // Save the task priority to the database
                String newPriority = newTaskPriority.getText().toString();
                if(!newPriority.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    reference.child(userID).child("Task").child("Task").child("priority").setValue(newPriority);
                    toastMessage("Adding " + newPriority + " to database...");
                    //reset the text
                    newTaskPriority.setText("");
                }

                // Save the task assignedTo to the database
                String newAssignedTo = newTaskAssignTo.getText().toString();
                if(!newAssignedTo.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    reference.child(userID).child("Task").child("Task").child("assignedTo").setValue(newAssignedTo);
                    toastMessage("Adding " + newAssignedTo + " to database...");
                    //reset the text
                    newTaskAssignTo.setText("");
                }

                // Save the task assignedDate to the database
                String newAssignedDate = newTaskAssignDate.getText().toString();
                if(!newAssignedDate.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    reference.child(userID).child("Task").child("Task").child("assignedDate").setValue(newAssignedDate);
                    toastMessage("Adding " + newAssignedDate + " to database...");
                    //reset the text
                    newTaskAssignDate.setText("");
                }

                // Save the task dueDate to the database
                String newDueDate = newTaskDueDate.getText().toString();
                if(!newDueDate.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    reference.child(userID).child("Task").child("Task").child("dueDate").setValue(newDueDate);
                    toastMessage("Adding " + newDueDate + " to database...");
                    //reset the text
                    newTaskDueDate.setText("");
                }

                // Save the task description to the database
                String newDescription = newTaskDescription.getText().toString();
                if(!newDescription.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    reference.child(userID).child("Task").child("Task").child("description").setValue(newDescription);
                    toastMessage("Adding " + newDescription + " to database...");
                    //reset the text
                    newTaskDescription.setText("");
                }
            }
        });
        */

    }

    @Override
    public void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
