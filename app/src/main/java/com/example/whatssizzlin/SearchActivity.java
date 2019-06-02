package com.example.whatssizzlin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ChipGroup searchChipGroup;
    private Button tagButton;
    private EditText tagText;

    private ChipGroup suggestedIngredients;
    private ChipGroup suggestedCultures;
    private ChipGroup suggestedCategories;

    private List<Tag> tags;

    private final int MAX_SUGGESTIONS = 20;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //chips
        searchChipGroup = (ChipGroup)findViewById(R.id.chipGroup);
        //tagButton = (Button)findViewById(R.id.addTag_btn);
        tagText = (EditText) findViewById(R.id.tag_txt);
        suggestedIngredients = (ChipGroup) findViewById(R.id.ingredient_grp);
        suggestedCultures = (ChipGroup) findViewById(R.id.culture_grp);
        suggestedCategories = (ChipGroup)findViewById(R.id.category_grp);

        tags = new ArrayList<>();
        getTags();
        setupSearchBar();


        /*tagButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addTagToChipGroup(tagText.getText().toString());
                        tagText.setText("");
                    }
                }
        );*/


    }

    private void setupSearchBar(){
        ((ImageButton)findViewById(R.id.search_btn)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            doSearchRequest();
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
                        System.out.println("text changes");
                        setSuggestionVisibility();
                        setTagSuggestionsByName(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

    }

    private void addTagToChipGroup(Tag tag){
        tagText.setText("");
        if(tag.getName().trim().isEmpty()) return;
        final Chip chip = new Chip(this);
        chip.setText(tag.getName());
        chip.setCloseIconVisible(true);
        chip.setChipBackgroundColor(getResources().getColorStateList(getTagColor(tag)));
        chip.setOnCloseIconClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchChipGroup.removeView(chip);
                    }
                }
        );
        searchChipGroup.addView(chip);

    }

    private void setSuggestionVisibility(){
        findViewById(R.id.tag_dropdown).setVisibility(tagText.hasFocus() &&
                !tagText.getText().toString().isEmpty()?View.VISIBLE:View.INVISIBLE);
        System.out.println("visibility "+findViewById(R.id.tag_dropdown).getVisibility());
    }

    private void getTags(){
        List<String> ingredients = Arrays.asList(getResources().getStringArray(R.array.ingredients));
        for(String ingredient : ingredients){
            tags.add(new Tag(ingredient, Tag.INGREDIENT));
        }

        List<String> cultures = Arrays.asList(getResources().getStringArray(R.array.culture));
        for(String culture : cultures){
            tags.add(new Tag(culture, Tag.CULTURE));
        }

        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.category));
        for(String category : categories){
            tags.add(new Tag(category, Tag.CATEGORY));
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
                TextView txt = new TextView(this);
                txt.setText(tag.getName());
                txt.setBackgroundColor(getResources().getColor(getTagColor(tag)));
                txt.setClickable(true);
                txt.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                System.out.println("tag"+tag.getName() + " pressed");
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

    private int getTagColor(Tag tag) {
        int tagColor;
        switch(tag.getType()){
            case Tag.INGREDIENT:
                tagColor = R.color.colorIngredientTag;
                break;
            case Tag.CULTURE:
                tagColor = R.color.colorCultureTag;
                break;
            case Tag.CATEGORY:
                tagColor = R.color.colorCategoryTag;
                break;
            default:
                tagColor = android.R.color.black;

        }
        return tagColor;
    }

    //https://stackoverflow.com/questions/6677969/tap-outside-edittext-to-lose-focus/36411427
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                Rect outRect2 = new Rect();
                v.getGlobalVisibleRect(outRect);
                findViewById(R.id.tag_dropdown).getGlobalVisibleRect(outRect2);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY()) && !outRect2.contains((int)event.getRawX(), (int)event.getRawY())){
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    private void doSearchRequest() throws JSONException {
        final TextView textView = findViewById(R.id.search_results_tmp);

        String url = "http://dummy.restapiexample.com/api/v1/create";
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
                        textView.setText("ERROR: Request failed");
                        error.printStackTrace();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }

    private JSONObject buildSearchJSON() throws JSONException {
        JSONObject search = new JSONObject();
        search.accumulate("name","test" + ((EditText)findViewById(R.id.tag_txt)).getText());
        search.accumulate("salary","123");
        search.accumulate("age","12");


        return search;
    }

}

