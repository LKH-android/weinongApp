package com.example.john.weinong;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.show.api.ShowApiRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2018/9/11.
 */

public class NewsActivity extends AppCompatActivity implements HideScrollListener{
    private RecyclerView recyclerView;
    private ImageButton fab;
    private ImageButton imageButton;
    private RefreshLayout refreshLayout;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener;
    private List<NewsBean.pagebean.contentlist> newsBeans=new ArrayList<>();
    private Handler handler;
    private static String title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morenews);
        fab = (ImageButton) findViewById(R.id.fab);
       recyclerView=(RecyclerView)findViewById(R.id.RecycleListviewmorenews);
        title=getIntent().getStringExtra("newstitle");
        imageButton=(ImageButton)findViewById(R.id.imagebutton1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(NewsActivity.this,SearchShopActivity.class);
                startActivity(intent);
                finish();

            }
        });
       if(isConnectIsNomarl()){
         NewsRequestmore(title);}
         else {
           Toast.makeText(NewsActivity.this,"服务器连接失败", Toast.LENGTH_SHORT).show();
       }
        handler =new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 0:
                        LinearLayoutManager layoutManager=new LinearLayoutManager(NewsActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        NewsAdapter newsAdapter=new NewsAdapter(NewsActivity.this,newsBeans,onRecyclerviewItemClickListener);
                        recyclerView.setAdapter(newsAdapter);
                        recyclerView.addOnScrollListener(new FabScrollListener(NewsActivity.this));
                        break;

                    default:
                        break;
                }
            }
        };
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String newsString=prefs.getString("news",null);
        if(newsString!=null){
            Gson gson=new Gson();
            NewsBean newsBean=gson.fromJson(newsString,NewsBean.class);
            newsBeans=newsBean.getPagebean().getContentlist();
            Message msg = handler.obtainMessage();
            msg.what = 0; //消息标识
            msg.obj=newsBeans;
            handler.sendMessage(msg);
        }
        onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                //这里的view就是我们点击的view  position就是点击的position
                String url=newsBeans.get(position).getLink();
                Intent intent=new Intent();
                intent.setClass(NewsActivity.this,newsweb.class);
                intent.putExtra("weburl",url);
                startActivity(intent);

            }
        };
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
         refreshLayout = (RefreshLayout)findViewById(R.id.Smartrefreshlayout);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(isConnectIsNomarl()&&newsBeans!=null){
                   NewsRequestmore(title);
                    refreshlayout.finishRefresh(2000);
                }else {
                    Toast.makeText(NewsActivity.this,"服务器连接失败", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(false);
                    refreshlayout.finishRefresh(2000);
                }
            }
        });
      refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
          @Override
          public void onLoadmore(RefreshLayout refreshlayout) {
               refreshlayout.finishLoadmoreWithNoMoreData();
          }
      });
    }
    public void NewsRequestmore(final String title){
        if(isConnectIsNomarl()){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res=new ShowApiRequest("https://route.showapi.com/109-35","74618","a8406ecafb1649408a1d4a3d57b24c04")
                        .addTextPara("title", title)
                        .addTextPara("maxResult", "1000")
                        .addTextPara("needAllList","0")
                        .post();
                com.alibaba.fastjson.JSONObject js=com.alibaba.fastjson.JSONObject.parseObject(res);
                final String news=js.getJSONObject("showapi_res_body").toString();
                Gson gson=new Gson();
                NewsBean newsBean=gson.fromJson(news,NewsBean.class);
                if(newsBean.getPagebean().getContentlist().size()!=0||newsBean.getPagebean().getContentlist()!=null){
                newsBeans=newsBean.getPagebean().getContentlist();
                    SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(NewsActivity.this).edit();
                    editor.putString("news",news);
                    editor.apply();
                Message msg = handler.obtainMessage();
                msg.what = 0; //消息标识
                msg.obj=newsBeans;
                handler.sendMessage(msg);}
                else {
                    Toast.makeText(NewsActivity.this,"获取服务器信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();}
        else {
            Toast.makeText(NewsActivity.this,"服务器连接失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onHide() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(fab.getHeight() + layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
    }
    @Override
    public void onShow() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
