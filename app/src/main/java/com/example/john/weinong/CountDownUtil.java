package com.example.john.weinong;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by john on 2018/10/28.
 */
//重新发送验证码倒计时辅助类
public class CountDownUtil extends CountDownTimer {
    private Button mButton;
    //参数说明 button 就是你要实现点击的那个按钮  第二个是你设设置的总时间 第三个参数是间隔时间
    public CountDownUtil(Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }
    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setClickable(false);
        mButton.setText(millisUntilFinished / 1000 +"秒后可发送");
    }
    @Override
    public void onFinish() {
        mButton.setClickable(true);
        mButton.setText("获取");
    }
}
