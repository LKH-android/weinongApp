package com.example.john.weinong;

/**
 * Created by john on 2018/10/25.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class NongshiphotoxiangqingAdapter extends RecyclerView.Adapter<NongshiphotoxiangqingAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mImages;
    private LayoutInflater mInflater;
    private  OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=null;

    public NongshiphotoxiangqingAdapter(Context context,ArrayList<String> mImages,OnRecyclerviewItemClickListener onRecyclerviewItemClickListener) {
        mContext = context;
        this.mImages=mImages;
        this.mInflater = LayoutInflater.from(mContext);
        this.onRecyclerviewItemClickListener=onRecyclerviewItemClickListener;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(mContext).load(mImages.get(position)).into(holder.ivImage);
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onRecyclerviewItemClickListener.onItemClickListener(v,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

}
