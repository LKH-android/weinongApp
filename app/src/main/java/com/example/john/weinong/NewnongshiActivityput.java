package com.example.john.weinong;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.john.weinong.dbService.Land;
import com.example.john.weinong.dbService.MyUser;
import com.example.john.weinong.dbService.Type;
import com.example.john.weinong.dbService.Work;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by john on 2018/10/15.
 */
//农事发布activity
public class NewnongshiActivityput extends AppCompatActivity {
    private Spinner spDown;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private LinearLayout Newnongshitime;
    private TextView Newnongshitimetext;
    private ProgressDialog progressDialog;
    private TimePickerView  pvCustomLunar;
    private LinearLayout tijiaoButton;
    private LinearLayout quxiaoButton;
    private String landobjectid;
    private MyUser user;
    private EditText editText;
    private String workleixing;
    private static final int REQUEST_CODE = 0x00000011;
    private RecyclerView rvImage;
    private NongshiphotoAdapter mAdapter;
    private ArrayList<String> images=new ArrayList<>();
    private OnRecyclerviewItemClickListener recyclerviewItemClickListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nongshixuanze);
        getData();
        initLunarPicker();
        spDown=(Spinner)findViewById(R.id.spDwon);
        Newnongshitime=(LinearLayout)findViewById(R.id.nongshicaozuotime);
        Newnongshitimetext=(TextView)findViewById(R.id.nongshicaozuotimetext);
        tijiaoButton=(LinearLayout) findViewById(R.id.nongshitijiao);
        quxiaoButton=(LinearLayout)findViewById(R.id.nongshiquxiao);
        editText=(EditText)findViewById(R.id.nongshiedittext);
        rvImage=(RecyclerView)findViewById(R.id.nongshiphotoRecycleListview);
       recyclerviewItemClickListener=new OnRecyclerviewItemClickListener() {
           @Override
           public void onItemClickListener(View v, int position) {
               ImageSelector.builder()
                       .useCamera(true) // 设置是否使用拍照
                       .setSingle(false)  //设置是否单选
                       .setViewImage(true) //是否点击放大图片查看,，默认为true
                       .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                       .setSelected(images)
                       .start(NewnongshiActivityput.this, REQUEST_CODE); // 打开相册
           }
       };

        data_list = new ArrayList<>();
        user = BmobUser.getCurrentUser(MyUser.class);
        final Intent intent=getIntent();
        landobjectid=intent.getStringExtra("LANDOBJECTID");

        rvImage.setLayoutManager(new GridLayoutManager(this, 3,LinearLayoutManager.VERTICAL,false));
        mAdapter = new NongshiphotoAdapter(this,recyclerviewItemClickListener);
        rvImage.setAdapter(mAdapter);
        Newnongshitime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvCustomLunar.show();
            }
        });
quxiaoButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
spDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        workleixing=data_list.get(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
tijiaoButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(Newnongshitimetext.getText().toString()))
        {
            Toast.makeText(NewnongshiActivityput.this, "请选择操作时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if( isConnectIsNomarl())
        {
        showProgressDialog();
        if(!images.isEmpty()){
      final  String[] strings = new String[images.size()];
        images.toArray(strings);
        BmobFile.uploadBatch(strings, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files,List<String> urls) {
                if(urls.size()==strings.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                    Land land=new Land();
                    land.setObjectId(landobjectid);
                    Work work=new Work();
                    work.setLandid(land);
                    work.setUser_id(user);
                    work.setDetailedwork(editText.getText().toString());
                    work.setWork(workleixing);
                    work.setPhoto(files);
                    work.setOperationtime(Newnongshitimetext.getText().toString());
                    work.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){
                                Log.i("bmob","保存成功");
                                Intent intent1=new Intent();
                                intent1.setClass(NewnongshiActivityput.this,Tianxiangqing.class);
                                startActivity(intent1);
                                finish();
                            }else{
                                Log.i("bmob","保存失败："+e.getMessage());
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
            }
            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
        }else {
            Land land=new Land();
            land.setObjectId(landobjectid);
            Work work=new Work();
            work.setLandid(land);
            work.setUser_id(user);
            work.setDetailedwork(editText.getText().toString());
            work.setWork(workleixing);
            work.setOperationtime(Newnongshitimetext.getText().toString());
            work.save(new SaveListener<String>() {

                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        Log.i("bmob","保存成功");
                        Intent intent1=new Intent();
                        intent1.setClass(NewnongshiActivityput.this,Tianxiangqing.class);
                        startActivity(intent1);
                        finish();
                    }else{
                        Log.i("bmob","保存失败："+e.getMessage());
                    }
                }
            });

        }
        }else {
            Toast.makeText(NewnongshiActivityput.this,"无法连接服务器",Toast.LENGTH_SHORT).show();
        }
    }
});
    }
    private void getData()
    {
        BmobQuery<Type> TypeQuery = new BmobQuery<Type>();
        TypeQuery.addQueryKeys("fertilize");
        TypeQuery.findObjects(new FindListener<Type>() {
            @Override
            public void done(List<Type> object, BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        data_list.add(object.get(i).getFertilize());
                    }
                    arr_adapter= new ArrayAdapter<String>(NewnongshiActivityput.this, android.R.layout.simple_spinner_item, data_list);
                    arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDown.setAdapter(arr_adapter);
                }else{
                    Toast.makeText(NewnongshiActivityput.this,"发生未知错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Newnongshitimetext.setText(getTime(date));
            }
        })
                .isDialog(true)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            mAdapter.refresh(images);
        }
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(NewnongshiActivityput.this);
            progressDialog.setMessage("正在上传数据...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
