package com.example.whatssizzlin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private CheckBox checkBoxShowPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);
        addOnListenerShowPassword();

        /*Underline clickable link in registration page "Already a member - log in"*/
        Button button = (Button) findViewById(R.id.btnAlreadyMember);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /*Underline clickable link in registration page "Already a member - log in"*/

        /*Initialize stuff*/
        email_id = findViewById(R.id.email_registration_id);
        password_id = findViewById(R.id.password_registration_id);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    /*for hiding password feature*/
    public void addOnListenerShowPassword() {
        checkBoxShowPwd = findViewById(R.id.show_password_id);
        checkBoxShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    password_id.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    password_id.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        /*End password hidden feature*/
    }




    public void btnRegistrationUser_click(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this,
                "Please wait...", "Processing...", true);

        (firebaseAuth.createUserWithEmailAndPassword(email_id.getText().toString(), email_id.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.e("Error", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /*Already a User Button Click*/
    public void btnAlreadyRegister_Click(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}


