package com.example.whatssizzlin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PantryFragment extends Fragment  {
    final ArrayList<String> ingredientItemList = new ArrayList<String>();
    public PantryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pantry2, container, false);

        /*Where we add and show our list of ingredients*/

        ListView mIngredientList = view.findViewById(R.id.ingredientListView);

        /*Adapter*/
        final ArrayAdapter<String> mListViewAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                //R.id.listIngredientView,
                ingredientItemList
        );
        /*More adapter*/
        mIngredientList.setAdapter(mListViewAdapter);
        mIngredientList.getAdapter();

        /* Find add ingredient button, and text field.
         * need to add a reset field after button is pressed and added.
         * need to update the UI
         *
         * */
        final EditText editText = view.findViewById(R.id.ingredient_id);
        Button btnAddIngredient = view.findViewById(R.id.btnAdd);
        btnAddIngredient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ingredientItemList.add(editText.getText().toString());
                mListViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

}
