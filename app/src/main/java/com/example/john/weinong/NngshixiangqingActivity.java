package com.example.john.weinong;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.john.weinong.dbService.Work;
import java.util.ArrayList;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by john on 2018/10/25.
 */

public class NngshixiangqingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NongshiphotoxiangqingAdapter mAdapter;
    private String workobjectid;
    private ArrayList<String> images=new ArrayList<>();
    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nongshiphotolayout);
        recyclerView=(RecyclerView)findViewById(R.id.nongshiphotoxiangqingRecycview);
        Intent intent=getIntent();
       workobjectid=intent.getStringExtra("workobjectid");
        BmobQuery<Work> query = new BmobQuery<Work>();
        query.getObject(workobjectid, new QueryListener<Work>() {

            @Override
            public void done(Work object, BmobException e) {
                if(e==null){

                    for(int i=0;i<object.getPhoto().size();i++)
                    {
                        images.add(object.getPhoto().get(i).getFileUrl());
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(NngshixiangqingActivity.this, 3));
                    mAdapter = new NongshiphotoxiangqingAdapter(NngshixiangqingActivity.this,images,onRecyclerviewItemClickListener);
                    recyclerView.setAdapter(mAdapter);
                }else{

                }
            }

        });
        onRecyclerviewItemClickListener=new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String url=images.get(position);
                Photofragment photofragment = new Photofragment();
                Bundle bundle = new Bundle();
                bundle.putString("URL",url);
                photofragment.setArguments(bundle);
                replaceFragment(photofragment);

            }
        };

    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.imageframelayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
