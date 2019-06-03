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

import com.example.whatssizzlin.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {

    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static String name;
    private EditText name_id;
    private EditText email_id;
    private EditText password_id;
    private FirebaseAuth firebaseAuth;  //for authenticating firebase for useremail password
    private CheckBox checkBoxShowPwd;
    private DatabaseReference mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addOnListenerShowPassword();
        FirebaseApp.initializeApp(this);


        /*Initialize stuff*/
        email_id = findViewById(R.id.email_registration_id);
        password_id = findViewById(R.id.password_registration_id);
        name_id = findViewById(R.id.name_registration_id);
        firebaseAuth = FirebaseAuth.getInstance();

        /*Underline clickable link in registration page "Already a member - log in"*/
        Button button = (Button) findViewById(R.id.btnAlreadyMember);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /*Underline clickable link in registration page "Already a member - log in"*/
    }

    /*for hiding password feature*/
    public void addOnListenerShowPassword() {
        checkBoxShowPwd = findViewById(R.id.show_password_registration_id);
        checkBoxShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (isChecked) {
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

    /*Already a User Button Click*/
    public void btnAlreadyRegister_Click(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    /*Register Now Button Pressed*/
    public void btnRegistrationUser_Click(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "Please wait...", "Processing...", true);
        (firebaseAuth.createUserWithEmailAndPassword(email_id.getText().toString(), password_id.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            name = name_id.getText().toString();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                              writeNewUser( user.getUid(), user.getDisplayName(), user.getEmail());
                                            }
                                        }
                                    });
                            //Toast.makeText(RegistrationActivity.this, "Registration successful, Welcome!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                        }
                        else
                        {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
        UserPref preferences = new UserPref();
        mDatabase.child("users").child(userId).child("preferences").setValue(preferences);
    }
}


