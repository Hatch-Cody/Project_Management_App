package com.example.project_management_app;

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

public class Add_Task extends AppCompatActivity {
    private static final String TAG = "DatabaseInformation";
    // database references
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference reference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Task variables
    TextView title;
    EditText newTaskName,
            newTaskPriority,
            newTaskAssignTo,
            newTaskAssignDate,
            newTaskDescription,
            newTaskDueDate;
    Button createTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__task);

        //Set task variables
        createTaskButton = (Button) findViewById(R.id.createTaskButton);
        title = (TextView) findViewById(R.id.title);
        newTaskName = (EditText) findViewById(R.id.newTaskName);
        newTaskPriority = (EditText) findViewById(R.id.newTaskPriority);
        newTaskAssignTo = (EditText) findViewById(R.id.newTaskAssignTo);
        newTaskAssignDate = (EditText) findViewById(R.id.newTaskAssignDate);
        newTaskDueDate = (EditText) findViewById(R.id.newTaskDueDate);
        newTaskDescription = (EditText) findViewById(R.id.newTaskDescription);
        // Watches Button Create in activity_add_task.xml
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            // When Create Button is clicked this function is called
            @Override
            public void onClick(View v) {
                String taskName = newTaskName.getText().toString();
                String taskPriority = newTaskPriority.getText().toString();
                String taskAssignTo = newTaskAssignTo.getText().toString();
                String taskAssignDate = newTaskAssignDate.getText().toString();
                String taskDueDate = newTaskDueDate.getText().toString();
                String taskDescription = newTaskDescription.getText().toString();
                title.setText("Name:\t" + taskName + "\nPassword:\t" + taskPriority);

                Map<String, Object> task = new HashMap<>();
                task.put("taskName", taskName);
                task.put("priority", taskPriority);
                task.put("assignedTo", taskAssignTo);
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
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add_Task.this, "ERROR" +e.toString(),
                                        Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "problem adding task to database!");
                            }
                        });
            }
        });
    }
}