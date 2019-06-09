package com.example.whatssizzlin;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.util.Random;

import static android.widget.GridLayout.HORIZONTAL;


/**
 * A simple {@link Fragment} subclass.
 *
 *
 */
public class CookBookFragment extends Fragment {
    private View view;
    private CookBookFragment cbf = this;
    private boolean wait = true;
    CookbookRecyclerViewAdapter adapterRecommended;
    private final static String TAG = "HomeActivity: Imaging";
    /*For our images into our Recommended view*/
    private ArrayList<String> mRecNames = new ArrayList<>();
    private ArrayList<String> mRecImageUrls = new ArrayList<>();
    private ArrayList<String> mRecTimes = new ArrayList<>();
    private ArrayList<Recipe> mRecRecs = new ArrayList<>();
    public ArrayList<String> mRecIDs = new ArrayList<>();
    /*For our images into our view*/

    /*For our images into our Recommended view*/
    private ArrayList<String> mFavNames = new ArrayList<>();
    private ArrayList<String> mFavImageUrls = new ArrayList<>();
    private ArrayList<String> mFavTimes = new ArrayList<>();
    private ArrayList<Recipe> mFavRecs = new ArrayList<>();
    public ArrayList<String> mFavIDs;
    /*For our images into our view*/


    public CookBookFragment() {
        // Required empty public constructor
    }
////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cookbook, container, false);
        getRecommendedImages();
        /*Recommended Views*/
        LinearLayoutManager layoutRecManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerRecView = view.findViewById(R.id.cookbookRecyclerView);
        recyclerRecView.setLayoutManager(layoutRecManager);
        adapterRecommended = new CookbookRecyclerViewAdapter(mRecNames, mRecImageUrls, mRecTimes, mRecRecs,this.getContext(), this);

        Button b = view.findViewById(R.id.cookbook_Create_Recipe);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                CreateRecipeFragment cf = new CreateRecipeFragment();
                cf.cb = cbf;
                fragmentTransaction.replace(R.id.main_frame, cf);
                fragmentTransaction.commit();
            }
        });

        recyclerRecView.setAdapter(adapterRecommended);
        /*Recommended Views*/




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

    public void addRecRecipe(final List<String> ID, final int index){
        if(ID.size() == 0) return;
        FirebaseDatabase.getInstance().getReference().child("meals").child(ID.get(index)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Recipe r = dataSnapshot.getValue(Recipe.class);
                //mRecImageUrls.add("htpps:"+r.img_url);
                mRecNames.add(r.name);
                r.id = ID.get(index);
                mRecRecs.add(r);
                // Create a storage reference from our app
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference sr = storage.getReference();
                StorageReference pic = sr.child("mealImages/" + ID.get(index) + ".jpg");
                Log.d("CRD", pic.getDownloadUrl().toString());
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
                mRecTimes.add(r.timeTag + " Minutes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    //
    private void getRecommendedImages(){



        addRecRecipe(mRecIDs, 0);


    }


    public void setFragment(ViewRecipe nn) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, nn);
        fragmentTransaction.commit();
    }
}
