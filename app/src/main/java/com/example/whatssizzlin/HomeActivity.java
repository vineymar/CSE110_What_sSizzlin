package com.example.whatssizzlin;
/*---------------------------Imports-------------------------------*/

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.whatssizzlin.Fragments.HomeFragment;
import com.example.whatssizzlin.Fragments.PantryFragment;
import com.example.whatssizzlin.Fragments.PreferencesFragment;
import com.example.whatssizzlin.Fragments.SearchFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

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
        getRecommendedImages();


        mainFrameView=findViewById(R.id.main_frame);
        bottomNavigationView = findViewById(R.id.BottomNavigation);
        /*Bottom Navigation stuff*/
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        pantryFragment = new PantryFragment();
        preferenceFragment = new PreferenceFragment();
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void addRecRecipe(final List<String> ID, final int index){
        FirebaseDatabase.getInstance().getReference().child("meals").child(ID.get(index)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Recipe r = dataSnapshot.getValue(Recipe.class);
                //mRecImageUrls.add("htpps:"+r.img_url);
                mRecNames.add(r.name);
                // Create a storage reference from our app
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference sr = storage.getReference();
                StorageReference pic = sr.child("mealImages/" + ID.get(index) + ".jpg");
                pic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mRecImageUrls.add(uri.toString());
                        if(index == (ID.size() - 1)){
                            initRecyclerView();
                        }
                        else{
                            addRecRecipe(ID, index + 1);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                mRecTimes.add(r.time.get(0).get("prep").get("mins"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addFavRecipe(final List<String> ID, final int index){
        FirebaseDatabase.getInstance().getReference().child("meals").child(ID.get(index)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Recipe r = dataSnapshot.getValue(Recipe.class);
                //mRecImageUrls.add("htpps:"+r.img_url);
                mFavNames.add(r.name);
                // Create a storage reference from our app
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference sr = storage.getReference();
                StorageReference pic = sr.child("mealImages/" + ID.get(index) + ".jpg");
                pic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mFavImageUrls.add(uri.toString());
                        if(index == (ID.size() - 1)){
                            initRecyclerView();
                        }
                        else{
                            addFavRecipe(ID, index + 1);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                mFavTimes.add(r.time.get(0).get("prep").get("mins"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    }

//
    private void getRecommendedImages(){
        Log.d(TAG, "Inside getImages: ");
        ArrayList<String> rec = new ArrayList<String>() {
            {
                add("0");
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
            }
        };
        ArrayList<String> fav = new ArrayList<String>() {
            {
                add("6");
                add("7");
                add("8");
                add("9");
                add("10");
                add("11");
            }
        };

        addFavRecipe(fav, 0);
        addRecRecipe(rec, 0);


    }

    private void initRecyclerView(){
        Log.d(TAG, "Initializing RecyclerView");
        if(calls != DISPLAY_CATEGORY_COUNT - 1){
            calls++;
            return;
        }
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
