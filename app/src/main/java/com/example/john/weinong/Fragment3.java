package com.example.john.weinong;


import android.content.Context;
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
import android.widget.Toast;
import com.example.john.weinong.dbService.Work;
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

public class Fragment3 extends Fragment {
    private List<Work> workList=new ArrayList<>();
    private TraceListAdapter adapter;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.fragment3RecycleListview);
        refreshLayout=(RefreshLayout)getActivity().findViewById(R.id.fragment3smartLayouttian);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(isConnectIsNomarl()&&workList!=null){
                    refreshLayout.setDisableContentWhenRefresh(true);
                    workList.clear();
                   getData();

                }else {
                    Toast.makeText(getActivity(),"服务器连接失败", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(false);
                    refreshlayout.finishRefresh(2000);
            }
            }
        });

    }
    //获取服务器数据
    public void getData() {
        if (Fragment1.user != null) {
            workList.clear();
            BmobQuery<Work> query = new BmobQuery<Work>();
            query.addWhereEqualTo("user_id", Fragment1.user);
            query.order("-updatedAt");
            query.include("landid");
            query.findObjects(new FindListener<Work>() {

                @Override
                public void done(List<Work> object, BmobException e) {
                    if (e == null) {
                        for (int i = 0; i < object.size(); i++) {
                            workList.add(object.get(i));
                        }
                        adapter = new TraceListAdapter(getActivity(), workList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        refreshLayout.finishRefresh();
                    } else {

                    }
                }

            });

        }else {
            workList.clear();
            adapter = new TraceListAdapter(getActivity(), workList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        workList.clear();
        getData();
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

}
