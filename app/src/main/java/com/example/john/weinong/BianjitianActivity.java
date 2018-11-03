package com.example.john.weinong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.john.weinong.dbService.Cucurbit;
import com.example.john.weinong.dbService.Eggplant;
import com.example.john.weinong.dbService.Filed;
import com.example.john.weinong.dbService.Land;
import com.example.john.weinong.dbService.Sortation;
import com.example.john.weinong.dbService.Vegetables;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by john on 2018/10/12.
 */

public class BianjitianActivity extends AppCompatActivity {
    private MapView mapView;
    private EditText editText;
    private TextView textViewmianji;
    private TextView textViewzhongzhiwu;
    private TextView textViewtime;
    private Button button;
    private BaiduMap baiduMap;
    private UiSettings mUiSettings;
    private String objectid;
    private double jiexila;
    private double jiexilo;
    private int size1;
    private Polygon polygons;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<String> options2Items = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    //农作物分类所需list
    private ArrayList<String> options4Items=new ArrayList<>();
    private ArrayList<ArrayList<String>> options5Items=new ArrayList<>();
    private TimePickerView  pvCustomLunar;
    private OptionsPickerView pvOptions;
    private OptionsPickerView pvOptions1;
    private LinearLayout linearLayouttianmianji;
    private LinearLayout linearLayoutzhongzhiwu;
    private LinearLayout linearLayouttime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.bianjitian);
        init();
        getOptionData();
        initOptionPicker();
        initOptionPicker2();
        initLunarPicker();
        baiduMap=mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mUiSettings=baiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        mapView.showScaleControl(false);
        mUiSettings.setAllGesturesEnabled(false);
        final Intent intent=getIntent();
       objectid=intent.getStringExtra("OBJECTID");
        jiexi();
        linearLayouttianmianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pvOptions != null){
                    pvOptions.show();}

            }
        });
        linearLayoutzhongzhiwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pvOptions1!=null)
                {
                    pvOptions1.show();
                }
            }
        });
        linearLayouttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvCustomLunar.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Land newland = new Land();
                newland.setCrop(textViewzhongzhiwu.getText().toString());
                newland.setTime(textViewtime.getText().toString());
                newland.setLandname(editText.getText().toString());
                newland.setLandarea(textViewmianji.getText().toString());
                newland.update(objectid, new UpdateListener() {


                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Intent intent1=new Intent();
                            intent1.setClass(BianjitianActivity.this,Tianxiangqing.class);
                            startActivity(intent1);
                            finish();
                        }else{
                            Toast.makeText(BianjitianActivity.this,"连接服务器失败",Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        });
    }
    private void init()
    {
     mapView=(MapView) findViewById(R.id.bianjitianditu);
     editText=(EditText)findViewById(R.id.bianjitianname);
     textViewmianji=(TextView)findViewById(R.id.bianjitianmianji);
     textViewzhongzhiwu=(TextView)findViewById(R.id.bianjitianzhongzhiwu);
     textViewtime=(TextView)findViewById(R.id.bianjitiantime);
     button=(Button)findViewById(R.id.bianjitianbaocun);
     linearLayouttianmianji=(LinearLayout)findViewById(R.id.linearlayouttianmianji);
     linearLayouttime=(LinearLayout)findViewById(R.id.linearlayouttime);
     linearLayoutzhongzhiwu=(LinearLayout)findViewById(R.id.linearlayoutzhongzhiwu);
    }
    //获取田的数据
    private void jiexi() {
        final List<LatLng> dians = new ArrayList<LatLng>();
        if (objectid != null) {
            BmobQuery<Land> query = new BmobQuery<Land>();
            query.getObject(objectid, new QueryListener<Land>() {
                @Override
                public void done(Land object, BmobException e) {
                    if (e == null) {
                        editText.setText(object.getLandname());
                        textViewmianji.setText(object.getLandarea());
                        textViewzhongzhiwu.setText(object.getCrop());
                        textViewtime.setText(object.getTime());
                        String str = object.getLocation();
                        String[] location = str.split("\\|");
                        for (int i = 0; i < location.length; i++) {
                            try {
                                String[] jingwei = location[i].split(",");
                                double num1 = Double.parseDouble(jingwei[0]);
                                double num2 = Double.parseDouble(jingwei[1]);
                                jiexila += num1;
                                jiexilo += num2;
                                dians.add(new LatLng(num1, num2));
                            } catch (NumberFormatException p) {
                            }
                        }
                        String landname = object.getLandname();
                        OverlayOptions ooPolygon = new PolygonOptions().points(dians)
                                .stroke(new Stroke(5, 0xAA00FF00)).fillColor(0xAAFFFF00);
                        polygons = (Polygon) baiduMap.addOverlay(ooPolygon);
                        size1 = location.length - 1;
                        LatLng llText = new LatLng(jiexila / size1, jiexilo / size1);
                        OverlayOptions textOption = new TextOptions()
                                .bgColor(0xAAFFFF00)
                                .fontSize(24)
                                .fontColor(0xFFFF00FF)
                                .text(landname)
                                .position(llText);
                        baiduMap.addOverlay(textOption);
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(llText);
                        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.target(llText).zoom(18).build()));
                        jiexila = 0;
                        jiexilo = 0;
                        dians.clear();
                    } else {
                        Toast.makeText(BianjitianActivity.this,"服务器连接失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(BianjitianActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
        }
    }
    //获取选择器的数据
    private void getOptionData() {

        for (int i=0;i<=1000;i++)
        {
            options1Items.add(""+i);
        }
        //选项1
        //选项2
        for (int a=0;a<=9;a++)
        {
            options2Items.add(""+a);
        }
        options3Items.add("面积：");

        BmobQuery<Sortation> SortationQuery = new BmobQuery<Sortation>();
        SortationQuery.addQueryKeys("field");
        SortationQuery.findObjects(new FindListener<Sortation>() {
            @Override
            public void done(List<Sortation> object, BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        options4Items.add(object.get(i).getField());
                    }


                }else{
                    Toast.makeText(BianjitianActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();

                }
            }
        });
        final ArrayList<String> options5Items_01 = new ArrayList<>();
        BmobQuery<Filed> FiledQuery = new BmobQuery<Filed>();
        FiledQuery.addQueryKeys("rice");
        FiledQuery.findObjects(new FindListener<Filed>() {
            @Override
            public void done(List<Filed> object, BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        options5Items_01.add(object.get(i).getRice());
                    }


                }else{
                    Toast.makeText(BianjitianActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();

                }
            }
        });
        final ArrayList<String> options5Items_02 = new ArrayList<>();
        BmobQuery<Vegetables> VegetablesQuery = new BmobQuery<Vegetables>();
        VegetablesQuery.addQueryKeys("cauliflower");
        VegetablesQuery.findObjects(new FindListener<Vegetables>() {
            @Override
            public void done(List<Vegetables> object, BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        options5Items_02.add(object.get(i).getCauliflower());
                    }


                }else{
                    Toast.makeText(BianjitianActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();

                }
            }
        });
        final ArrayList<String> options5Items_03 = new ArrayList<>();
        BmobQuery<Cucurbit> CucurbitQuery = new BmobQuery<Cucurbit>();
        CucurbitQuery.addQueryKeys("wateermelon");
        CucurbitQuery.findObjects(new FindListener<Cucurbit>() {
            @Override
            public void done(List<Cucurbit> object, BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        options5Items_03.add(object.get(i).getWateermelon());
                    }
                }else{
                    Toast.makeText(BianjitianActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();

                }
            }
        });
        final ArrayList<String> options5Items_04 = new ArrayList<>();
        BmobQuery<Eggplant> EggplantQuery = new BmobQuery<Eggplant>();
        EggplantQuery.addQueryKeys("tomatoes");
        EggplantQuery.findObjects(new FindListener<Eggplant>() {
            @Override
            public void done(List<Eggplant> object, BmobException e) {
                if(e==null){
                    for(int i=0;i<object.size();i++)
                    {
                        options5Items_04.add(object.get(i).getTomatoes());
                    }


                }else{
                    Toast.makeText(BianjitianActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();

                }
            }
        });
        options5Items.add(options5Items_01);
        options5Items.add(options5Items_03);
        options5Items.add(options5Items_04);
        options5Items.add(options5Items_02);

        /*--------数据源添加完毕---------*/
    }
    //时间选择器
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
               textViewtime.setText(getTime(date));
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
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();

    }
    private void initOptionPicker2() {//农作物选择器初始化
        pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options5Items.get(options1).get(options2);
               textViewzhongzhiwu.setText(tx);
            }
        })
                .setTitleText("种植物选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isDialog(true)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();
        pvOptions1.setPicker(options4Items, options5Items);}
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    private void initOptionPicker() {//面积选择器初始化
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx =
                        options1Items.get(options2)+"亩"
                                +options2Items.get(options3)+"分";
               textViewmianji.setText(tx);
            }
        })
                .setTitleText("田面积选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.BLACK)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isDialog(true)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "亩","分")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();
        pvOptions.setNPicker(options3Items,options1Items, options2Items);
    }
}
