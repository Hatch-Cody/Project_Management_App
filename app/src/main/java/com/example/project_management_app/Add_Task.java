package com.example.project_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_Task extends AppCompatActivity /* implements AdapterView.OnItemSelectedListener */ {
    private static final String TAG = "DatabaseInformation";

    // database references
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference reference;

    private int year = 0;
    private int month = 0;
    private int days = 0;

    //create instance of firebasefirestore to enable task addition
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Task variables
    TextView title;
    EditText newTaskName,
             newTaskPriority,
             newTaskAssignedTo,
             newTaskAssignDate,
             newTaskDescription,
             newTaskDueDate;
    Button   createTaskButton;

    //Spinner prioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__task);

        /*
        // Spinner for selecting the priority of a task
        prioritySpinner = (Spinner) findViewById(R.id.newTaskPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setOnItemSelectedListener(this);
        */

        //Set task variables
        createTaskButton   = (Button)   findViewById(R.id.createTaskButton);
        title              = (TextView) findViewById(R.id.title);
        newTaskName        = (EditText) findViewById(R.id.newTaskName);
        newTaskPriority    = (EditText) findViewById(R.id.newTaskPriority);
        newTaskAssignedTo  = (EditText) findViewById(R.id.newTaskAssignedTo);
        newTaskAssignDate  = (EditText) findViewById(R.id.newTaskAssignDate);
        newTaskDueDate     = (EditText) findViewById(R.id.newTaskDueDate);
        newTaskDescription = (EditText) findViewById(R.id.newTaskDescription);

        // Watches Button Create in activity_add_task.xml
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            // When Create Button is clicked this function is called
            @Override
            public void onClick(View v) {
                // Create string convert to strings
                String taskName = newTaskName.getText().toString();
                String taskPriority = newTaskPriority.getText().toString();
                String taskAssignedTo = newTaskAssignedTo.getText().toString();
                String taskAssignDate = newTaskAssignDate.getText().toString();
                String taskDueDate = newTaskDueDate.getText().toString();
                String taskDescription = newTaskDescription.getText().toString();

//                Calendar currentDate = Calendar.getInstance();
//                year = currentDate.get(Calendar.YEAR);
//                month = currentDate.get(Calendar.MONTH);
//                days = currentDate.get(Calendar.DAY_OF_MONTH);
//
//
//                String Date = DateFormat.getDateInstance().format(taskAssignDate);
//                String DueDate = DateFormat.getDateInstance().format(taskDueDate);
//
//
//                if (year == ) {
//                    Toast.makeText(Add_Task.this, "Not a valid date, please re-enter date",
//                            Toast.LENGTH_LONG).show();
//                }else if (){
//
//                } else {

                    Map<String, Object> task = new HashMap<>();
                    task.put("taskName", taskName);
                    task.put("priority", taskPriority);
                    task.put("assignedTo", taskAssignedTo);
                    task.put("assignDate", taskAssignDate);
                    task.put("dueDate", taskDueDate);
                    task.put("description", taskDescription);

                    // Add a new document with a generated ID
                    db.collection("Tasks")
                            .add(task)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Add_Task.this, "Task Added Successfully!",
                                            Toast.LENGTH_SHORT).show();
                                    //RETURN TO TASK_NEWTASK IF IT IS SUCCESSFUL
                                    startActivity(new Intent(Add_Task.this, tasks_newTask.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Add_Task.this, "ERROR" + e.toString(),
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "problem adding task to database!");
                                }
                            });
             //   }
            }
        });
    }

    /*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object priority = parent.getItemAtPosition(position);
        newTaskPriority = (EditText) priority;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    */
}