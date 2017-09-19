package com.ahuo.myapp2.util;

import android.view.View;

import java.util.Calendar;

/**
 * Created by ahuo on 17-9-19.
 */

public abstract class MyOnClickListener implements View.OnClickListener{
    public static final int MIN_CLICK_DELAY_TIME = 800;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onMyClick(v);
        }
    }

    protected abstract void onMyClick(View v);
}
