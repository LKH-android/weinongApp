package com.example.john.weinong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import com.example.john.weinong.dbService.Land;
import com.example.john.weinong.dbService.Work;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by john on 2018/10/10.
 */

public class Tianxiangqing extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private TextView textName;
    private TextView textNongzuowu;
    private TextView textMianji;
    private TextView textTime;
    private LinearLayout bianjitian;
    private LinearLayout shanchutian;
    private LinearLayout jinongshi;
    private String ObjectId;
    private double jiexila;
    private double jiexilo;
    private int size1;
    private Polygon polygons;
    private UiSettings mUiSettings;
    private Button fab;
    private TextView nongshicount;
    private TextView nongshitime;
private Button backbutton;
private Button quanditubutton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.tianxinxi);
        init();
        baiduMap=mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mUiSettings=baiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        mapView.showScaleControl(false);
        mUiSettings.setAllGesturesEnabled(false);
        final Intent intent=getIntent();
        ObjectId=intent.getStringExtra("objectid");
        jiexi();
        shanchutian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Tianxiangqing.this);
                dialog.setTitle("确认删除");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Land item = new Land();
                        item.setObjectId(ObjectId);
                        item.delete(new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    finish();
                                }else{
                                    Toast.makeText(Tianxiangqing.this,"删除失败",Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
            }
        });
        bianjitian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ObjectId!=null)
                {
                Intent intent1=new Intent();
                intent1.putExtra("OBJECTID",ObjectId);
                intent1.setClass(Tianxiangqing.this,BianjitianActivity.class);
                startActivity(intent1);
                }
                else {
                    return;
                }
            }
        });
        jinongshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent();
                intent2.putExtra("landobjectid",ObjectId);
                intent2.setClass(Tianxiangqing.this,NongshitimelineActivity.class);
                startActivity(intent2);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent();
                intent3.putExtra("LANDOBJECTID",ObjectId);
                intent3.setClass(Tianxiangqing.this,NewnongshiActivityput.class);
                startActivity(intent3);
            }
        });
backbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
quanditubutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent1=new Intent();
        intent1.setClass(Tianxiangqing.this,WeixingActivity.class);
        startActivity(intent1);
    }
});
    }
    private void init()
    {
        mapView=(MapView)findViewById(R.id.tianditu);
        textName=(TextView)findViewById(R.id.tianxingxiname);
        textMianji=(TextView)findViewById(R.id.minajitext);
        textNongzuowu=(TextView)findViewById(R.id.nongzuowtext);
        textTime=(TextView)findViewById(R.id.timetext);
        bianjitian=(LinearLayout)findViewById(R.id.tianxinxibianjitian);
        shanchutian=(LinearLayout)findViewById(R.id.tianxinxishanchutian);
        jinongshi=(LinearLayout)findViewById(R.id.nongshilinearlayout);
         fab=(Button)findViewById(R.id.jinongshibutton);
         nongshicount=(TextView)findViewById(R.id.nongshicount);
         nongshitime=(TextView)findViewById(R.id.nongshitime);
         backbutton=(Button)findViewById(R.id.backbutton);
quanditubutton=(Button)findViewById(R.id.quanditubutton);
    }
    private void jiexi() {
        BmobQuery<Work> workBmobQuery = new BmobQuery<Work>();
        Land land=new Land();
        land.setObjectId(ObjectId);
        workBmobQuery.addWhereEqualTo("landid",land);
        workBmobQuery.order("-updatedAt");
        workBmobQuery.findObjects(new FindListener<Work>() {

            @Override
            public void done(List<Work> object,BmobException e) {
                if(e==null){
                    nongshicount.setText(""+object.size());
                    nongshitime.setText(object.get(0).getOperationtime());
                }else{
                }
            }

        });
        final List<LatLng> dians = new ArrayList<LatLng>();
        if (ObjectId != null) {
            BmobQuery<Land> query = new BmobQuery<Land>();
            query.getObject(ObjectId, new QueryListener<Land>() {
                @Override
                public void done(Land object, BmobException e) {
                    if (e == null) {
                        textName.setText(object.getLandname());
                        textMianji.setText(object.getLandarea());
                        textNongzuowu.setText(object.getCrop());
                        textTime.setText(object.getTime());
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
                        Toast.makeText(Tianxiangqing.this,"服务器连接失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(Tianxiangqing.this,"发生未知错误",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.getMap().clear();
        jiexi();
    }

}