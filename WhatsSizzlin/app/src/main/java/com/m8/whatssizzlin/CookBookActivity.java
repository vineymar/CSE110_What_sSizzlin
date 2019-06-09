package com.m8.whatssizzlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m8.whatssizzlin.Fragments.CreateFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CookBookActivity extends AppCompatActivity {
    @OnClick(R.id.card_create)
    void createRecipes() {
        startActivity(new Intent(this, CreateFragment.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        ButterKnife.bind(CookBookActivity.this);
    }


}
