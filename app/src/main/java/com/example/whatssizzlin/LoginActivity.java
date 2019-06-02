package com.example.whatssizzlin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText email_id;
    private EditText password_id;

    // [START declare_auth]
    private FirebaseAuth firebaseAuth;
    // [END declare_auth]
    //private static final String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        email_id = findViewById(R.id.email_login_id);
        password_id = findViewById(R.id.password_login_id);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
