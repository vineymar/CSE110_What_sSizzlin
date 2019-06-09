package com.example.whatssizzlin;
import android.support.annotation.NonNull;
import android.telecom.Call;

import com.example.whatssizzlin.Model.User;
import com.example.whatssizzlin.Service.UserPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDB {

    public static List<Integer> favRecipeList = new ArrayList();
    public static List<Recipe> recipeList = new ArrayList<>();
    public static List<IngredientTag> userInventory = new ArrayList<>();
    private static DatabaseReference mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference();
    public static FirebaseUser FBUser = FirebaseAuth.getInstance().getCurrentUser();

    public static void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
        UserPref preferences = new UserPref();
        mDatabase.child("users").child(userId).child("preferences").setValue(preferences);
    }

    public static void populateArrays( ){
        readInventory();
        readFav();
        readPref();
    }

    public static void readPref(){
        //Toast.makeText(home, "Only once", Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FBUser.getUid() + "/favorites");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meals/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Integer recipeId = dataSnapshot1.getValue(Integer.class);
                    if (!favRecipeList.contains(recipeId.toString())) {
                        favRecipeList.add(recipeId);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void readFav() {
        //Toast.makeText(home, "Only once", Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FBUser.getUid() + "/favorites");
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meals/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Integer recipeId = dataSnapshot1.getValue(Integer.class);
                    if (!favRecipeList.contains(recipeId.toString())) {
                        favRecipeList.add(recipeId);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void addFav( Integer recipeId ){
        favRecipeList.add(recipeId);
        HomeFragment.favList.add(recipeId.toString());
        mDatabase.child("users").child(FBUser.getUid()).child("favorites").setValue(favRecipeList);
    }

    public static void addPrivateRecipe( Recipe recipe){
        recipeList.add(recipe);
        mDatabase.child("users").child(FBUser.getUid()).child("recipes").setValue(recipeList);
    }
    public static void readInventory( ){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + FBUser.getUid() + "/inventory");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    userInventory.add( dataSnapshot1.getValue(IngredientTag.class));
                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void writeInventory( IngredientTag item){
        if(!userInventory.contains(item)){
            userInventory.add(item);
        }
        mDatabase.child("users").child(FBUser.getUid()).child("inventory").setValue(userInventory);
    }

}
