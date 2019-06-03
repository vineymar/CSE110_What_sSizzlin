package com.example.whatssizzlin;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.widget.GridLayout.HORIZONTAL;


/**
 * A simple {@link Fragment} subclass.
 *
 *
 */
public class HomeFragment extends Fragment {
    final int CATEGORY_COUNT = 2;
    private View view;
    private boolean wait = true;
    RecyclerViewAdapter adapterRecommended;
    RecyclerViewAdapter adapterFavorite;
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


    public HomeFragment() {
        // Required empty public constructor
    }
////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home2, container, false);
        getRecommendedImages();
        /*Recommended Views*/
        LinearLayoutManager layoutRecManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerRecView = view.findViewById(R.id.recycleRecommendedView);
        recyclerRecView.setLayoutManager(layoutRecManager);
        adapterRecommended = new RecyclerViewAdapter(mRecNames, mRecImageUrls, mRecTimes, this.getContext());

        recyclerRecView.setAdapter(adapterRecommended);
        /*Recommended Views*/

        /*Favorite Views*/
        LinearLayoutManager layoutFavManager = new LinearLayoutManager(this.getContext(), HORIZONTAL, false);
        RecyclerView recyclerFavView = view.findViewById(R.id.recycleFavoritesView);
        recyclerFavView.setLayoutManager(layoutFavManager);
        adapterFavorite = new RecyclerViewAdapter(mFavNames, mFavImageUrls, mFavTimes, this.getContext());
        recyclerFavView.setAdapter(adapterFavorite);

        return view;
    }
/*
    private void initializeRecycleView(){

        /*Recommended Views*/
/*
        LinearLayoutManager layoutRecManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerRecView = view.findViewById(R.id.recycleRecommendedView);
        recyclerRecView.setLayoutManager(layoutRecManager);
        RecyclerViewAdapter adapterRecommended = new RecyclerViewAdapter(mRecNames, mRecImageUrls, mRecTimes, this.getContext());
        recyclerRecView.setAdapter(adapterRecommended);
        /*Recommended Views*/

        /*Favorite Views*/
    /*
        LinearLayoutManager layoutFavManager = new LinearLayoutManager(this.getContext(), HORIZONTAL, false);
        RecyclerView recyclerFavView = view.findViewById(R.id.recycleFavoritesView);
        recyclerFavView.setLayoutManager(layoutFavManager);
        RecyclerViewAdapter adapterFavorite = new RecyclerViewAdapter(mFavNames, mFavImageUrls, mFavTimes, this.getContext());
        recyclerFavView.setAdapter(adapterFavorite);


    }
*/
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
                            adapterRecommended.notifyDataSetChanged();
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
                            adapterFavorite.notifyDataSetChanged();
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








}
