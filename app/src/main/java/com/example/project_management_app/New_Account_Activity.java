package com.example.project_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

/**
 * Handles account creation, instantiates database for user profiles.
 */
public class New_Account_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Profile_Adapter proAdapter;
    private EditText email, cEmail, password, cPassword, fName, lName;
    private Button create, exit;
    private String tag = "ACCOUNT_CREATION";
    private String id;
    private Profile profile;
    private DatabaseReference userProfiles;
    private Gson gson = new Gson();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        FirebaseApp.initializeApp(New_Account_Activity.this);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email_field1);
        cEmail = (EditText) findViewById(R.id.email_field2);
        password = (EditText) findViewById(R.id.password_field1);
        cPassword = (EditText) findViewById(R.id.password_field2);
        create = (Button) findViewById(R.id.create_Btn);
        exit = (Button) findViewById(R.id.exit_Btn_account_creation);
        lName = (EditText) findViewById(R.id.lName);
        fName = (EditText) findViewById(R.id.fName);
        userProfiles = FirebaseDatabase.getInstance().getReference("userProfiles");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag, "Create Account clicked...");

                if((!email.getText().toString().trim().equals(""))
                     || (!password.getText().toString().trim().equals(""))){
                    if ((email.getText().toString().trim().equals(cEmail.getText().toString().trim()))
                            && (password.getText().toString().trim().equals(cPassword.getText().toString().trim()))) {

                        final String fEmail = email.getText().toString().trim();
                        final String fPassword = password.getText().toString().trim();
                        id = userProfiles.push().getKey();
                        profile = new Profile(fEmail, fName.getText().toString().trim(), lName.getText().toString().trim(), id);
                        proAdapter = new Profile_Adapter(fEmail, id, profile);
                        userProfiles.child(id).setValue(proAdapter);

                        Intent i = new Intent();
                        i.putExtra("email", email.getText().toString().trim());
                        setResult(RESULT_OK, i);
                        Log.d(tag, "->" + proAdapter.getJson() + "<-|");
                        createAccount(fEmail, fPassword);

                        finish();
                    } else {
                        Toast.makeText(New_Account_Activity.this, "E-Mails or Passwords not the same.",
                                Toast.LENGTH_LONG).show();
                        Log.d(tag, "E-Mails or Passwords not the same. No creation attempt made");
                    }
                }else{
                    Toast.makeText(New_Account_Activity.this, "E-Mail or Password field empty.",
                            Toast.LENGTH_LONG).show();
                    Log.d(tag, "E-Mails or Passwords empty. No creation attempt made");
                    if(id != null){
                        userProfiles.child(id).removeValue();
                    }
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag, "Exit clicked");
                finish();
            }
        });


    }



        private void createAccount(final String fEmail, String fPassword){

            Log.d(tag, "Beginning account creation...");
//            final String id = userProfiles.push().getKey();
//            profile = new Profile(fEmail, fName.getText().toString().trim(), lName.getText().toString().trim(), id);
//            userProfiles.child(id).setValue(profile);

            mAuth.createUserWithEmailAndPassword(fEmail, fPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //if task succeeds, logs in immediately and skips this block??
                                Log.d(tag, "createUserWithEmail:success");
                                Toast.makeText(New_Account_Activity.this, "Account created",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.w(tag, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(New_Account_Activity.this, "Authentication Failed, Try Again.",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });

        }



}
