package com.example.whatssizzlin;
/*---------------------------Imports-------------------------------*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.whatssizzlin.Fragments.HomeFragment;
import com.example.whatssizzlin.Fragments.PantryFragment;
import com.example.whatssizzlin.Fragments.PreferencesFragment;
import com.example.whatssizzlin.Fragments.SearchFragment;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

import butterknife.BindView;
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


    @BindView(R.id.BottomNavigation)
    BottomNavigationView bottomNavigationView;

    CollectionReference userRef;
    AlertDialog dialog;
    /*Bottom Navigation*/

    /*OnCreate method*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        getRecommendedImages();


        /*Bottom Navigation stuff*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.
                OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                if (menuItem.getItemId() == R.id.NavigationHome) {
//                    fragment = new HomeFragment();
//                } else if (menuItem.getItemId() == R.id.NavigationSearch) {
//                    fragment = new SearchFragment();
//                } else if (menuItem.getItemId() == R.id.NavigationPantry) {
//                    fragment = new PantryFragment();
//                } else if (menuItem.getItemId() == R.id.NavigationPreferences) {
//                    fragment = new PreferencesFragment();
//                }
                switch(menuItem.getItemId()){
                    case R.id.NavigationHome: fragment = new HomeFragment(); break;
                    case R.id.NavigationSearch: fragment = new SearchFragment(); break;
                    case R.id.NavigationPantry: fragment = new PantryFragment(); break;
                    case R.id.NavigationPreferences: fragment = new PreferencesFragment(); break;
                }
                return loadFragment(fragment);
            }
        });


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    private void getRecommendedImages(){
        Log.d(TAG, "Inside getImages: ");

        /* Need a way to pull image urls and image_names
         * mImageUrls.add(Image URL stuff)
         * mNames.add(Image URL stuff)
         * */
        /*Recommended For loop ideally*/
        mRecImageUrls.add("https://www.onceuponachef.com/images/2017/10/How-To-Make-Hard-Boiled-Eggs-760x516.jpg");
        mRecNames.add("Eggs");
        mRecTimes.add("5 min");
        mRecImageUrls.add("https://www.thespruceeats.com/thmb/CtGnnAdHCVd5jms3JyTDfIgDzR0=/960x0/filters:no_upscale():max_bytes(150000):strip_icc()/French-Toast-58addf8e5f9b58a3c9d41348.jpg");
        mRecNames.add("Toast");
        mRecTimes.add("2 min");
        mRecImageUrls.add("https://i.kinja-img.com/gawker-media/image/upload/s--_iU5hnjV--/c_scale,f_auto,fl_progressive,q_80,w_800/naiqjp2jbpvcylp09utj.png");
        mRecNames.add("Batman");
        mRecTimes.add("20 min");
        mRecImageUrls.add("https://media.wired.com/photos/5c54ee6a4feec32ca0f590d8/master/w_2400,c_limit/superman-922909434.jpg");
        mRecNames.add("Superman");
        mRecTimes.add("50 min");
        /*Recommended end For loop ideally*/

        /*Favorite For loop ideally*/
        mFavImageUrls.add("https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/chicken_pasta_bake_25701_16x9.jpg");
        mFavNames.add("Pasta");
        mFavTimes.add("20 min");

        mFavImageUrls.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/delish-keto-pizza-073-1544039876.jpg");
        mFavNames.add("Pizza");
        mFavTimes.add("30 min");

        mFavImageUrls.add("https://www.tasteofhome.com/wp-content/uploads/2017/10/exps28800_UG143377D12_18_1b_RMS-696x696.jpg");
        mFavNames.add("Burger");
        mFavTimes.add("35 min");

        mFavImageUrls.add("https://s23209.pcdn.co/wp-content/uploads/2019/04/Mexican-Street-TacosIMG_9091.jpg");
        mFavNames.add("Tacos");
        mFavTimes.add("20 min");

        mFavImageUrls.add("https://s23209.pcdn.co/wp-content/uploads/2018/06/Creamy-Chorizo-Queso-DipIMG_5112-copy.jpg");
        mFavNames.add("Lasagna");
        mFavTimes.add("30 min");
        /*Favorite end For loop ideally*/

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "Initializing RecyclerView");

        /*Recommended Views*/
        LinearLayoutManager layoutRecManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerRecView = findViewById(R.id.recycleRecommendedView);
        recyclerRecView.setLayoutManager(layoutRecManager);
        RecyclerViewAdapter adapterRecommended = new RecyclerViewAdapter(mRecNames, mRecImageUrls, mRecTimes, this);
        recyclerRecView.setAdapter(adapterRecommended);
        /*Recommended Views*/

        /*Favorite Views*/
        LinearLayoutManager layoutFavManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerFavView = findViewById(R.id.recycleFavoritesView);
        recyclerFavView.setLayoutManager(layoutFavManager);
        RecyclerViewAdapter adapterFavorite = new RecyclerViewAdapter(mFavNames, mFavImageUrls, mFavTimes, this);
        recyclerFavView.setAdapter(adapterFavorite);
        /*Favorite Views*/
    }






}
