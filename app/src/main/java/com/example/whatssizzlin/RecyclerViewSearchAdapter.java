package com.example.whatssizzlin;



//viewHolderClass inside adapter

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter<RecyclerViewSearchAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    //
    private String mSearchNames;
    private String mSearchImageUrls;
    private String mSearchTimes;
    private Context mSearchContext;


    public RecyclerViewSearchAdapter(Recipe thisRecipe, Context context) {
        this.mSearchNames = thisRecipe.name;
        this.mSearchImageUrls = thisRecipe.img_url;
        this.mSearchTimes = thisRecipe.timeTag + " minutes.";
        this.mSearchContext = context;
        //this.mSearchRecs = recs;
       // searchFragment = sf;
        //this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mSearchContext)
                .asBitmap()
                .load(mSearchImageUrls)
                .into(holder.image);

        holder.image_name.setText(mSearchNames);
        holder.image_time.setText(mSearchTimes);
//
//        /*OnClickListener circuluarView*/
//        holder.image.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                ViewRecipe nn = new ViewRecipe();
//                nn.search = searchFragment;
//                Bundle bundle = new Bundle();
//                bundle.putString("name", mSearchRecs.get(position).name);
//                String str = new String();
//
//                for (String s : mSearchRecs.get(position).ingredients){
//                    str += s + '\n';
//                }
//                bundle.putString("ingredients", str);
//                Log.d("hi", str);
//                String inv = new String();
//                for (String s : mSearchRecs.get(position).method){
//                    inv += s + '\n';
//                }
//
//                bundle.putString("instructions", inv);
//                Log.d("bye", inv);
//                bundle.putString("id", mSearchRecs.get(position).id);
//                nn.setArguments(bundle);
//                //nn.setID(mIDs.get(position));
//                //searchFragment.search.setFragment(nn);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentTransaction.replace(R.id.main_frame, new CreateRecipeFragment());
//                fragmentTransaction.commit();
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mSearchNames.length(); //or mImageUrls since they will be the same
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView image_name;
        TextView image_time;

        //default constructor

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            image_name = itemView.findViewById(R.id.name);
            image_time = itemView.findViewById(R.id.time);
        }
    }

}
