package com.example.john.weinong;

import android.app.AlertDialog;
import java.lang.Thread.UncaughtExceptionHandler;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class WeixingActivity extends AppCompatActivity implements View.OnClickListener, BaiduMap.OnMapClickListener, BaiduMap.OnMarkerDragListener,UncaughtExceptionHandler{

    protected Button btnYes;
    protected Button btnfanhui;
    protected MapView map;
    protected ImageView location;
    private BaiduMap baidumap;
    public String zuobiao="|";
    private LocationClient mLocationClient;  //定位相关
    private MyLocationListener mLocationListener;
    private boolean isFirstLocation = true;    //是否第一次定位，如果是第一次定位的话要将自己的位置显示在地图 中间
    private Marker marker;  //marker 相关
    List<Marker> markers = new ArrayList<>();  //算是map的索引，通过此id 来按顺序取出坐标点

    private List<String> ids = new ArrayList<>();
    //用来存储坐标点
    private Map<String, LatLng> latlngs = new HashMap<>();
    private ProgressDialog progressDialog;
    private Polyline mPolyline;    //多边形
    private Polygon polygon;
    private Polygon polygons;
    private double latitude;
    private double longitude;
    private double la;
    private double lo;
    private int size;
    private double jiexila;
    private double jiexilo;
    private int size1;
    private ArrayList<String> options1Items = new ArrayList<>();    //田面积所需的list
    private ArrayList<String> options2Items = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    private ArrayList<String> options4Items=new ArrayList<>();    //农作物分类所需list
    private ArrayList<ArrayList<String>> options5Items=new ArrayList<>();
    private  TextView texttianmianji;
    private OptionsPickerView pvOptions;
    private OptionsPickerView pvOptions1;
    private TextView textzhonzhiwu;
    private TextView texttime;
    private TimePickerView  pvCustomLunar;
    private   String url;
    private BmobFile bmobFile;
    private  Land p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.tian);
        Thread.setDefaultUncaughtExceptionHandler(this);
        p2 = new Land();
        initView();
        getOptionData();
        initOptionPicker();
        initOptionPicker2();
        initLunarPicker();
        jiexi();
        initLocation();//定位
    }

    @Override
    public void onClick(final View view) {
        //确定按钮
        if (view.getId() == R.id.btn_yes) {
            //--------------------------确定多边形的大小和别名-----------------------------
            if(ids.size()>2){
      }
            LatLng l = null;
            la = 0;
            lo = 0;
            size = ids.size();
            if (size <= 2) {
                Toast.makeText(this, "点必须大于2", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < size; i++) {
                l = latlngs.get(ids.get(i));
                la = la + l.latitude;
                lo = lo + l.longitude;
                String a = ids.get(i);
                zuobiao+=latlngs.get(a).latitude+","+latlngs.get(a).longitude+"|";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("田地信息");
            builder.setCancelable(false);
            View inflate = View.inflate(this, R.layout.dialog_aliasname, null);
            final EditText edt_alias = inflate.findViewById(R.id.edt_alias);
            final LinearLayout tianmianji=inflate.findViewById(R.id.tianmianji);
             texttianmianji=inflate.findViewById(R.id.textView16);
             final LinearLayout zhonzhiwu=inflate.findViewById(R.id.zhonzhiwu);
             textzhonzhiwu=inflate.findViewById(R.id.textView18);
             final LinearLayout time=inflate.findViewById(R.id.time);
             texttime=inflate.findViewById(R.id.textView20);
            tianmianji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pvOptions != null){
                    pvOptions.show();}
                }
            });
            zhonzhiwu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pvOptions1!=null)
                    {
                        pvOptions1.show();
                    }
                }
            });
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pvCustomLunar.show();}

            });
            builder.setView(inflate);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   final String trim = edt_alias.getText().toString().trim();
                    if (trim.equals("")||texttianmianji.getText().equals("")||textzhonzhiwu.getText().equals("")) {
                        Toast.makeText(WeixingActivity.this, "请完整填写信息！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    drawPolygon();
                    // 添加文字，求出多边形的中心点向中心点添加文字
                    LatLng llText = new LatLng(la / size, lo / size);

                    Log.i("aaaaaa",""+la/size);
                    OverlayOptions ooText = new TextOptions()
                            .fontSize(24).fontColor(0xFFFF00FF).text(trim + "")
                            .position(llText);
                    baidumap.addOverlay(ooText);
                    for (int j = 0; j < markers.size(); j++) {
                        markers.get(j).remove();
                    }
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(llText);
                    baidumap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.target(llText).build()));
                    latlngs.clear();
                    ids.clear();
                    showProgressDialog();
                    if(polygon!=null)
                    {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            baidumap.snapshotScope(null, new BaiduMap.SnapshotReadyCallback(){
                                @Override
                                public void onSnapshotReady(Bitmap snapshot) {
                                    File file = new File("/mnt/sdcard/testall300.png");
                                    FileOutputStream out;
                                    try {
                                        out = new FileOutputStream(file);
                                        if (snapshot.compress(Bitmap.CompressFormat.PNG, 50, out)) {

                                            out.flush();
                                            out.close();
                                            String picPath = "/mnt/sdcard/testall300.png";
                                            bmobFile = new BmobFile(new File(picPath));
                                            Log.i("ssssss","123");
                                            bmobFile.uploadblock(new UploadFileListener() {

                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        if(Fragment1.user!=null)
                                                        {
                                                        url=bmobFile.getFileUrl();
                                                        Log.i("pppppp",bmobFile.getFileUrl());
                                                        p2.setLocation(zuobiao);
                                                        p2.setLandname(trim);
                                                        p2.setImage(bmobFile);
                                                        p2.setImageurl(url);
                                                        p2.setLandarea(texttianmianji.getText().toString());
                                                        p2.setTime(texttime.getText().toString());
                                                        p2.setCrop(textzhonzhiwu.getText().toString());
                                                        p2.setUser_id(Fragment1.user);
                                                        p2.save(new SaveListener<String>() {

                                                            @Override
                                                            public void done(String objectId,BmobException e) {
                                                                if(e==null){
                                                                    url=null;
                                                                    Log.i("bmob","保存成功");
                                                                    closeProgressDialog();
                                                                    finish();
                                                                }else{
                                                                    Toast.makeText(WeixingActivity.this,"数据上传失败",Toast.LENGTH_SHORT).show();
                                                                    Log.i("bmob","保存失败："+e.getMessage());
                                                                    url=null;
                                                                    closeProgressDialog();
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                                        closeProgressDialog();
                                                        finish();}
                                                    }else{
                                                        Toast.makeText(WeixingActivity.this,"数据上传失败",Toast.LENGTH_SHORT).show();
                                                        Log.i("bmob1","保存失败："+e.getMessage());
                                                        closeProgressDialog();
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onProgress(Integer value) {
                                                    // 返回的上传进度（百分比）
                                                }
                                            });
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }).start();}
                    }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    btnfanhui.setVisibility(View.VISIBLE);
                }
            });
            builder.create().show();
        } else if (view.getId() == R.id.location) {//返回定位的图标
            //点击定位按钮，返回自己的位置
            isFirstLocation = true;
        }else if(view.getId()==R.id.button6){
            if(markers.size()>=1) {
                marker = markers.get(markers.size() - 1);
                Log.i("44444444444", marker.toString());
                marker.remove();
                markers.remove(marker);
                String id1 = marker.getId();
                Log.i("33333333", id1.toString());
                ids.remove(id1);
                latlngs.remove(id1);
                baidumap.hideInfoWindow();
                if (ids.size() < 2) {
                    if (mPolyline != null) {
                        mPolyline.remove();
                    }
                    return;
                }
                drawLine();
            }else {
                Toast.makeText(WeixingActivity.this,"地图上已无标记", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 通过点击地图，来获取坐标
     *
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        //向地图添加marker
        addMarler(latitude, longitude);
        if (ids.size() >= 2) {
            drawLine();
        }
    }
    /**
     * 地图上marker的点击事件
     * @param marker
     * @return
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        String id = marker.getId();
        Log.e("aaaaaa", "id-->" + id);
        double latitude1 = marker.getPosition().latitude;
        double longitude1 = marker.getPosition().longitude;
        //当拖拽完成后，需要把原来存储的坐标给替换掉
        latlngs.remove(id);
        latlngs.put(id, new LatLng(latitude1, longitude1));
        Log.e("aaa", ids.size() + "---拖拽结束后map 的 " + latlngs.size());
        for (int i = 0; i < ids.size(); i++) {
            String s = ids.get(i);
            Log.e("aaa", "key= " + s + " and value= " + latlngs.get(s).toString());
        }
        drawLine();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }
    /**
     * 如果有大于两个点，就画多边形
     */
    private void drawPolygon() {
        LatLng ll = null;
        List<LatLng> pts = new ArrayList<LatLng>();
        for (int i = 0; i < ids.size(); i++) {
            String s = ids.get(i);
            Log.e("77777",latlngs.get(s).toString());
            ll = latlngs.get(s);
            pts.add(ll);
        }
        OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                .stroke(new Stroke(5, 0xAA00FF00)).fillColor(0xAAFFFF00);
        polygon = (Polygon) baidumap.addOverlay(ooPolygon);
    }
    /**
     * 如果此时有两个点，就画线
     */
    private void drawLine() {
        if (mPolyline != null) {
            mPolyline.remove();
        }
        List<LatLng> points = new ArrayList<LatLng>();
        LatLng l = null;
        for (int i = 0; i < ids.size(); i++) {
            l = latlngs.get(ids.get(i));
            points.add(l);
        }
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points);
        mPolyline = (Polyline) baidumap.addOverlay(ooPolyline);
    }
    /**
     * 根据坐标来添加marker
     *
     * @param latitude
     * @param longitude
     */
    private void addMarler(double latitude, double longitude) {
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.point);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                //.zIndex(9)
                .draggable(true);
        //在地图上添加Marker，并显示
        marker = (Marker) baidumap.addOverlay(option);
        markers.add(marker);
        String id = marker.getId();
        latlngs.put(id, new LatLng(latitude, longitude));
        ids.add(id);
    }

    private void initLocation() {
        //定位客户端的设置
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        //注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //配置定位
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        option.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("aaa", "位置：" + location.getLongitude());
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            baidumap.setMyLocationData(data);
            if (isFirstLocation) {
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(15.0f);
                baidumap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                isFirstLocation = false;
                showInfo("位置：" + location.getAddrStr());
            }
        }

    }
    //显示消息
    private void showInfo(String str) {
        Toast.makeText(WeixingActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        baidumap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        baidumap.setMyLocationEnabled(false);
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭定位图层
        baidumap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        map.onDestroy();
        latlngs.clear();
        ids.clear();
        //polygons.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        map.onPause();
    }

    private void initView() {
        btnYes = (Button) findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(WeixingActivity.this);
        location = (ImageView) findViewById(R.id.location);
        location.setOnClickListener(WeixingActivity.this);
        btnfanhui=(Button)findViewById(R.id.button6);
        btnfanhui.setOnClickListener(WeixingActivity.this);
        map = (MapView) findViewById(R.id.mmap);
        baidumap = map.getMap();
        baidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //给marker设置点击事件，用来删除marker
        //给map设置监听事件，用来拿到点击地图的点的坐标
        baidumap.setOnMapClickListener(this);
        //给marker设置拖拽监听事件，用来获取拖拽完成后的坐标
        baidumap.setOnMarkerDragListener(this);
        UiSettings uiSettings=baidumap.getUiSettings();
        uiSettings. setOverlookingGesturesEnabled(false);
        uiSettings.setCompassEnabled(false);
    }
    private void jiexi()
    {
        final List<LatLng> dians = new ArrayList<LatLng>();
if(Fragment1.user!=null){
            BmobQuery<Land> query = new BmobQuery<Land>();
            query.addWhereEqualTo("user_id", Fragment1.user);
            query.order("-updatedAt");

            query.findObjects(new FindListener<Land>() {

                @Override
                public void done(List<Land> object, BmobException e) {
                    if (e == null) {
                        Toast.makeText(WeixingActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                        Log.i("bmob", "成功");
                        for (Land land1 : object) {
                            String str = land1.getLocation();
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
                            String landname = land1.getLandname();
                            OverlayOptions ooPolygon = new PolygonOptions().points(dians)
                                    .stroke(new Stroke(5, 0xAA00FF00)).fillColor(0xAAFFFF00);
                            polygons = (Polygon) baidumap.addOverlay(ooPolygon);
                            size1 = location.length - 1;
                            LatLng llText = new LatLng(jiexila / size1, jiexilo / size1);
                            OverlayOptions textOption = new TextOptions()
                                    .bgColor(0xAAFFFF00)
                                    .fontSize(24)
                                    .fontColor(0xFFFF00FF)
                                    .text(landname)
                                    .position(llText);
                            baidumap.addOverlay(textOption);
                            jiexila = 0;
                            jiexilo = 0;
                            dians.clear();
                        }
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage());
                    }
                }

            });
        }}

    private void initOptionPicker() {//条件选择器初始化
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx =
                        options1Items.get(options2)+"亩"
                                +options2Items.get(options3)+"分";
                texttianmianji.setText(tx);
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
    private void initOptionPicker2() {//条件选择器初始化
        pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options5Items.get(options1).get(options2);
                textzhonzhiwu.setText(tx);
            }
        })
                .setTitleText("种植物选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
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
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();
        pvOptions1.setPicker(options4Items, options5Items);}
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
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
                texttime.setText(getTime(date));
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
                    Toast.makeText(WeixingActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WeixingActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WeixingActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WeixingActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WeixingActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
options5Items.add(options5Items_01);
options5Items.add(options5Items_03);
options5Items.add(options5Items_04);
options5Items.add(options5Items_02);
        /*--------数据源添加完毕---------*/
    }
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        //在此处理异常， arg1即为捕获到的异常
        Log.i("AAA", "uncaughtException   " + arg1);
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(WeixingActivity.this);
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
}
