package com.example.whatssizzlin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRecipeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Add Ingredients
    private LinearLayout mLayout_ing;
    private EditText mEditText_ing;
    private Button mButton_ing;
    TextInputEditText ingInp;
    TextInputEditText qInp;
    ListView mIngs;
    ArrayList<String> ings;
    ArrayList<String> quants;
    ArrayList<String> inps;
    ArrayAdapter<String> arrayAdapter;
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
    // TODO: Rename and change types and number of parameters
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

        String author;
        String description;
        List<String> difficulty;
        List<String> ingredients;
        List<String> method;
        String name;
        Map<String, String> nutrition;
        String servings;
        List<Map<String, Map<String, String>>> time;


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


        Button b = (Button) view.findViewById(R.id.submit_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe r = new Recipe();
                r.nutrition = new HashMap<>();
                r.method = new ArrayList<>();
                r.method.add(t.getText().toString());
                Log.d("CRE", r.method.get(0));
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
    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
