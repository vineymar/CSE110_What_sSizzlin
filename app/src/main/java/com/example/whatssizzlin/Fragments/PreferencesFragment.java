package com.example.whatssizzlin.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.whatssizzlin.Common.Common;
import com.example.whatssizzlin.MainActivity;
import com.example.whatssizzlin.R;
import com.example.whatssizzlin.Service.PicassonImageLoadingService;
import com.facebook.accountkit.AccountKit;
import com.google.firebase.firestore.CollectionReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends Fragment {

    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;

    AlertDialog dialog;

    private Unbinder unbinder;

    @BindView(R.id.layout_user_information)
    LinearLayout layout_user_information;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.txt_user_preference)
    TextView txt_user_preference;
    @BindView(R.id.btn_edit)
    Button btn_edit;
    @BindView(R.id.btn_logout)
    Button btn_logout;


    public PreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        unbinder = ButterKnife.bind(this, view);

        Slider.init(new PicassonImageLoadingService());

        if (AccountKit.getCurrentAccessToken() != null) {
            setUserInformation();
        }

        return view;
    }

    @OnClick(R.id.btn_edit)
    void editUserInformation() {

    }

    @OnClick(R.id.btn_logout)
    void logoutUser() {
        AccountKit.logOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }



    private void setUserInformation() {
        layout_user_information.setVisibility(View.VISIBLE);
        txt_user_name.setText(Common.currenUser.getName());
        txt_user_preference.setText(Common.currenUser.getPreference());
    }

}