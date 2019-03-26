package com.example.project_management_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tasks_newTask extends AppCompatActivity {
    Dialog myDialog;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Task> list;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_viewer);
        myDialog = new Dialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Task");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Task>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Task t = dataSnapshot1.getValue(Task.class);
                    list.add(t);
                }
                adapter = new TaskAdapter(tasks_newTask.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(tasks_newTask.this, "Unable to connect to database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CreatePopup(View v){
        TextView close;
        Button createTask;
        myDialog.setContentView(R.layout.new_task_popup);
        close = (TextView) myDialog.findViewById(R.id.close);
        createTask = (Button) myDialog.findViewById(R.id.newTaskFAB);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}
