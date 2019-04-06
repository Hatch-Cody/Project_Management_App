package com.example.project_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    final String tag = "LOGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Log.d(tag, "onCreate() called...");

        FirebaseApp.initializeApp(Login_Activity.this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    Log.d(tag, "User Authenticated, Finishing Activity.");

//                    Intent i = new Intent();
//                    i.putExtra("email", email.getText().toString().trim());//////////////////////THIS PLACEMENT == double log
//                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        };

        Button login_Btn = (Button) findViewById(R.id.login_Btn);
        password = (EditText) findViewById(R.id.password_field);
        email = (EditText) findViewById(R.id.email_field);

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {

                Log.d(tag, "Login clicked...");
                signIn();
            }
        });

        Button new_user = (Button) findViewById(R.id.new_user);
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {

                Log.d(tag, "Create Account clicked...");
                startActivityForResult(new Intent(Login_Activity.this, New_Account_Activity.class), 2);
            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


        private void signIn() {
            String pswrd = password.getText().toString();
            String mail = email.getText().toString();

            generateResult();

            if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pswrd)) {
                Toast.makeText(Login_Activity.this, "Login Field(s) Empty", Toast.LENGTH_LONG).show();
                Log.d(tag, "Login Field(s) Empty, No Attempt Made...");
            } else {
                mAuth.signInWithEmailAndPassword(mail, pswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            Log.d(tag, "Login Failed.");
                        }

                    }

                });
            }

        }


    private void generateResult(){
        Intent i = new Intent();
        i.putExtra("email", email.getText().toString().trim());//////////////////////THIS PLACEMENT == double log
        setResult(RESULT_OK, i);
        Log.d(tag, "Result generated");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
        if(requestCode == 2 && resultCode == RESULT_OK){
            String rEmail = i.getStringExtra("email").trim();
            email.setText(rEmail);
        }

    }



}
