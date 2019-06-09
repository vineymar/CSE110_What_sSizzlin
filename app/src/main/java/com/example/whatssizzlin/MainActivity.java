package com.example.whatssizzlin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        email_id=findViewById(R.id.email_login_id);
        password_id=findViewById(R.id.password_login_id);
        firebaseAuth = FirebaseAuth.getInstance();

        /*Underline clickable link in registration page "Already a member - log in"*/
        Button button = (Button) findViewById(R.id.btnRegistration);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /*Underline clickable link in registration page "Already a member - log in"*/


    }

////
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
                            //UserDB.populateArrays();
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
              /*Navigation Buttons onClick*/
}
