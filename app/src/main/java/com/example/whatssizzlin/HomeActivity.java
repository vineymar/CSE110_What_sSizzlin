package com.example.whatssizzlin;
/*---------------------------Imports-------------------------------*/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
/*---------------------------Imports-------------------------------*/

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity: Imaging";
    /*For our images into our Recommended view*/
    private ArrayList<String> mRecNames = new ArrayList<>();
    private ArrayList<String> mRecImageUrls = new ArrayList<>();
    private ArrayList<String> mRecTimes = new ArrayList<>();
    /*For our images into our view*/

    /*For our images into our Recommended view*/
    private ArrayList<String> mFavNames = new ArrayList<>();
    private ArrayList<String> mFavImageUrls = new ArrayList<>();
    private ArrayList<String> mFavTimes = new ArrayList<>();
    /*For our images into our view*/

    final int DISPLAY_CATEGORY_COUNT = 2;

    int calls = 0;

    /*Fragments and Navigation Bar*/
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mainFrameView;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PantryFragment pantryFragment;
    private PreferenceFragment preferenceFragment;
    /*Fragments and Navigation Bar*/



    /*OnCreate method*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        //getRecommendedImages();


        mainFrameView=findViewById(R.id.main_frame);
        bottomNavigationView = findViewById(R.id.BottomNavigation);
        /*Bottom Navigation stuff*/
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        pantryFragment = new PantryFragment();
        preferenceFragment = new PreferenceFragment();
        homeFragment.home = this;
        setFragment(homeFragment);


        /*Navigation bar clicking*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.NavigationHome:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorApp);
                        setFragment(homeFragment);
                        return true;
                    case R.id.NavigationSearch:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorApp);
                        setFragment(searchFragment);
                        return true;
                    case R.id.NavigationPantry:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorApp);
                        setFragment(pantryFragment);
                        return true;
                    case R.id.NavigationPreferences:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorApp);
                        setFragment(preferenceFragment);
                        return true;
                    default:
                        return false;
                }
            }
            private void setFragment(Fragment fragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();

            }
        });

    }

    private void viewRecipe(Recipe rec){
        Intent myIntent = new Intent(HomeActivity.this, ViewRecipe.class);
        //myIntent.putExtra("recipe", rec);
        //myIntent.putE

    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }









}