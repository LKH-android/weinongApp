package com.example.john.weinong;

/**
 * Created by john on 2018/9/27.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.john.weinong.dbService.Land;
import com.example.john.weinong.dbService.Work;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by john on 2018/9/11.
 */

public class TianAdapter extends RecyclerView.Adapter<TianAdapter.ViewHolder> {
    public Context mContext;
    private Land cdland;
    private List<Land> mTianList;

    public TianAdapter(Context mContext, List<Land> mTianList){
        this.mTianList=mTianList;
        this.mContext=mContext;
    }
    @Override
    public TianAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tianitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        cdland=mTianList.get(position);
        Glide.with(mContext)
                .load(mTianList.get(position).getImageurl())
                .into(holder.tianImage);
        holder.tianName.setText(mTianList.get(position).getLandname());
        holder.tianarea.setText(mTianList.get(position).getLandarea());
        holder.tianCrop.setText(mTianList.get(position).getCrop());
        holder.tianTime.setText(mTianList.get(position).getTime());
        holder.itemView.setTag(position);
        final String DeleteItem=mTianList.get(position).getObjectId();
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobQuery<Work> query = new BmobQuery<Work>();
                query.addWhereEqualTo("landid",mTianList.get(position));
                query.order("-updatedAt");
                query.findObjects(new FindListener<Work>() {
                    @Override
                    public void done(List<Work> list, BmobException e) {
                        if(e==null){
                            for(int i=0;i<list.size();i++)
                            {
                                Work work=new Work();
                                work.setObjectId(list.get(i).getObjectId());
                                work.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                        }else{
                                            Toast.makeText(mContext,"删除失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }else {

                        }
                    }
                });
                removeData(position);
                Land item = new Land();
                item.setObjectId(DeleteItem);
                item.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){

                        }else{
                            Toast.makeText(mContext,"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("objectid",DeleteItem);
                intent.setClass(mContext,Tianxiangqing.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTianList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tianImage;
        TextView tianName;
        TextView tianarea;
        TextView tianCrop;
        TextView tianTime;
        Button btnDelete;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            tianImage=(ImageView)itemView.findViewById(R.id.imageViewland);
            tianName=(TextView)itemView.findViewById(R.id.landname);
            tianarea=(TextView)itemView.findViewById(R.id.landarea);
            tianCrop=(TextView)itemView.findViewById(R.id.landcrop);
            tianTime=(TextView)itemView.findViewById(R.id.landTime);
            btnDelete=(Button)itemView.findViewById(R.id.btnDelete);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.tianitemlinearlayout);
        }
    }


    public void removeData(int position) {
        mTianList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
