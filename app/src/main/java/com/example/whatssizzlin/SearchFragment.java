package com.example.whatssizzlin;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final int SET_FILTER = 100;
    private TextView mTextMessage;
    private ChipGroup searchChipGroup;
    private List<Tag> selectedTagList;

    private Button tagButton;
    private EditText tagText;

    private ChipGroup suggestedIngredients;
    private ChipGroup suggestedCultures;
    private ChipGroup suggestedCategories;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mTimes = new ArrayList<>();
    private ArrayList<Recipe> mRecs = new ArrayList<>();

    private int min_serving = 0;
    private int max_serving = 21;
    private int min_time = 0;
    private int max_time = 361;

    private List<Tag> tags;
    private View view;
    private Activity activity;

    private final int MAX_SUGGESTIONS = 20;

    /*Vertical View*/
    RecyclerViewAdapter adapterSearch;
    private ArrayList<String> mSearchIDs;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search2, container, false);
        activity = getActivity();

        selectedTagList = new ArrayList<Tag>();
        searchChipGroup = (ChipGroup)view.findViewById(R.id.chipGroup);
        tagText = (EditText) view.findViewById(R.id.tag_txt);
        suggestedIngredients = (ChipGroup) view.findViewById(R.id.ingredient_grp);
        suggestedCultures = (ChipGroup) view.findViewById(R.id.culture_grp);
        suggestedCategories = (ChipGroup)view.findViewById(R.id.category_grp);
        tags = new ArrayList<>();


        getSearchImages();
        /*For RecyclerView*/
        LinearLayoutManager layoutRecManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerSearchView = view.findViewById(R.id.recycleSearchView);
        recyclerSearchView.setLayoutManager(layoutRecManager);

                                                    /*From Recipes, grab arraylist names, urls, times, recipes */
        adapterSearch = new RecyclerViewAdapter(mNames, mImageUrls, mTimes, mRecs, this.getContext(), null);

        recyclerSearchView.setAdapter(adapterSearch);

        /*Tag stuff*/
        getTags();
        setupTouchListener();
        setupSearchBar();
        System.out.println("SETUP SEARCHVIEW");
        return view;
    }


    private void getSearchImages(){
        //Log.d(TAG, "Inside getImages: ");
        mSearchIDs = new ArrayList<String>() {
            {
//               //Do stuff
            }
        };
        /*This is what hayden had below. */
       // addSearchRecipe(mRecIDs, 0);
    }
//    private void addRecRecipe(final List<String> ID, final int index){
//        FirebaseDatabase.getInstance().getReference().child("meals").child(ID.get(index)).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Recipe r = dataSnapshot.getValue(Recipe.class);
//                //mRecImageUrls.add("htpps:"+r.img_url);
//                mRecNames.add(r.name);
//                r.id = ID.get(index);
//                mRecRecs.add(r);
//                // Create a storage reference from our app
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference sr = storage.getReference();
//                StorageReference pic = sr.child("mealImages/" + ID.get(index) + ".jpg");
//                pic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        mRecImageUrls.add(uri.toString());
//                        if(index == (ID.size() - 1)){
//                            adapterRecommended.notifyDataSetChanged();
//                        }
//                        else{
//                            addRecRecipe(ID, index + 1);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                    }
//                });
//                mRecTimes.add(r.time.get(0).get("prep").get("mins"));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void addRecipeToResults(Recipe r){
        mNames.add(r.name);
        mImageUrls.add(r.img_url);
        mTimes.add("60h");//r.getStringTime());
        mRecs.add(r);
        adapterSearch.notifyDataSetChanged();

    }




    private void setupTouchListener(){
        view.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View _view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            Activity a = (Activity) view.getContext();
                            View v = a.getCurrentFocus();
                            if ( v instanceof EditText) {
                                Rect outRect = new Rect();
                                Rect outRect2 = new Rect();
                                v.getGlobalVisibleRect(outRect);
                                view.findViewById(R.id.tag_dropdown).getGlobalVisibleRect(outRect2);
                                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY()) && !outRect2.contains((int)event.getRawX(), (int)event.getRawY())){
                                    v.clearFocus();
                                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                }
                            }
                        }
                        return false;
                    }
                }
        );

    }
    private void setupSearchBar(){
        ((Button)view.findViewById(R.id.filter_btn)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), FilterActivity.class);
                        i.putExtra("min_time",min_time);
                        i.putExtra("max_time",max_time);
                        i.putExtra("min_serving",min_serving);
                        i.putExtra("max_serving",max_serving);
                        startActivityForResult(i,SET_FILTER);
                    }
                }
        );

        ((ImageButton)view.findViewById(R.id.search_btn)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("search pressed");
                        try {
                            doSearchRequest();
                            tagText.clearFocus();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        tagText.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        setSuggestionVisibility();
                    }
                }
        );
        tagText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        setSuggestionVisibility();
                        setTagSuggestionsByName(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

    }

    private void addTagToChipGroup(final Tag tag){
        if(selectedTagList.contains(tag))return;
        tagText.setText("");
        final Chip chip = new Chip(view.getContext());
        selectedTagList.add(tag);
        chip.setText(tag.getName());
        chip.setCloseIconVisible(true);
        chip.setChipBackgroundColor(getResources().getColorStateList(tag.getTagColor()));
        chip.setOnCloseIconClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchChipGroup.removeView(chip);
                        selectedTagList.remove(tag);
                    }
                }
        );
        searchChipGroup.addView(chip);

    }

    private void setSuggestionVisibility(){
        view.findViewById(R.id.tag_dropdown).setVisibility(tagText.hasFocus() &&
                !tagText.getText().toString().isEmpty()?View.VISIBLE:View.INVISIBLE);

    }

    private void getTags(){
        List<String> ingredients = Arrays.asList(getResources().getStringArray(R.array.ingredients));
        int i = 0;
        for(String ingredient : ingredients){
            tags.add(new IngredientTag(ingredient,i++));
        }

        List<String> cultures = Arrays.asList(getResources().getStringArray(R.array.culture));
        for(String culture : cultures){
            tags.add(new CultureTag(culture,i++));
        }

        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.category));
        for(String category : categories){
            tags.add(new CategoryTag(category, i++));
        }

    }

    private void setTagSuggestionsByName(String name){

        int count = 0;
        suggestedIngredients.removeAllViews();
        suggestedCultures.removeAllViews();
        suggestedCategories.removeAllViews();

        if(name.isEmpty()) return;


        for(final Tag tag : tags){
            if(count > MAX_SUGGESTIONS)break;
            if(tag.getName().startsWith(name) || tag.getName().contains(" "+name)){
                TextView txt = new TextView(view.getContext());
                txt.setText(tag.getName());
                txt.setBackgroundColor(getResources().getColor(tag.getTagColor()));
                txt.setClickable(true);
                txt.setFocusableInTouchMode(false);
                txt.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addTagToChipGroup(tag);
                                //tagText.clearFocus();
                            }
                        }
                );
                switch (tag.getType()){
                    case Tag.INGREDIENT:
                        suggestedIngredients.addView(txt);
                        break;
                    case Tag.CULTURE:
                        suggestedCultures.addView(txt);
                        break;
                    case Tag.CATEGORY:
                        suggestedCategories.addView(txt);
                        break;
                }
                count++;
            }
        }

    }


    private void doSearchRequest() throws JSONException {
        final TextView textView = view.findViewById(R.id.search_results_tmp);

        String url = "http://54.185.10.110:8080/";
        JSONObject jsonRequest = buildSearchJSON();
        System.out.println("doing search request");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonRequest, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("received response");
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("ERROR: Request failed\n" + error.getMessage());

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(view.getContext()).addToRequestQueue(jsonObjectRequest);


    }

    private JSONObject buildSearchJSON() throws JSONException {
        JSONObject search = new JSONObject();
        String query = "";
        for(Tag tag:selectedTagList){
            query += tag.getName() + " ";
        }
        query += getSearchName();

        System.out.println("query:"+query);
        search.accumulate("query",query);

        return search;
    }

    private String getSearchName(){
        return tagText.getText().toString();
    }
    private String[] getSelectedTagsStringArray(){
        String[] tagStringArray = new String[selectedTagList.size()];
        for(int i = 0 ; i < selectedTagList.size() ; i++){
            tagStringArray[i] = selectedTagList.get(i).getName();
        }
        return tagStringArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SET_FILTER) {
            if (resultCode == Activity.RESULT_OK) {
                min_serving = data.getIntExtra("min_serving",0);
                max_serving = data.getIntExtra("max_serving",21);
                min_time = data.getIntExtra("min_time",0);
                max_time = data.getIntExtra("max_time",361);

            }
        }
    }

}
