package com.example.whatssizzlin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.algolia.instantsearch.core.helpers.Searcher;
import com.algolia.instantsearch.ui.helpers.InstantSearch;

public class test extends AppCompatActivity {
    private static final String ALGOLIA_APP_ID = "07ZX63WQSH";
    private static final String ALGOLIA_SEARCH_API_KEY = "69013fd500045cc7dfc90d5d12dfd651";
    private static final String ALGOLIA_INDEX_NAME = "Meals";

    Searcher searcher;
    InstantSearch helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        searcher = Searcher.create(ALGOLIA_APP_ID, ALGOLIA_SEARCH_API_KEY, ALGOLIA_INDEX_NAME);
        helper = new InstantSearch(this, searcher);
        helper.search();
        //searcher.search();
    }

    @Override
    protected void onDestroy() {
        searcher.destroy();
        super.onDestroy();
    }
}
//public class Glide4Engine implements ImageEngine {
//    @Override
//    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
//        Glide.with(context)
//                .asBitmap() // some .jpeg files are actually gif
//                .load(uri)
//                .apply(new RequestOptions()
//                        .override(resize, resize)
//                        .placeholder(placeholder)
//                        .centerCrop())
//                .into(imageView);
//    }
//
//    @Override
//    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
//                                 Uri uri) {
//        Glide.with(context)
//                .asBitmap() // some .jpeg files are actually gif
//                .load(uri)
//                .apply(new RequestOptions()
//                        .override(resize, resize)
//                        .placeholder(placeholder)
//                        .centerCrop())
//                .into(imageView);
//    }
//
//    @Override
//    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
//        Glide.with(context)
//                .load(uri)
//                .apply(new RequestOptions()
//                        .override(resizeX, resizeY)
//                        .priority(Priority.HIGH)
//                        .fitCenter())
//                .into(imageView);
//    }
//
//    @Override
//    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
//        Glide.with(context)
//                .asGif()
//                .load(uri)
//                .apply(new RequestOptions()
//                        .override(resizeX, resizeY)
//                        .priority(Priority.HIGH)
//                        .fitCenter())
//                .into(imageView);
//    }
//
//    @Override
//    public boolean supportAnimatedGif() {
//        return true;
//    }
//}