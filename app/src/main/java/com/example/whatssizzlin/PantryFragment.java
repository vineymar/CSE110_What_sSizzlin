package com.example.whatssizzlin;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.chip.ChipGroup;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
////

/**
 * A simple {@link Fragment} subclass.
 */
public class PantryFragment extends Fragment  {

    SparseBooleanArray sparseBooleanArray ;
    final ArrayList<String> ingredientItemList = new ArrayList<String>();
    ListView mIngredientList;
    ArrayAdapter<String> mListViewAdapter;
    String[] ItemList;
    ArrayList<String>itemsList;
    ArrayAdapter<String> adapter;
    EditText itemText;
    Button addBtn;
    ListView lv;
    private String my_sel_items;


    /*Search bar stuff*/
    private static final int SET_FILTER = 100;
    private TextView mTextMessage;
    private ChipGroup searchChipGroup;
    private Button tagButton;
    private EditText tagText;

    private ChipGroup suggestedIngredients;
    private ChipGroup suggestedCultures;
    private ChipGroup suggestedCategories;

    private int min_serving = 0;
    private int max_serving = 21;
    private int min_time = 0;
    private int max_time = 361;
    private LinearLayout tag_dropdown;


    private List<Tag> tags;
    private View view;
    private Activity activity;

    private final int MAX_SUGGESTIONS = 20;
    /*Search bar stuff*/


    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_pantry2, container, false);

        final Button deleteItems = (Button) view.findViewById(R.id.pantryRemove);
        tagText = (EditText) view.findViewById(R.id.tag_txt);
        suggestedIngredients = (ChipGroup) view.findViewById(R.id.ingredient_grp);
        suggestedCultures = (ChipGroup) view.findViewById(R.id.culture_grp);
        suggestedCategories = (ChipGroup)view.findViewById(R.id.category_grp);
        tags = new ArrayList<>();

        getTags();
        my_sel_items=new String();

        /*Where we add and show our list of ingredients*/

        mIngredientList = view.findViewById(R.id.ingredientListView);
        tag_dropdown = view.findViewById(R.id.tag_dropdown);

        /*Adapter for Adding Ingredients*/
        mListViewAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_multiple_choice,
                //R.id.listIngredientView,
                ingredientItemList
        );
        /*More adapter*/
        mIngredientList.setAdapter(mListViewAdapter);
        mIngredientList.getAdapter();

        Button b = view.findViewById(R.id.pantryClear);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray a = mIngredientList.getCheckedItemPositions();
                int j = 0;
                while(ingredientItemList.size() > 0)
                {
                        ingredientItemList.remove(0);
                }
                mListViewAdapter.notifyDataSetChanged();
            }
        });


//        final EditText editText = view.findViewById(R.id.ingredient_id);
//        Button btnAddIngredient = view.findViewById(R.id.btnAdd);
//        btnAddIngredient.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(editText.getText().toString().matches("")) {
//                    Toast.makeText(getContext(), "Please Enter an Ingredient", Toast.LENGTH_SHORT).show();
//                }else{
//                    //editText.setTextColor(getResources().getColor(android.R.color.white));
//                    ingredientItemList.add(editText.getText().toString());
//                    editText.setText("");
//
//                    mListViewAdapter.notifyDataSetChanged();
//                }
//            }
//        }); /*Adapter for Adding Ingredients*/

        /*Check Box Adapter*/

        deleteItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SparseBooleanArray a = mIngredientList.getCheckedItemPositions();
                int j = 0;
                for(int i = 0; i < a.size() ; i++)
                {
                    Log.d("PAN", i+ " " + j + " " + a);
                    if (a.valueAt(i))
                    {
                        ingredientItemList.remove(j);
                    }
                    else{
                        j++;
                    }
                }

                mListViewAdapter.notifyDataSetChanged();
                for(int i = 0; i < ingredientItemList.size(); i++){
                    mIngredientList.setItemChecked(i, false);
                }
                deleteItems.setVisibility(View.INVISIBLE);
            }
        });

        mIngredientList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mIngredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3)
            {
                my_sel_items=new String("Selected Items");
                deleteItems.setVisibility(View.INVISIBLE);
                mIngredientList.setBackgroundResource(R.drawable.backgroundcolor);
                SparseBooleanArray a = mIngredientList.getCheckedItemPositions();

                for(int i = 0; i < ingredientItemList.size() ; i++)
                {
                    if (a.valueAt(i))
                    {   UserDB.deleteInventory(  new IngredientTag((String)mIngredientList.getAdapter().getItem(i), 1));
                        deleteItems.setVisibility(View.VISIBLE);
                        my_sel_items = my_sel_items + ","
                                + (String) mIngredientList.getAdapter().getItem(i);
                    }
                }
                Log.v("values",my_sel_items);
            }
        });

        /*Check Box Adapter*/

        setupSearchBar();
        setupTouchListener();

        return view;
    }


    /*Pantry Search with SearchImplementationTags<>*/
    private void setupSearchBar(){
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

    private void setSuggestionVisibility(){
        Log.d("RES", tagText.hasFocus() + " " + tagText.getText().toString());
        view.findViewById(R.id.tag_dropdown).setVisibility(tagText.hasFocus() &&
                !tagText.getText().toString().isEmpty()?View.VISIBLE:View.INVISIBLE);

    }

    private void getTags(){
        List<String> ingredients = Arrays.asList(getResources().getStringArray(R.array.ingredients));
        int i = 0;
        for(String ingredient : ingredients){
            tags.add(new IngredientTag(ingredient,i++));
            UserDB.writeInventory(new IngredientTag(ingredient,i++));
        }
//        List<String> cultures = Arrays.asList(getResources().getStringArray(R.array.culture));
//        for(String culture : cultures){
//            tags.add(new CultureTag(culture,i++));
//        }
//
//        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.category));
//        for(String category : categories){
//            tags.add(new CategoryTag(category, i++));
//        }

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
                final TextView txt = new TextView(view.getContext());
                txt.setText(tag.getName());
                txt.setBackgroundColor(getResources().getColor(tag.getTagColor()));
                txt.setClickable(true);
                txt.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //addTagToChipGroup(tag);
                                ingredientItemList.add(txt.getText().toString());
                                tag_dropdown.setVisibility(View.INVISIBLE);
                                tagText.setText("");
                                mListViewAdapter.notifyDataSetChanged();
                                mIngredientList.setItemChecked(ingredientItemList.size() - 1, false);
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

    private void setupTouchListener(){
        view.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if(activity == null){
                                return false;
                            }
                            View v = activity.getCurrentFocus();
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


}
