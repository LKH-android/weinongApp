package com.example.john.weinong;


import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.john.weinong.dbService.MyUser;
import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserSginupPasswordAndSmsActivity extends AppCompatActivity {
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    private CircleImageView cImageView;
    private ImageView bImageView;
    private static final int REQUEST_CODE = 0x00000011;
    private  String phone;
    private  String username;
    private String password;
    private String code;
    private BmobFile bmobFile;
    private ArrayList<String> images=new ArrayList<>();
    private Button buttonyz;
    private Context mcontext=UserSginupPasswordAndSmsActivity.this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sginup_password_and_sms);
        Bmob.initialize(mcontext, "1fef8f91ecb001a1d0000a835b9b23d9");
        ButterKnife.bind(this);
        init();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @OnClick({R.id.btn_send, R.id.btn_signup,R.id.imageview_person_back, R.id.circleimageview_personal_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                query.addWhereEqualTo("mobilePhoneNumber", phone);
                query.count(MyUser.class, new CountListener() {
                    @Override
                    public void done(Integer count, BmobException e) {
                        if(e==null){
                          if(count>=1)
                          {
                              Toast.makeText(mcontext,"该号码已被注册",Toast.LENGTH_SHORT).show();
                          }else {
                              BmobSMS.requestSMSCode(phone, "微农", new QueryListener<Integer>() {
                                  @Override
                                  public void done(Integer smsId, BmobException e) {
                                      if (e == null) {
                                          CountDownUtil countDownUtil =new CountDownUtil(buttonyz,60000,1000);  // vercodebtn 就是你的那个按钮
                                          countDownUtil.start();

                                      } else {
                                          if(e.getErrorCode()==10010)
                                          {
                                              Toast.makeText(mcontext,"短信验证操作过于频繁请一小时后再试",Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  }
                              });
                          }
                        }else{
                            Toast.makeText(mcontext,"发生未知错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case R.id.btn_signup: {
                 username = mEdtUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                 password = mEdtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                 phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                 code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!images.isEmpty())
                {
                String picPath = images.get(0);
                 bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            MyUser user = new MyUser();
                            //TODO 设置手机号码（必填）
                            user.setMobilePhoneNumber(phone);
                            //TODO 设置用户名，如果没有传用户名，则默认为手机号码
                            user.setUsername(username);
                            //TODO 设置用户密码
                            user.setPassword(password);
                            //TODO 设置额外信息：此处为昵称
                            user.setPhoto(bmobFile);
                            user.signOrLogin(code, new SaveListener<BmobUser>() {
                                @Override
                                public void done(BmobUser bmobUser, BmobException e) {
                                    if (e == null) {
                                       finish();
                                    } else {
                                        if(e.getErrorCode()==207)
                                        {
                                            Toast.makeText(mcontext,"验证码输入错误",Toast.LENGTH_SHORT).show();
                                        }
                                        if(e.getErrorCode()==202)
                                        {
                                            Toast.makeText(mcontext,"用户名已存在",Toast.LENGTH_SHORT).show();
                                        }
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
                });}else {
                    MyUser user = new MyUser();
                    //TODO 设置手机号码（必填）
                    user.setMobilePhoneNumber(phone);
                    //TODO 设置用户名，如果没有传用户名，则默认为手机号码
                    user.setUsername(username);
                    //TODO 设置用户密码
                    user.setPassword(password);
                    //TODO 设置额外信息：此处为昵称
                    user.signOrLogin(code, new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                finish();
                            } else {
                                if(e.getErrorCode()==207)
                                {
                                    Toast.makeText(mcontext,"验证码输入错误",Toast.LENGTH_SHORT).show();
                                }
                                if(e.getErrorCode()==202)
                                {
                                    Toast.makeText(mcontext,"用户名已存在",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                break;
            }
            case R.id.imageview_person_back:
                finish();
                break;
            case R.id.circleimageview_personal_photo:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setSingle(true)  //设置是否单选
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            default:
                break;
        }
    }
    public void init() {
        bImageView = (ImageView) findViewById(R.id.imageview_person_back);
        cImageView = (CircleImageView) findViewById(R.id.circleimageview_personal_photo);
        buttonyz=(Button)findViewById(R.id.btn_send);
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
