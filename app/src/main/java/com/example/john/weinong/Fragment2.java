package com.example.john.weinong;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.john.weinong.dbService.Land;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by john on 2018/9/5.
 */

public class Fragment2 extends Fragment {
    private ImageView Button;
    private RecyclerView recyclerView;
   private List<Land> Land=new ArrayList<>();
    private  TianAdapter tianAdapter;
    private LinearLayoutManager layoutManager;
    private  RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       Button=(ImageView) getActivity().findViewById(R.id.tianjia);
       recyclerView=(RecyclerView)getActivity().findViewById(R.id.TianRecycleListview);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Fragment1.user!=null)
                {
                Intent intent=new Intent();
                intent.setClass(getActivity(),WeixingActivity.class);
                startActivity(intent);}else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLayout = (RefreshLayout)getActivity().findViewById(R.id.smartLayouttian);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(isConnectIsNomarl()&&Land!=null){
                    refreshLayout.setDisableContentWhenRefresh(true);
                         Land.clear();
                        Bmobfragment2();
                }else {
                    Toast.makeText(getActivity(),"服务器连接失败", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(false);
                    refreshlayout.finishRefresh(2000);
                }

            }
        });



    }
    //从服务器获取数据
    public void Bmobfragment2(){

         if(Fragment1.user!=null)
         {
             Land.clear();
        BmobQuery<Land> query = new BmobQuery<Land>();
        query.addWhereEqualTo("user_id",Fragment1.user);
        query.order("-updatedAt");
        query.findObjects(new FindListener<Land>() {
            @Override
            public void done(List<Land> object,BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        Land.add(object.get(i));
                    }
                    layoutManager=new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    tianAdapter=new TianAdapter(getActivity(),Land);
                    recyclerView.setAdapter(tianAdapter);
                    refreshLayout.finishRefresh();
                }else{

                }
            }

        });
    }else {
             Land.clear();
             layoutManager=new LinearLayoutManager(getActivity());
             recyclerView.setLayoutManager(layoutManager);
             tianAdapter=new TianAdapter(getActivity(),Land);
             recyclerView.setAdapter(tianAdapter);
         }
    }
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {

            return true;
        } else {

            return false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
Land.clear();
Bmobfragment2();
    }



}
