package com.example.john.weinong;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.john.weinong.dbService.Advertisement;
import com.example.john.weinong.dbService.MyUser;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.show.api.ShowApiRequest;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by john on 2018/9/5.
 */

public class Fragment1 extends Fragment {
    public LocationClient mLocationClient;
    private RollPagerView mRollViewPager;
   private ImageButton imageButton;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButtonnews;
    public static TextView positionText;
    private RecyclerView recyclerView;
    public Handler handler;
    private Button gengduo;
    public static MyUser user;
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener;
    private List<NewsBean.pagebean.contentlist> newsBeans=new ArrayList<>();
    private RefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bmob.initialize(getActivity(), "1fef8f91ecb001a1d0000a835b9b23d9");
        user = BmobUser.getCurrentUser(MyUser.class);
        positionText=(TextView)getActivity().findViewById(R.id.location);
        imageButton1=(ImageButton)getActivity().findViewById(R.id.imagebutton2);
        Button button=(Button)getActivity().findViewById(R.id.button4);
       recyclerView=(RecyclerView)getActivity().findViewById(R.id.RecycleListview);
       gengduo=(Button)getActivity().findViewById(R.id.gengduo);
       imageButton=(ImageButton)getActivity().findViewById(R.id.imageButton);
       imageButtonnews=(ImageButton)getActivity().findViewById(R.id.imageButton5);
        mRollViewPager = (RollPagerView)getActivity().findViewById(R.id.roll_view_pager);
      gundongguanggao();
       imageButtonnews.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent();
               intent.setClass(getActivity(),NewsActivity.class);
               intent.putExtra("newstitle","农产品农业");
               startActivity(intent);
           }
       });

        final MainActivity mainActivity = (MainActivity) getActivity();//从Fragment取得Activity实例
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.Qiehuan(2);
            }
        });
        positionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),locationMainActivity.class);
                intent.putExtra("county",positionText.getText().toString());
                startActivity(intent);
            }
        });

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent();
               intent.setClass(getActivity(),NewsActivity.class);
               intent.putExtra("newstitle","农产品农业");
               startActivity(intent);
           }
       });
        mLocationClient=new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        if(Build.VERSION.SDK_INT>=23)
        {
            List<String> permissionList=new ArrayList<>();
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(!permissionList.isEmpty()){
                String[]permissions=permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(getActivity(),permissions,1);
            }else {
                requestLocation();
            }
        }else {
            requestLocation();}

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),locationMainActivity.class);
                intent.putExtra("county",positionText.getText().toString());
                startActivity(intent);
            }
        });
        imageButton2=(ImageButton)getActivity().findViewById(R.id.imageButton4);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),WeatherActivity.class);
                intent.putExtra("countyname",positionText.getText().toString());
                startActivity(intent);
            }
        });
gengduo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(getActivity(),NewsActivity.class);
        intent.putExtra("newstitle","农产品农业");
        startActivity(intent);
    }
});
        NewsRequest("农产品农业");
        handler =new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 0:
                        CustomLinearLayoutManager layoutManager=new  CustomLinearLayoutManager(getActivity());
                        layoutManager.setScrollEnabled(false);
                        recyclerView.setLayoutManager(layoutManager);
                        NewsAdapter newsAdapter=new NewsAdapter(getActivity(),newsBeans,onRecyclerviewItemClickListener);
                        recyclerView.setAdapter(newsAdapter);
                        refreshLayout.finishRefresh();
                        break;

                    default:
                        break;
                }
            }
        };
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        String newsString=prefs.getString("newsfragment1",null);
        if(newsString!=null){
            Gson gson=new Gson();
            NewsBean newsBean=gson.fromJson(newsString,NewsBean.class);
            newsBeans=newsBean.getPagebean().getContentlist();
            Message msg = handler.obtainMessage();
            msg.what = 0; //消息标识
            msg.obj=newsBeans;
            handler.sendMessage(msg);
        }
        //新闻recycleview的点击事件
        onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                //这里的view就是我们点击的view  position就是点击的position
                String url=newsBeans.get(position).getLink();
                Intent intent=new Intent();
                intent.setClass(getActivity(),newsweb.class);
                intent.putExtra("weburl",url);
                startActivity(intent);

            }
        };
       refreshLayout = (RefreshLayout)getActivity().findViewById(R.id.smartLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(isConnectIsNomarl()&&newsBeans!=null){
                    refreshLayout.setDisableContentWhenRefresh(true);
                    mLocationClient.start();
                    NewsRequest("农产品农业");
                }else {
                    Toast.makeText(getActivity(),"服务器连接失败", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(false);
                    refreshlayout.finishRefresh(2000);
                }

            }
        });


    }



    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!= PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getActivity(),"必须同意所有权限才能使用定位功能", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(getActivity(),"发生未知错误定位失败", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

//滚动广告条的适配器
    private class TestNormalAdapter extends StaticPagerAdapter {

        private List<Advertisement> urls=new ArrayList<>();

        public TestNormalAdapter (List<Advertisement> url)
        {
            this.urls=url;
        }



        @Override
        public View getView(ViewGroup container, final int position) {

                ImageView view = new ImageView(container.getContext());

                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.gundongimage01).error(R.drawable.gundongimage01).diskCacheStrategy(DiskCacheStrategy.NONE);;

                Glide.with(getActivity())
                        .load(urls.get(position).getImage().getFileUrl())
                        .apply(options)
                        .into(view);

            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.putExtra("weburl",urls.get(position).getUrl());
        intent.setClass(getActivity(),newsweb.class);
        startActivity(intent);
    }
});
            return view;
        }


        @Override
        public int getCount() {
            return urls.size();
        }
    }
    //结束定位
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
    //内部类实现定位
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {

                    StringBuilder currentPosition=new StringBuilder();

                    currentPosition.append(bdLocation.getDistrict());
                    if(!currentPosition.toString().equals("null")){
                        positionText.setText(currentPosition);
                        mLocationClient.stop();

                    }
                    Log.d("位置信息",positionText.getText().toString());

        }
    }
    //H获取新闻信息
    public void NewsRequest(final String title){
        if(isConnectIsNomarl()){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res=new ShowApiRequest("https://route.showapi.com/109-35","74618","a8406ecafb1649408a1d4a3d57b24c04")
                        .addTextPara("title", title)
                        .addTextPara("maxResult", "10")
                        .addTextPara("needAllList","0")
                        .post();

                 com.alibaba.fastjson.JSONObject js=com.alibaba.fastjson.JSONObject.parseObject(res);
               if(String.valueOf(js.getJSONArray("ret_code")).equals("null"))
                {

                 final String news=js.getJSONObject("showapi_res_body").toString();
                Gson gson=new Gson();
                  NewsBean newsBean=gson.fromJson(news,NewsBean.class);
                  if(newsBean.getPagebean().getContentlist().size()!=0||newsBean.getPagebean().getContentlist()!=null){
                  newsBeans=newsBean.getPagebean().getContentlist();
                      SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                      editor.putString("newsfragment1",news);
                      editor.apply();
                Message msg = handler.obtainMessage();
                msg.what = 0; //消息标识
                msg.obj=newsBeans;
                handler.sendMessage(msg);

                  }
                else {
                      Toast.makeText(getActivity(),"获取服务器信息失败", Toast.LENGTH_SHORT).show();
                  }

            }else { Toast.makeText(getActivity(),"获取服务器信息失败", Toast.LENGTH_SHORT).show();
               }
            }

        }).start();}
        else {

            Toast.makeText(getActivity(),"服务器连接失败", Toast.LENGTH_SHORT).show();
        }


    }
    //获取滚动栏的图片与链接
    private void gundongguanggao()
    {
        BmobQuery<Advertisement> query = new BmobQuery<Advertisement>();
        query.addWhereEqualTo("queren", "true");
        query.setLimit(50);
        query.findObjects(new FindListener<Advertisement>() {
            @Override
            public void done(List<Advertisement> object, BmobException e) {
                if(e==null){
                    mRollViewPager.setFocusable(true);
                    mRollViewPager.setFocusableInTouchMode(true);
                    mRollViewPager.requestFocus();
                    mRollViewPager.setPlayDelay(3000);
                    mRollViewPager.setAnimationDurtion(500);
                    mRollViewPager.setAdapter(new TestNormalAdapter(object));
                    mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    //判断是否连网
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {

            return true;
        } else {

            return false;
        }
    }


}
