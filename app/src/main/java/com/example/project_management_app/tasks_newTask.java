package com.example.project_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
 * This class creates new tasks by getting task attributes and adding them to the database
 */
public class tasks_newTask extends AppCompatActivity {
    private static final String TAG = "AppDatabaseInformation";

    private RecyclerView recyclerView;
    private List<Task> tasksList;
    TaskAdapter taskAdapter;

    // References to the Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference reference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_viewer);

        tasksList  = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksList);

        // Prepare recyclerView to be populated
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        db = FirebaseFirestore.getInstance();

        // Go through database and populate the recyclerView with Tasks
        db.collection("Tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                // Check if there is a problem
                if (e != null) {
                    Log.d(TAG, "Error: " + e.getMessage());
                }

                // Loop through Tasks document of the database
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        // Store task to be displayed to the recycler view
                        Task task = doc.getDocument().toObject(Task.class);

                        // Add task to taskList to be displayed in the recycler view
                        tasksList.add(task);
                        taskAdapter.notifyDataSetChanged();

                                Log.d(TAG, "Reading task into recyclerView");
                    }
                }
            }
        });
    }

        // Floating Action Button link to create a new task
        public void newTask(View v) {
                startActivity(new Intent(tasks_newTask.this, Add_Task.class));
        }
}
