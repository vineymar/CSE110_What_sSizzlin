package com.example.whatssizzlin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PantryFragment extends Fragment  {

    public PantryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pantry2, container, false);

        String[] ingredientItemList = {
                        "watermelon",
                        "Bananas",
                        "Pickles" };

        ListView mIngredientList = view.findViewById(R.id.ingredientListView);

        ArrayAdapter<String> mListViewAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                ingredientItemList
        );

        mIngredientList.setAdapter(mListViewAdapter);
        mIngredientList.getAdapter();

//        editTxt = (EditText) rootView.findViewById(R.id.ingredient_id);
//        btnAddIngredient = (Button) rootView.findViewById(R.id.btnAdd);
//        list = (ListView) rootView.findViewById(R.id.ingredientList);
//        btnAddIngredient.setOnClickListener(this);
//        arrayList = new ArrayList<String>();
//
//        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
//        // and the array that contains the data
//        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
//
//        // Here, you set the data in your ListView
//        list.setAdapter(adapter);

        return view;
    }



//    @Override
//    public void onClick(View v) {
//// this line adds the data of your EditText and puts in your array
//        arrayList.add(editTxt.getText().toString());
//        // next thing you have to do is check if your adapter has changed
//        adapter.notifyDataSetChanged();
//    }
}
