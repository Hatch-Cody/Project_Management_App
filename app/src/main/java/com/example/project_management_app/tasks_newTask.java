package com.example.project_management_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    // Read the information from the popup into the database
    public void uploadTask(View v) {
        Log.d(TAG, "uploadTask() called");

        // Get task information from the popup window
        createTaskButton = (Button) findViewById(R.id.createTaskButton);
        newTaskName = (EditText) findViewById(R.id.newTaskName);
        newTaskPriority = (EditText) findViewById(R.id.newTaskPriority);
        newTaskAssignTo = (EditText) findViewById(R.id.newTaskAssignTo);
        newTaskAssignDate = (EditText) findViewById(R.id.newTaskAssignDate);
        newTaskDueDate = (EditText) findViewById(R.id.newTaskDueDate);
        newTaskDescription = (EditText) findViewById(R.id.newTaskDescription);

        // Create a new task with the information
        Map<String, Object> task = new HashMap<>();
        task.put("taskName", newTaskName);
        task.put("priority", newTaskPriority);
        task.put("assignedTo", newTaskAssignTo);
        task.put("assignDate", newTaskAssignDate);
        task.put("dueDate", newTaskDueDate);
        task.put("description", newTaskDescription);

        // Add a new document with a generated ID
        db.collection("Tasks").document("Task").set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(tasks_newTask.this, "Task Added Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(tasks_newTask.this, "ERROR" +e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "problem adding task to database!");
                    }
                });
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
