package com.example.whatssizzlin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*            //Facebook stuff               */

public class PhoneNumberActivity extends AppCompatActivity {



    private final static int APP_REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
//        if (accessToken != null) {
//
//        } else { //we're already logged in
//            phoneLogin();
//            ButterKnife.bind(PhoneNumberActivity.this);
//            setContentView(R.layout.activity_home);
//        }
        phoneLogin();
    }

    public void phoneLogin() {
            final Intent intent = new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(
                            LoginType.PHONE,
                            AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
            // ... perform additional configuration ...
            intent.putExtra(
                    AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                    configurationBuilder.build());
            startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) {          // confirm that this response matches your request

            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null)
            {
                Toast.makeText(this, ""+loginResult.getError().getErrorType().getMessage(),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else if (loginResult.wasCancelled())
            {
                Toast.makeText(this, toastMessage = "Login Cancelled",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
            else
            {
                // Surface the result to your user in an appropriate way.
                    Toast.makeText(this, toastMessage="Welcome to What's Sizzlin!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, HomeActivity.class));
            }
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo
                    ("com.example.whatssizzlin", PackageManager.GET_SIGNATURES);

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
