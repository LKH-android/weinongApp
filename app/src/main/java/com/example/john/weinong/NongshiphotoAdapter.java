package com.example.john.weinong;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;

public class NongshiphotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<String> mImages;
    private LayoutInflater mInflater;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=null;



    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    public NongshiphotoAdapter(Context context,OnRecyclerviewItemClickListener onRecyclerviewItemClickListener) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.onRecyclerviewItemClickListener=onRecyclerviewItemClickListener;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
          View view1=  mInflater.inflate(R.layout.firestnongshiphotoitemlayout, parent, false);

          return new ViewHolder1(view1);
        } else {
            View view = mInflater.inflate(R.layout.adapter_image, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder1) {


            ((ViewHolder1)holder).ivImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerviewItemClickListener.onItemClickListener(v,position);
                }
            });
        } else if (holder instanceof ViewHolder) {

                final String image = mImages.get(position);
                Glide.with(mContext).load(new File(image)).into(((ViewHolder) holder).ivImage);

        }


    }

    @Override
    public int getItemViewType(int position) {

        if(position+1==getItemCount()) {
            return ITEM_TYPE.ITEM1.ordinal();
        }
        return ITEM_TYPE.ITEM2.ordinal();

    }

    @Override
    public int getItemCount() {
        return mImages == null ? 1 : mImages.size()+1;
    }

    public void refresh(ArrayList<String> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
    static class ViewHolder1 extends  RecyclerView.ViewHolder{
        ImageView ivImage1;

        public ViewHolder1(View itemView) {
            super(itemView);
            ivImage1 = itemView.findViewById(R.id.tianjiaphoto);
        }
    }
}
