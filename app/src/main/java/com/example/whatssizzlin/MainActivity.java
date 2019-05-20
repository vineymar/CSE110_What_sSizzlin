package com.example.whatssizzlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email_id, password_id;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }

    //Registration Button Click
    public void btnRegistration_Click(View v){
        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
    }
    //Login Button Click
    public void btnLogin_Click(View v){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    //GoogleLogIn Button Click
    public void btnGoogleLogIn_Click(View v){
        startActivity(new Intent(MainActivity.this, GoogleActivity.class));
    }
    //FacebookLogIn Button Click
    public void btnFacebookLogIn_Click(View v){
        startActivity(new Intent(MainActivity.this, FacebookActivity.class));
    }
    //TwitterogIn Button Click
    public void btnTwitterLogIn_Click(View v){
        startActivity(new Intent(MainActivity.this, TwitterActivity.class));
    }
    //PhoneNumberLogIn Button Click
    public void btnPhoneNumberLogIn_Click(View v){
        startActivity(new Intent(MainActivity.this, PhoneNumberActivity.class));
    }
}
