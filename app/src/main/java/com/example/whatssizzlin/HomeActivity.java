package com.example.whatssizzlin;
/*---------------------------Imports-------------------------------*/

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
/*---------------------------Imports-------------------------------*/

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity: Imaging";
    private static final int GET_FROM_GALLERY = 3;
    /*For our images into our Recommended view*/
    private ArrayList<String> mRecNames = new ArrayList<>();
    private ArrayList<String> mRecImageUrls = new ArrayList<>();
    private ArrayList<String> mRecTimes = new ArrayList<>();
    private Bitmap bitmap = null;
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
        Toast.makeText( this, "Welcome " + RegistrationActivity.name, Toast.LENGTH_LONG).show();
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

    public Bitmap getBitmap(){
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("why", String.valueOf(requestCode) + " " + String.valueOf(GET_FROM_GALLERY) + " " + resultCode);

        super.onActivityResult(requestCode, resultCode, data);
        Log.d("why", String.valueOf(requestCode) + " " + String.valueOf(GET_FROM_GALLERY) + " " + resultCode);

        //Detects request codes
        if(requestCode==65539) {
            if (data == null){
                return;
            }
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Log.d("why", String.valueOf(bitmap.getByteCount()));
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }




}