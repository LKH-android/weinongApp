package com.example.john.weinong;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.john.weinong.dbService.Work;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by john on 2018/10/15.
 */
public class TraceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Work> traceList = new ArrayList<>(1);



    public TraceListAdapter(Context context, List<Work> traceList) {
        inflater = LayoutInflater.from(context);
        this.traceList = traceList;
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.nongshiactivityitem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        itemHolder.bindHolder(traceList.get(position));
        itemHolder.buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Work work = new Work();
                work.setObjectId(traceList.get(position).getObjectId());
                work.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            removeData(position);
                            Log.i("bmob","成功");
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
        });
        itemHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(isConnectIsNomarl())
                    {
                        Intent intent=new Intent();
                        intent.putExtra("workobjectid",traceList.get(position).getObjectId());
                        intent.setClass(mContext,NngshixiangqingActivity.class);
                        mContext.startActivity(intent);}
                    else {
                        Toast.makeText(mContext,"无法连接服务器",Toast.LENGTH_SHORT).show();
                    }
                }catch (  java.lang.IndexOutOfBoundsException e)
                {

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return traceList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView operationTime, detailedactivity,work,landname;
        private TextView tvTopLine, tvDot;
        private LinearLayout linearLayout;
        private Button buttondelete;
        public ViewHolder(View itemView) {
            super(itemView);
            operationTime = (TextView) itemView.findViewById(R.id.operationTime);
            work = (TextView) itemView.findViewById(R.id.work);
            detailedactivity = (TextView) itemView.findViewById(R.id.detailedactivity);
            tvDot = (TextView) itemView.findViewById(R.id.tvDot);
            tvTopLine = (TextView) itemView.findViewById(R.id.tvTopLine);
            landname=(TextView)itemView.findViewById(R.id.nongshiactivityitemlandname);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.nongshiitem);
            buttondelete=(Button)itemView.findViewById(R.id.btnnongshizongDelete);
        }
        public void bindHolder(Work trace) {
            operationTime.setText(trace.getOperationtime());
            detailedactivity.setText(trace.getDetailedwork());
            work.setText(trace.getWork());
            landname.setText(trace.getLandid().getLandname());
        }
    }
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
    public void removeData(int position) {
        traceList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}

