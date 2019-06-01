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

public class LoginActivity extends AppCompatActivity {

    private EditText email_id;
    private EditText password_id;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        /*Email/Password/Verifying links*/
        email_id = findViewById(R.id.email_login_id);
        password_id =findViewById(R.id.password_login_id);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void btnLogin_Click(View v){
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Authenticating...", true);
        (firebaseAuth.createUserWithEmailAndPassword(email_id.getText().toString(),email_id.getText().toString()))
            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }else{
                            Log.e("Error", task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}


