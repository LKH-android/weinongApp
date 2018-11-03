package com.example.john.weinong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.john.weinong.dbService.MyUser;
import java.io.File;
import java.util.ArrayList;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by john on 2018/10/17.
 */

public class XiugaitouxiangActivity extends AppCompatActivity implements
        View.OnClickListener {

    private CircleImageView cImageView;
    private ImageView bImageView;
    private RelativeLayout relativeLayout;
    private Button button;
    private ArrayList<String> images;
    private static final int REQUEST_CODE = 0x00000011;
    private BmobFile bmobFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtouxiang);
        init();
    }
    public void init() {

        bImageView = (ImageView) findViewById(R.id.xiugaitouxiangimageview_person_back);
        bImageView.setOnClickListener(this);
relativeLayout=(RelativeLayout)findViewById(R.id.xiugaitouxianglayout_mine_personal);
relativeLayout.setOnClickListener(this);
        cImageView = (CircleImageView) findViewById(R.id.xiugaitouxiangcircleimageview_mine_photo);
        cImageView.setOnClickListener(this);
        button=(Button)findViewById(R.id.xiugaitouxiangquereng);
        button.setOnClickListener(this);
        if(Fragment1.user.getPhoto()!=null) {
            Glide.with(this)
                    .load(Fragment1.user.getPhoto().getFileUrl())
                    .into(cImageView);
        }else {
            cImageView.setImageResource(R.drawable.demon1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiugaitouxiangimageview_person_back:
                finish();
                break;
            case R.id.xiugaitouxianglayout_mine_personal:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setSingle(true)  //设置是否单选
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            case R.id.xiugaitouxiangquereng:
                String picPath =images.get(0);
                bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            MyUser user = new MyUser();
                            user.setPhoto(bmobFile);
                            user.update(Fragment1.user.getObjectId(), new UpdateListener() {

                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                    finish();
                                    }else{
                                      Toast.makeText(XiugaitouxiangActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Glide.with(this).load(new File(images.get(0))).into(cImageView);
        }
    }
}
