package com.example.john.weinong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.john.weinong.dbService.MyUser;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by john on 2018/10/17.
 */

public class XiugaipasswordActivity extends AppCompatActivity {
    private ImageView backImageView;
    private EditText newPassword;
    private Button button;
    private TextView mTvInfo;
    private EditText mEdtCode;
    private Button yanzhengmabutton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(XiugaipasswordActivity.this, "1fef8f91ecb001a1d0000a835b9b23d9");
        ButterKnife.bind(this);
        setContentView(R.layout.newpassword);
        backImageView=(ImageView)findViewById(R.id.xiugaipasswordimageview_person_back);
        newPassword=(EditText)findViewById(R.id.newpassword);
        button=(Button)findViewById(R.id.xiugaipasswordquereng);
        mEdtCode=(EditText)findViewById(R.id.edt_code);
        mTvInfo=(TextView)findViewById(R.id.tv_info);
        yanzhengmabutton=(Button)findViewById(R.id.btn_send);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        yanzhengmabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                String phone = user.getMobilePhoneNumber();
                Boolean verify = user.getMobilePhoneNumberVerified();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(XiugaipasswordActivity.this, "请先绑定手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (verify == null || !verify) {
                    Toast.makeText(XiugaipasswordActivity.this, "请先绑定手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                BmobSMS.requestSMSCode(phone, "微农", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                        } else {
                            mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword1 = newPassword.getText().toString().trim();
                if (TextUtils.isEmpty(newPassword1)) {
                    Toast.makeText(XiugaipasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(XiugaipasswordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobUser.resetPasswordBySMSCode(code, newPassword1, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            mTvInfo.append("重置成功");
                            finish();
                        } else {
                            mTvInfo.append("重置失败：" + e.getErrorCode() + "-" + e.getMessage());
                        }
                    }
                });
            }
        });
    }



}
