package com.example.whatssizzlin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRecipeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Add Ingredients
    private LinearLayout mLayout_ing;
    private EditText mEditText_ing;
    private Button mButton_ing;
    private Button mButton_addImage;
    TextInputEditText ingInp;
    TextInputEditText qInp;
    CookBookFragment cb;
    ListView mIngs;
    ArrayList<String> ings;
    ArrayList<String> quants;
    ArrayList<String> inps;
    ArrayAdapter<String> arrayAdapter;
    private Uri filePath;
    private static final int GET_FROM_GALLERY = 3;
    CookBookFragment cb;
    //

    public CreateRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRecipeFragment.
     */

    public static CreateRecipeFragment newInstance(String param1, String param2) {
        CreateRecipeFragment fragment = new CreateRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.d("CRE", FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        //Add ingredients
        //setContentView(R.layout.fragment_create_recipe);

        ingInp = (TextInputEditText) view.findViewById(R.id.mIng);
        qInp = (TextInputEditText) view.findViewById(R.id.mQuant);
        mIngs = (ListView) view.findViewById(R.id.listIngredients);
        mButton_ing = (Button) view.findViewById(R.id.button_ing);
        ings = new ArrayList<>();
        quants = new ArrayList<>();
        inps = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, inps);
        mIngs.setAdapter(arrayAdapter);
        mButton_addImage = view.findViewById(R.id.button_addImage);
        
        mButton_addImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }


        });


        
        mButton_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ns = qInp.getText().toString() + " " + ingInp.getText().toString();
                ings.add(ingInp.getText().toString());
                quants.add(qInp.getText().toString());
                inps.add(ns);

                arrayAdapter.notifyDataSetChanged();

            }
        });

        final TextInputEditText t = view.findViewById(R.id.recipeInstructions);
        final TextInputEditText rn = view.findViewById(R.id.recipeName);
        final EditText rd = view.findViewById(R.id.recipeDescription);
        final EditText te = view.findViewById(R.id.time_estimate);
        final EditText servs = view.findViewById(R.id.recipeServings);

        Button c = (Button) view.findViewById(R.id.recipeCancel);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new PreferenceFragment());
                fragmentTransaction.commit();
            }
        });

        Button b = (Button) view.findViewById(R.id.submit_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity h = (HomeActivity) getActivity();
                if(h.getBitmap() == null){
                    Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(rn.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please enter the recipe name", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(ings.size() == 0){
                    Toast.makeText(getContext(), "Please enter the ingredients", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(t.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please enter the instructions", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(rd.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please enter the recipe description", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(te.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please enter the time estimate", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(servs.getText().toString().equals("") || servs.getText().toString() == null){
                    Toast.makeText(getContext(), "Please enter the servings", Toast.LENGTH_LONG).show();
                    return;
                }
                final Recipe r = new Recipe();
                r.name = rn.getText().toString();
                r.nutrition = new HashMap<>();
                r.method = new ArrayList<>();
                r.method.add(t.getText().toString());
                r.servings = servs.getText().toString();
                //Log.d("CRE", r.servings);
                r.servingTag = Integer.parseInt(r.servings);
                r.description = rd.getText().toString();
                r.author = RegistrationActivity.name;
                r.difficulty = new ArrayList<>();
                r.ingredients = inps;
                r.time = new ArrayList<>();
                r.ingredientTags = ings;
                r.timeTag = Integer.parseInt(te.getText().toString());

                final String key = FirebaseDatabase.getInstance().getReference().child("meals").push().getKey();
                r.id = key;


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap b = h.getBitmap();
                b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                StorageReference imgRef = FirebaseStorage.getInstance().getReference().child("mealImages/"+key+".jpg");
                UploadTask uploadTask = imgRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), "Error uploading image", Toast.LENGTH_LONG).show();
                        return;
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FirebaseDatabase.getInstance().getReference().child("meals").child(key).setValue(r);

                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cookbook").child(key).setValue(r.id);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_frame, cb);
                        fragmentTransaction.commit();
                        ArrayList<String> al = new ArrayList<>();
                        al.add(r.id);
                        cb.addRecRecipe(al, 0);
                        Toast.makeText(getContext(), "Recipe submitted", Toast.LENGTH_LONG).show();


                    }
                });
            }
        });
       // mButton_ing.setOnClickListener(ing_onClick());
       // TextView textView = new TextView(Objects.requireNonNull(getActivity()).getApplicationContext()); //this
       // textView.setText("New text");

       // Recipe recipe = new Recipe(author, description, difficulty, ingredients, method, name, nutrition, servings. time);
        inflater.inflate(R.layout.fragment_create_recipe, container, false);
        return view;
    }



/*
    private View.OnClickListener ing_onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout_ing.addView(createNewTextView(mEditText_ing.getText().toString()));
            }
        };
    }

    private TextView createNewTextView(String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(Objects.requireNonNull(getActivity()).getApplicationContext());  //this
        textView.setLayoutParams(lparams);
        textView.setText("New text: " + text);
        return textView;
    }

    */

/*

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
