package com.example.john.weinong;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.john.weinong.util.HttpUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by john on 2018/8/23.
 */

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView winddir;
    private TextView windsc;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    public String locationname;
    private Button navButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherLayout=(ScrollView)findViewById(R.id.weather_layout);
        titleCity=(TextView)findViewById(R.id.title_city);
        titleUpdateTime=(TextView)findViewById(R.id.title_update_time);
        degreeText=(TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_layout);
        winddir=(TextView)findViewById(R.id.aqi_text);
        windsc=(TextView)findViewById(R.id.pm25_text);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carWashText=(TextView)findViewById(R.id.car_wash_text);
        sportText=(TextView)findViewById(R.id.sport_text);
        bingPicImg=(ImageView)findViewById(R.id.bing_pic_img);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
     locationname=getIntent().getStringExtra("countyname");
        HeConfig.init("HE1808222125071069","aa2601cbaf694f20a2b37aa1ea642b2c");
        HeConfig.switchToFreeServerNode();
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                        requestWeather(locationname);
            }
        });
        if(locationname!=null)
        {
            requestWeather(locationname);
           loadBingPic();}
       }
        public void requestWeather(final String location){
            HeWeather.getWeather(WeatherActivity.this,location, new HeWeather.OnResultWeatherDataListBeansListener() {
                @Override
                public void onError(Throwable throwable) {
                    Toast.makeText(WeatherActivity.this,"获取服务器信息失败", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                }

                @Override
                public void onSuccess(List<Weather> list) {
                    Gson gson=new Gson();
                    String weathertext1= gson.toJson(list);
                    String weathertext2=weathertext1.substring(1,weathertext1.length()-1);
                    final WeatherBean weatherBean=gson.fromJson(weathertext2,WeatherBean.class);
                    Log.i("天气信息",weatherBean.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(weatherBean!=null&&"ok".equals(weatherBean.getStatus()))
                            {
                                showWeatherInfo(weatherBean);
                                locationname=weatherBean.getBasic().getLocation();

                            }else {
                                Toast.makeText(WeatherActivity.this,"获取天气信息失败", Toast.LENGTH_SHORT).show();}
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }
            });
            loadBingPic();
        }

    private void showWeatherInfo(WeatherBean weather)
    {
        String cityName=weather.getBasic().getLocation();
        String updateTime=weather.getUpdate().getLoc();
        String degree=weather.getNow().getTmp()+"℃";
        String weatherInfo=weather.getNow().getCond_txt();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for(WeatherBean.daily_forecast forecast:weather.getDaily_forecast()){
            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dataText=(TextView)view.findViewById(R.id.date_text);
            TextView infoText=(TextView)view.findViewById(R.id.info_text);
            TextView maxText=(TextView)view.findViewById(R.id.max_text);
            TextView minText=(TextView)view.findViewById(R.id.min_text);
            dataText.setText(forecast.getDate());
            infoText.setText(forecast.getCond_txt_d());
            maxText.setText(forecast.getTmp_max());
            minText.setText(forecast.getTmp_min());
            forecastLayout.addView(view);
        }
        if(weather.getNow().getWind_dir()!=null){
            winddir.setText(weather.getNow().getWind_dir());
            windsc.setText(weather.getNow().getWind_sc());
        }
        for(WeatherBean.lifestyle lifestyle:weather.getLifestyle()) {
           if(lifestyle.getType().toString().equals("comf"))
           {String comfort = "舒适度：" +lifestyle.getTxt();
               comfortText.setText(comfort);
           }
           if(lifestyle.getType().toString().equals("uv")){
               String airsun="紫外线强度: "+lifestyle.getTxt();
               carWashText.setText(airsun);
           }
           if(lifestyle.getType().toString().equals("air")){
               String airc="空气污染扩散条件指数: "+lifestyle.getTxt();
               sportText.setText(airc);
           }
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }
    private void loadBingPic(){
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
             final String bingPic=response.body().string();
             Log.i("999999",bingPic.toString());
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                 }
             });
            }
        });
    }
}
