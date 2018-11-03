package com.example.john.weinong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.john.weinong.dbService.MyUser;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by john on 2018/10/18.
 */

public class DengluActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private ImageView backImageView;
    private Button dengluButton;
    private Button zhuceButton;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu);
        intent=new Intent();
        username=(EditText)findViewById(R.id.dengluname);
        password=(EditText)findViewById(R.id.denglupassword);
        backImageView=(ImageView)findViewById(R.id.dengluimageview_person_back);
        dengluButton=(Button)findViewById(R.id.denglubutton);
        zhuceButton=(Button)findViewById(R.id.zhucebutton);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zhuceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(DengluActivity.this,UserSginupPasswordAndSmsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dengluButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bu2 = new BmobUser();
                bu2.setUsername(username.getText().toString());
                bu2.setPassword(password.getText().toString());
                bu2.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            Fragment1.user=BmobUser.getCurrentUser(MyUser.class);
                            Fragment2 fragment2=new Fragment2();
                            Fragment3 fragment3=new Fragment3();
                            fragment2.Bmobfragment2();
                            fragment3.getData();
                        finish();
                        }else{
                            Toast.makeText(DengluActivity.this,"登录失败，用户名或密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
