package com.example.john.weinong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

/**
 * Created by john on 2018/10/17.
 */

public class ShezhiActivity extends AppCompatActivity {
    private RelativeLayout touxiang;
    private RelativeLayout name;
    private RelativeLayout password;
    private RelativeLayout tuichu;
    private ImageView backImageView;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
         intent=new Intent();
        backImageView=(ImageView)findViewById(R.id.shezhiimageview_person_back);
        touxiang=(RelativeLayout)findViewById(R.id.shezhitouxiang);
        name=(RelativeLayout)findViewById(R.id.shezhiname);
        password=(RelativeLayout)findViewById(R.id.shezhipassword);
        tuichu=(RelativeLayout)findViewById(R.id.shezhituichu);
backImageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
touxiang.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(Fragment1.user!=null)
      {
          intent.setClass(ShezhiActivity.this,XiugaitouxiangActivity.class);
          startActivity(intent);

      }else {
          Toast.makeText(ShezhiActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
      }
    }
});
name.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(Fragment1.user!=null)
        {

            intent.setClass(ShezhiActivity.this,XiugainameActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(ShezhiActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }
    }
});
password.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(Fragment1.user!=null)
        {
        intent.setClass(ShezhiActivity.this,XiugaipasswordActivity.class);
        startActivity(intent);

        }else {
            Toast.makeText(ShezhiActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }

    }
});
tuichu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        BmobUser.logOut();
        BmobUser currentUser = BmobUser.getCurrentUser();
        Fragment1.user=null;
        finish();
    }
});
    }
}
