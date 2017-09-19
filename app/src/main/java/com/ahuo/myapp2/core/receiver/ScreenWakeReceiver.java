package com.ahuo.myapp2.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ahuo.myapp2.core.alive.AliveActivityManager;
import com.ahuo.myapp2.util.MyLog;

/**
 * Created by hly on 2016/10/12.
 * email hly910206@gmail.com
 */

public class ScreenWakeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        MyLog.e("onReceive action:%s", action);

        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {//锁屏
            //启动保活页面
            AliveActivityManager.getsInstance().startAliveActivity(context);
        } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {//亮屏
            //结束保活页面
            AliveActivityManager.getsInstance().finishAliveActivity();
        } else if (TextUtils.equals(action, Intent.ACTION_USER_PRESENT)) {//解锁
        }
    }
}
