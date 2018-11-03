package com.example.john.weinong;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by john on 2018/10/22.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;
    public CustomLinearLayoutManager(Context context) {
        super(context);
    }
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}


