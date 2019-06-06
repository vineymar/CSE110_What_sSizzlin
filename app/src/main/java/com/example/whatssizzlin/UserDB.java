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
    private static DatabaseReference mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference();
    public static FirebaseUser FBUser = FirebaseAuth.getInstance().getCurrentUser();

    public static void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
        UserPref preferences = new UserPref();
        mDatabase.child("users").child(userId).child("preferences").setValue(preferences);
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



}
