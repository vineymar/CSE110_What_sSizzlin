package com.m8.whatssizzlin;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.m8.whatssizzlin.Common.Common;
import com.m8.whatssizzlin.Fragments.UserFragment;
import com.m8.whatssizzlin.Model.User;

import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class EditActivity extends AppCompatActivity {
    CollectionReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(EditActivity.this);

        userRef = FirebaseFirestore.getInstance().collection("User");

        if (getIntent() != null) {
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if (true) {
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {
                        if (account != null) {
                            DocumentReference currentUser = userRef.document(account.getPhoneNumber().toString());
                            currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot userSnapshot = task.getResult();
                                        if (!userSnapshot.exists()) {
                                            showUpdateDialog(account.getPhoneNumber().toString());
                                        }
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Toast.makeText(EditActivity.this, ""+accountKitError.getErrorType().
                                getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void showUpdateDialog(final String phoneNumber) {

        View sheetView = getLayoutInflater().inflate(R.layout.layout_update_information, null);

        Button btn_update = (Button)sheetView.findViewById(R.id.btn_update);
        final TextInputEditText edt_name = (TextInputEditText)sheetView.findViewById(R.id.edit_name);
        final TextInputEditText edt_address = (TextInputEditText)sheetView.findViewById(R.id.edit_address);
        final TextInputEditText edt_email = (TextInputEditText)sheetView.findViewById(R.id.edit_email);
        final TextInputEditText edt_preference = (TextInputEditText)sheetView.findViewById(R.id.edit_preference);
        final TextInputEditText edt_two = (TextInputEditText)sheetView.findViewById(R.id.edit_two);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = new User(edt_name.getText().toString(), edt_address.getText().toString(),
                        edt_email.getText().toString(), edt_preference.getText().toString(),
                        edt_two.getText().toString(), phoneNumber);

                userRef.document(phoneNumber).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Common.currenUser = user;
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);

                        Toast.makeText(EditActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
