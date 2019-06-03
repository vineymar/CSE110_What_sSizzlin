package com.example.whatssizzlin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PantryFragment extends Fragment  {

    SparseBooleanArray sparseBooleanArray ;
    final ArrayList<String> ingredientItemList = new ArrayList<String>();
    String[] ItemList;
    ArrayList<String>itemsList;
    ArrayAdapter<String> adapter;
    EditText itemText;
    Button addBtn;
    ListView lv;
    private String my_sel_items;

    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        my_sel_items=new String();
        View view = inflater.inflate(R.layout.fragment_pantry2, container, false);

        /*Where we add and show our list of ingredients*/

        final ListView mIngredientList = view.findViewById(R.id.ingredientListView);

        /*Adapter for Adding Ingredients*/
        final ArrayAdapter<String> mListViewAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_multiple_choice,
                //R.id.listIngredientView,
                ingredientItemList
        );
        /*More adapter*/
        mIngredientList.setAdapter(mListViewAdapter);
        mIngredientList.getAdapter();

        final EditText editText = view.findViewById(R.id.ingredient_id);
        Button btnAddIngredient = view.findViewById(R.id.btnAdd);
        btnAddIngredient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Please Enter an Ingredient", Toast.LENGTH_SHORT).show();
                }else{
                    ingredientItemList.add(editText.getText().toString());
                    editText.setText("");
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
        }); /*Adapter for Adding Ingredients*/
        /*Check Box Adapter*/
        mIngredientList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mIngredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3)
            {
                my_sel_items=new String("Selected Items");
                SparseBooleanArray a = mIngredientList.getCheckedItemPositions();

                for(int i = 0; i < ingredientItemList.size() ; i++)
                {
                    if (a.valueAt(i))
                    {

                        my_sel_items = my_sel_items + ","
                                + (String) mIngredientList.getAdapter().getItem(i);
                    }
                }
                Log.v("values",my_sel_items);
            }
        });

        /*Check Box Adapter*/

        return view;
    }
}
