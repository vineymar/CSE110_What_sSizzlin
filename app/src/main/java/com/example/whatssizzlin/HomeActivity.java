package com.example.whatssizzlin;
/*---------------------------Imports-------------------------------*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
/*---------------------------Imports-------------------------------*/

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity: Imaging";
    /*For our images into our view*/
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    /*For our images into our view*/

    /*OnCreate method*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getImages();
    }

    private void getImages(){
        Log.d(TAG, "Inside getImages: ");

        /* Need a way to pull image urls and image_names
         * mImageUrls.add(Image URL stuff)
         * mNames.add(Image URL stuff)
         * */
        mImageUrls.add("https://www.onceuponachef.com/images/2017/10/How-To-Make-Hard-Boiled-Eggs-760x516.jpg");
        mNames.add("Eggs");

        mImageUrls.add("https://www.thespruceeats.com/thmb/CtGnnAdHCVd5jms3JyTDfIgDzR0=/960x0/filters:no_upscale():max_bytes(150000):strip_icc()/French-Toast-58addf8e5f9b58a3c9d41348.jpg");
        mNames.add("Toast");
        //mTimes.add()

        mImageUrls.add("https://i.kinja-img.com/gawker-media/image/upload/s--_iU5hnjV--/c_scale,f_auto,fl_progressive,q_80,w_800/naiqjp2jbpvcylp09utj.png");
        mNames.add("Batman");

        mImageUrls.add("https://media.wired.com/photos/5c54ee6a4feec32ca0f590d8/master/w_2400,c_limit/superman-922909434.jpg");
        mNames.add("Superman");


        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "Initializing RecyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames, mImageUrls, this);
        recyclerView.setAdapter(adapter);

    }


}
