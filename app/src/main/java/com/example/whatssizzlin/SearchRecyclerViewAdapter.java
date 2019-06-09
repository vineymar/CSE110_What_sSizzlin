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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    //
    private ArrayList<Recipe> myRecs;
    private HomeFragment homeFragment;
    private Context mContext;

    public SearchRecyclerViewAdapter(ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> times, ArrayList<Recipe> recs, Context context, HomeFragment hf) {
    /*    this.mNames = names;
        this.mImageUrls = imageUrls;
        this.mTimes = times;
        this.mContext=context;
        this.mRecs = recs;
        homeFragment = hf;
        //this.mContext = context;
*/
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
/*
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);

        holder.image_name.setText(mNames.get(position));
        holder.image_time.setText(mTimes.get(position));
*/
        /*OnClickListener circuluarView*/
        holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
/*
                ViewRecipe nn = new ViewRecipe();
                nn.home = homeFragment;
                Bundle bundle = new Bundle();
                bundle.putString("name", mRecs.get(position).name);
                String str = new String();

                for (String s : mRecs.get(position).ingredients){
                    str += s + '\n';
                }
                bundle.putString("ingredients", str);
                Log.d("hi", str);
                String inv = new String();
                for (String s : mRecs.get(position).method){
                    inv += s + '\n';
                }

                bundle.putString("instructions", inv);
                Log.d("bye", inv);
                bundle.putString("id", mRecs.get(position).id);
                nn.setArguments(bundle);
                //nn.setID(mIDs.get(position));
                homeFragment.home.setFragment(nn);

*/
               /*
                Intent tvr = new Intent(homeFragment.getActivity(), ViewRecipe.class);
                tvr.putExtra("id", mIDs.get(position));
                homeFragment.startActivity(tvr);

                //homeFragment.startActivity(homeFragment., ViewRecipe.class);
                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                */
            }
        });
    }

    @Override
    public int getItemCount() {
        return myRecs.size(); //or mImageUrls since they will be the same
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
