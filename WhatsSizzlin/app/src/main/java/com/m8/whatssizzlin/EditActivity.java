package com.m8.whatssizzlin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;

import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity {
    CollectionReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(EditActivity.this);


    }
}
