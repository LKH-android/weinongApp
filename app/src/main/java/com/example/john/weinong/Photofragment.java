package com.example.john.weinong;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by john on 2018/10/31.
 */

public class Photofragment extends Fragment {
    private ImageView imageView;
    private String url;
    private PopupWindow popupWindow;
    private TextView textView;
    private TextView textView1;
    private LinearLayout linearLayout;
    private int width;
    private int heigth;
    private Bitmap tempBitmip;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lookphotolayout,container,false);
        return view;
    }
    public static Photofragment newInstance(String url) {
        Photofragment newFragment = new Photofragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView=(ImageView)getActivity().findViewById(R.id.lookphotoimage);


        linearLayout=(LinearLayout)getActivity().findViewById(R.id.lookphotolinearlayout);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        heigth = dm.heightPixels;
        width = dm.widthPixels;
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.height=heigth-70;
        params.width =width;
        linearLayout.setLayoutParams(params);
        initPopupWindow();
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               showPopWindow();
                return false;
            }
        });

        url=(String)getArguments().get("URL");

        imageviewshow(url);

    }
    private void initPopupWindow() { //要在布局中显示的布局
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dibutanchuang, null, false);
//实例化PopupWindow并设置宽高
        textView=(TextView)contentView.findViewById(R.id.open_from_camera);
        textView1=(TextView)contentView.findViewById(R.id.cancel);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               savebitmap();
                popupWindow.dismiss();
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,180,true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true); //设置可以点击
        popupWindow.setTouchable(true); //进入退出的动画。
    }
    private void showPopWindow() {
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.lookphotolayout,
                null); popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }
    public void imageviewshow( String url)
    {
        Glide.with(getActivity()).load(url).into(imageView);
    }

    private void savebitmap()
    {            final Context context=getApplicationContext();
                    imageView.setDrawingCacheEnabled(true);
                            tempBitmip=imageView.getDrawingCache();
                    new Thread(new Runnable() {
                     @Override
                         public void run() {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                Log.i("kkkkk",tempBitmip+"");
                //保存图片
                File newFile=new File("/mnt/sdcard/"+format.format(date)+".jpg");
                FileOutputStream out;
                try {
                    Log.i("ppppp",tempBitmip+"");
                    out = new FileOutputStream(newFile);
                    if (tempBitmip.compress(Bitmap.CompressFormat.JPEG, 100, out)) {

                        out.flush();
                        out.close();
                    }
                }catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                String backgroundPath = newFile.getPath();
                if (backgroundPath != null) {

                    // 其次把文件插入到系统图库
                    try {
                        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                                newFile.getAbsolutePath(), backgroundPath, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // 最后通知图库更新
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(newFile.getPath()))));



                } else {

                }
            }
        }).start();

    }
}
