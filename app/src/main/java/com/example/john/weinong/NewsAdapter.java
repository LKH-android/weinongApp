package com.example.john.weinong;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

/**
 * Created by john on 2018/9/11.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener{
    public Context mContext;
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
  private List<NewsBean.pagebean.contentlist> mNewsList;
  public NewsAdapter(Context mContext, List<NewsBean.pagebean.contentlist> mNewsList, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener){
      this.mNewsList=mNewsList;
      this.mContext=mContext;
      this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
  }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem,parent,false);
        view.setOnClickListener(this);
      ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      NewsBean.pagebean.contentlist contentlist=mNewsList.get(position);
            int i=contentlist.getImageurls().size();
          for(int a=0;a<i;a++){
              RequestOptions options = new RequestOptions()
                      .placeholder(R.mipmap.morennews).error(R.mipmap.morennews);
              Glide.with(mContext)
                      .load(contentlist.getImageurls().get(a).getUrl())
                      .apply(options)
                      .into(holder.NewsImage);
        }
      holder.Newstime.setText(contentlist.getPubDate().toString());
      holder.NewsTitle.setText(contentlist.getTitle().toString());
      holder.NewsId.setText(contentlist.getSource().toString());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView NewsImage;
        TextView NewsTitle;
        TextView NewsId;
        TextView Newstime;
        public ViewHolder(View itemView) {
            super(itemView);
            NewsImage=(ImageView)itemView.findViewById(R.id.newsimage);
            NewsTitle=(TextView)itemView.findViewById(R.id.newstitle);
            NewsId=(TextView)itemView.findViewById(R.id.newsid);
            Newstime=(TextView)itemView.findViewById(R.id.newsidtime);
        }
    }

    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }
}
