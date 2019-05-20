package com.example.whatssizzlin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {

    private EditText email_id;
    private EditText password_id;
    private FirebaseAuth firebaseAuth;  //for authenticating firebase for useremail password


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);
        email_id = findViewById(R.id.email_resgistrationid);
        password_id = findViewById(R.id.password_resgistrationid);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void btnRegistrationUser_click(View v){
            final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this,
                    "Please wait...", "Processing...", true);

            (firebaseAuth.createUserWithEmailAndPassword(email_id.getText().toString(),email_id.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                Log.e("Error", task.getException().toString());
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }


