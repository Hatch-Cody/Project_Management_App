package com.example.project_management_app;

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

public class New_Account_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, cEmail, password, cPassword;
    private Button create, exit;
    private String tag = "ACCOUNT_CREATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);

        FirebaseApp.initializeApp(New_Account_Activity.this);
        mAuth = FirebaseAuth.getInstance();

        email     = findViewById(R.id.email_field1);
        cEmail    = findViewById(R.id.email_field2);
        password  = findViewById(R.id.password_field1);
        cPassword = findViewById(R.id.password_field2);
        create    = findViewById(R.id.create_Btn);
        exit      = findViewById(R.id.exit_Btn_account_creation);

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

        private void createAccount(String fEmail, String fPassword){
            Log.d(tag, "Beginning account creation...");

            mAuth.createUserWithEmailAndPassword(fEmail, fPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //if task succeeds, logs in immediately and skips this block
                                Log.d(tag, "createUserWithEmail:success");
                                Toast.makeText(New_Account_Activity.this, "Account created, Please login.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                // immediately closes after log/toast
                                Log.w(tag, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(New_Account_Activity.this, "Authentication Failed, Try Again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
}
