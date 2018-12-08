package com.example.john.weinong;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by john on 2018/9/12.
 */
//判断新闻界面滑动时回到顶部按钮是否可见
public class FabScrollListener extends RecyclerView.OnScrollListener {

    private HideScrollListener listener;
    private static final int THRESHOLD = 20;
    private int distance = 0;
    private boolean visible = true;//是否可见
    public FabScrollListener(HideScrollListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (distance > THRESHOLD && visible) {
            //隐藏动画
            Log.i("ggggg",distance+"");
            visible = false;
            listener.onHide();
            distance = 0;
        } else if (distance < -20 && !visible) {
            //显示动画
            visible = true;
            listener.onShow();
            distance = 0;
        }

        if (visible && dy > 0 || (!visible && dy < 0)) {
            distance += dy;
        }
    }

}
