package com.example.whatssizzlin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenceFragment extends Fragment {

    private HomeFragment homeFragment;

    public PreferenceFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preference, container, false);


        //Create Recipe Button
        Button btnCreateRecipe = view.findViewById(R.id.button_createRecipe);
        btnCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new CreateRecipeFragment());
                fragmentTransaction.commit();
            }
        });


        Button btnLogout_Click=view.findViewById(R.id.btn_logout);
        btnLogout_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                Toast.makeText(getContext(), "You Are Now Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
