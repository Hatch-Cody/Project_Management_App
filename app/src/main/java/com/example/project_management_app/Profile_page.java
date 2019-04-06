package com.example.project_management_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * Displays profile, allows editing of profile info.
 */
public class Profile_page extends AppCompatActivity {
    Dialog myDialog;
    private TextView name1, name2, position, email, phoneNum, bio;
    private EditText phoneField, bioField, posField;
    private Profile userProfile;
    private  Gson gson = new Gson();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private List<Task> tasksList;
    TaskAdapter taskAdapter;
    private String tag = "PROFILE_PAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        myDialog = new Dialog(this);

        name1 = (TextView) findViewById(R.id.textView13);
        name2 = (TextView) findViewById(R.id.textView15);
        position = (TextView) findViewById(R.id.textView9);

        email = (TextView) findViewById(R.id.textView12);
        phoneNum = (TextView) findViewById(R.id.textView8);
        bio = (TextView) findViewById(R.id.textView14);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String json = intent.getString("json");
            userProfile = gson.fromJson(json, Profile.class);
            String name = (userProfile.getFirstName() + " " + userProfile.getLastName());
            name2.setText(name);
            name1.setText(name);
            position.setText(userProfile.getPosition());
            email.setText(userProfile.getEmail());
            phoneNum.setText(userProfile.getPhoneNumber());
            bio.setText(userProfile.getBio());
        }
        // TESTING FOR USERS TASKS TO DISPLAY ON THE PROFILE PAGE NEEDS TO BE CLEANED ONCE
        // EVERYTHING ELSE IS DONE
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

                        if(task.getAssignedTo().equals(userProfile.getFirstName())) {
                            Log.d(tag, "Inside if statement of onEvent in tasks_newTask after task assign when priority == 1");
                            tasksList.add(task);
                        }
                        Log.d(tag, "Inside if statement of onEvent in tasks_newTask ofter tasksList.add(task)");

                        taskAdapter.notifyDataSetChanged();
                        Log.d(tag, "Reading into recyclerView");
                    }

                }
            }
        });//END OF TESTING
    }

    public void CreatePopup(View v){
        TextView close;
        Button createUser;
        myDialog.setContentView(R.layout.new_user_popup);
        close = (TextView) myDialog.findViewById(R.id.close);
        createUser = (Button) myDialog.findViewById(R.id.createUser);

        phoneField = (EditText) findViewById(R.id.phone_field);
        bioField = (EditText) findViewById(R.id.bio_field);
        posField = (EditText) findViewById(R.id.position_field);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;

                if(phoneField != null) {
                    temp = phoneField.getText().toString().trim();
                    if (!temp.equals("")) {
                        userProfile.setPhoneNumber(temp);
                    }
                }

                if(posField != null) {
                    temp = posField.getText().toString().trim();
                    if (!temp.equals("")) {
                        userProfile.setPosition(temp);
                    }
                }

                if(bioField != null) {
                    temp = bioField.getText().toString().trim();
                    if (!temp.equals("")) {
                        userProfile.setBio(temp);
                    }
                }

                Toast.makeText(Profile_page.this, "Profile Changes Saved.",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent();
                temp = gson.toJson(userProfile);
                i.putExtra("json", temp);
                setResult(RESULT_OK, i);
                myDialog.dismiss();
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}


