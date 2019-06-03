package com.example.whatssizzlin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;


public class FilterActivity extends Activity {
    private final static double WIDTH_RATIO = 0.7;
    private final static double HEIGHT_RATIO = 0.6;
    private static final int MAX_HRS = 6;

    private int min_serving = 0;
    private int max_serving = 21;
    private int min_time = 0;
    private int max_time = 361;

    private CrystalRangeSeekbar servingBar;
    private CrystalRangeSeekbar timeBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int)(dm.widthPixels * WIDTH_RATIO), (int)(dm.heightPixels * HEIGHT_RATIO) );

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;
        getWindow().setAttributes(params);

        setupRangeValues();

        setupServingsRange(min_serving,max_serving);
        setupTimeRange(min_time,max_time);
        setupApplyButton();


    }
    private void setupRangeValues(){
        min_serving = getIntent().getIntExtra("min_serving",0);
        max_serving = getIntent().getIntExtra("max_serving",21);
        min_time = getIntent().getIntExtra("min_time",0);
        max_time = getIntent().getIntExtra("max_time",361);
        System.out.println("SETTING UP RANGE VALUES");
        System.out.println("mins"+min_serving);
        System.out.println("maxs"+max_serving);
        System.out.println("mint"+min_time);
        System.out.println("maxt"+max_time);

    }

    private void setupApplyButton(){
        ((Button)findViewById(R.id.filter_apply_btn)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishActivity();
                    }
                }
        );

    }
    private void finishActivity(){
        Intent i = new Intent();
        i.putExtra("min_time",min_time);
        i.putExtra("max_time",max_time);
        i.putExtra("min_serving",min_serving);
        i.putExtra("max_serving",max_serving);
        setResult(RESULT_OK,i);
        finish();
    }

    private void setupServingsRange(int min, int max){
        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.serving_rng_bar);
        servingBar = rangeSeekbar;
// get min and max text view
        final TextView tvMin = (TextView) findViewById(R.id.serving_min_txt);
        final TextView tvMax = (TextView) findViewById(R.id.serving_max_txt);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue).equals("21")?"20+":String.valueOf(maxValue));
                min_serving = numberToInt(minValue);
                max_serving = numberToInt(maxValue);
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

        rangeSeekbar.setMinStartValue(min);
        rangeSeekbar.setMaxStartValue(max);
        rangeSeekbar.apply();

    }
    private void setupTimeRange(int min, int max){
        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.time_rng_bar);
        timeBar = rangeSeekbar;
// get min and max text view
        final TextView tvMin = (TextView) findViewById(R.id.time_min_txt);
        final TextView tvMax = (TextView) findViewById(R.id.time_max_txt);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                tvMin.setText(parseTimeValueToString(minValue));
                tvMax.setText(parseTimeValueToString(maxValue));
                min_time = numberToInt(minValue);
                max_time = numberToInt(maxValue);
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

        rangeSeekbar.setMinStartValue(min);
        rangeSeekbar.setMaxStartValue(max);
        rangeSeekbar.apply();

    }

    private int numberToInt(Number n) {
        return Integer.parseInt(String.valueOf(n));
    }
    private String parseTimeValueToString(Number val){
        String result = "";
        int num = numberToInt(val);
        int hrs = num/60;
        int mins = num%60;

        if(hrs == MAX_HRS && mins > 0){
            result = MAX_HRS + "h+";
            return result;
        }

        if(hrs != 0){
            result += hrs+"h ";
        }
        result += mins + "m";
        return result;
    }
}
