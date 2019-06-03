package com.m8.whatssizzlin.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.m8.whatssizzlin.Common.Common;
import com.m8.whatssizzlin.CookBookActivity;
import com.m8.whatssizzlin.EditActivity;
import com.m8.whatssizzlin.HomeActivity;
import com.m8.whatssizzlin.MainActivity;
import com.m8.whatssizzlin.Model.User;
import com.m8.whatssizzlin.R;
import com.m8.whatssizzlin.Service.PicassonImageLoadingService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.layout_user_information)
    LinearLayout layout_user_information;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.txt_user_preference)
    TextView txt_user_preference;
    @BindView(R.id.btn_logout)
    Button btn_logout;
    @OnClick(R.id.card_edit)
    void editInfo() {
        startActivity(new Intent(getActivity(), EditActivity.class));
    }
    @OnClick(R.id.card_cookbook)
    void cookBook() {
        startActivity(new Intent(getActivity(), CookBookActivity.class));
    }



    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        Slider.init(new PicassonImageLoadingService());

        if (AccountKit.getCurrentAccessToken() != null) {
            setUserInformation();
        }

        return view;
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
