package com.example.whatssizzlin;
/*---------------------------Imports-------------------------------*/

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
/*---------------------------Imports-------------------------------*/

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView mBtmView;
    private int mMenuId;
    /*OnCreate method*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBtmView = (BottomNavigationView) findViewById(R.id.BottomNavigation);
        mBtmView.setOnNavigationItemSelectedListener(this);
        mBtmView.getMenu().findItem(R.id.NavigationHome).setChecked(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        mMenuId = item.getItemId();
        for (int i = 0; i < mBtmView.getMenu().size(); i++) {
            MenuItem menuItem = mBtmView.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }
        switch (item.getItemId()) {
            case R.id.NavigationHome:
                return true;
            case R.id.NavigationSearch:
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                return true;
            case R.id.NavigationPantry:
                startActivity(new Intent(HomeActivity.this, PantryActivity.class));
                return true;
            case R.id.NavigationPreferences:
                startActivity(new Intent(HomeActivity.this, PreferencesActivity.class));
                return true;
            default:
                return onNavigationItemSelected(item);
        }
    }
//    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater = getMenuInflater();
////        inflater.inflate(R.menu.navigation, menu);
////        return true;
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle item selection
////        switch (item.getItemId()) {
////            case R.id.NavigationHome:
////                return true;
////            case R.id.NavigationSearch:
////                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
////                return true;
////            case R.id.NavigationPantry:
////                startActivity(new Intent(HomeActivity.this, PantryActivity.class));
////                return true;
////            case R.id.NavigationPreferences:
////                startActivity(new Intent(HomeActivity.this, PreferencesActivity.class));
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
////    }
}
