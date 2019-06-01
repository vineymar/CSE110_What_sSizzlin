package com.example.whatssizzlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText email_id, password_id;
    private CheckBox checkBoxShowPwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addOnListenerShowPassword();
        /*For password hidden feature*/
        email_id = findViewById(R.id.email_login_id);
        password_id = findViewById(R.id.password_login_id);
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
        setContentView(R.layout.activity_registration);
    }
    /*Login Button Click*/
    public void btnLogin_Click(View v){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    /*/PhoneNumberLogIn Button Click*/
    public void btnPhoneNumberLogIn_Click(View v){
        startActivity(new Intent(MainActivity.this, PhoneNumberActivity.class));
    }

                                /*Navigation Buttons onClick*/
}
