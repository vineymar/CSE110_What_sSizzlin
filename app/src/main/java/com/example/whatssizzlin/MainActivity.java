package com.example.whatssizzlin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.io.InputStream;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private EditText email_id, password_id;
    private CheckBox checkBoxShowPwd;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        addOnListenerShowPassword();
        email_id = findViewById(R.id.email_login_id);
        password_id = findViewById(R.id.password_login_id);
        firebaseAuth = FirebaseAuth.getInstance();


    }
                                /*Navigation Buttons onClick*/
    /*for hiding password feature*/
    public void addOnListenerShowPassword(){
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

    /*Registration Button Click*/
    public void btnRegistration_Click(View v){
        //setContentView(R.layout.activity_registration);
        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
    }
    /*Login Button Click*/
    public void btnLogin_Click(View v){
        //firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Proccessing...", true);

        (firebaseAuth.signInWithEmailAndPassword(email_id.getText().toString(), password_id.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            UserDB.populateArrays();
                            String email =firebaseAuth.getCurrentUser().getEmail();
                            RegistrationActivity.name = firebaseAuth.getCurrentUser().getDisplayName();
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    /*/PhoneNumberLogIn Button Click*/
    public void btnPhoneNumberLogIn_Click(View v){
        startActivity(new Intent(MainActivity.this, PhoneNumberActivity.class));
    }
                            /*Navigation Buttons onClick*/
}
