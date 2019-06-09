package com.example.whatssizzlin;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;
import static java.lang.Integer.parseInt;

public class ViewRecipe extends Fragment {

    Recipe rec;
    HomeFragment home;
    SearchFragment search;
    Uri uri;
    TextView name;
    String mName;
    public final List<String> s = new ArrayList<String>();

    public ViewRecipe(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_view_recipe, container, false);

        name = v.findViewById(R.id.recipeName);

        name.setText(getArguments().getString("name"));
        TextView ing = v.findViewById(R.id.ingredientsList);
        ing.setText(getArguments().getString("ingredients"));
        Log.d("in", getArguments().getString("ingredients"));
        TextView inst = v.findViewById(R.id.instructionsList);
        inst.setText(getArguments().getString("instructions"));

        final ImageView imageView = v.findViewById(R.id.imageView);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child("mealImages/" + getArguments().getString("id") + ".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

        final ImageButton btnAddIngredient = v.findViewById(R.id.favorite_off);
        btnAddIngredient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Recipe Added", Toast.LENGTH_LONG).show();
                if(home == null) return;
                home.mRecIDs.add(getArguments().getString("id"));
                //Toast.makeText(getContext(), getArguments().getString("id"), Toast.LENGTH_SHORT).show();
                int recipeID = Integer.parseInt(getArguments().getString("id"));
                UserDB.addFav( recipeID);
                s.add(getArguments().getString("id"));
                home.addFavRecipe(s, 0 );

                //Makes the button unclickable
                btnAddIngredient.setPressed(true);
                btnAddIngredient.setClickable(false);
                btnAddIngredient.setBackgroundColor(Color.TRANSPARENT);
            }
        });



        return v;

    }
/*
    public void setID(final String ID){
        FirebaseDatabase.getInstance().getReference().child("meals").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rec = dataSnapshot.getValue(Recipe.class);
                // Create a storage reference from our app
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference sr = storage.getReference();
                StorageReference pic = sr.child("mealImages/" + ID + ".jpg");
                pic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        rec.img_url = uri.toString();
                        configureScreen();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
    private void configureScreen(){

        //final TextView name = this.view.findViewById(R.id.recipeName);
        name.setText(rec.name);
    }
}
