package com.example.john.weinong;

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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by john on 2018/10/17.
 */

public class XiugainameActivity extends AppCompatActivity {
    private ImageView backImageView;
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newname);
        backImageView=(ImageView)findViewById(R.id.xiugainameimageview_person_back);
        editText=(EditText)findViewById(R.id.xiugainameedt_username);
        button=(Button)findViewById(R.id.xiugainamequereng);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser newUser = new BmobUser();
                newUser.setUsername(editText.getText().toString());
                MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                        finish();
                        }else{
                            Toast.makeText(XiugainameActivity.this,"修改用户信息失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
