package com.example.project_management_app;

import android.app.Dialog;
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
 * This class creates new tasks from a popup window and then adds them to the database
 */
public class tasks_newTask extends AppCompatActivity {
    private static final String TAG = "DatabaseInformation";

    Dialog myDialog;
    private RecyclerView recyclerView;
    private List<Task> tasksList;
    TaskAdapter taskAdapter;

    // database references
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference reference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_viewer);

        tasksList = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("assignedTo");

                        //tasksList.add(task);

                        //taskAdapter.notifyDataSetChanged();

                        Log.d(TAG, "Name" + name);
                    }
                }
            }
        });
    }

        // FAB link to create a new task
        public void newTask(View v) {
                startActivity(new Intent(tasks_newTask.this, Add_Task.class));
        }
}
