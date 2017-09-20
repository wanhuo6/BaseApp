package com.ahuo.myapp2.core.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ahuo.myapp2.core.receiver.ScreenWakeReceiver;
import com.ahuo.tool.util.MyLog;

/**
 * Created on 2016-10-12.
 *
 * @author GuoNing.
 */
public class AliveService extends Service {

    private final int PID = android.os.Process.myPid();
    private AssistServiceConnection mConnection;
    private ScreenWakeReceiver mScreenWakeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        MyLog.e("GrayService->onCreate");
        super.onCreate();

        setForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.e("GrayService->onStartCommand");

        if (mScreenWakeReceiver == null) {
            mScreenWakeReceiver = new ScreenWakeReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            intentFilter.addAction("com.kk.user.ALARM_ACTION");
            registerReceiver(mScreenWakeReceiver, intentFilter);
        }

        return START_STICKY;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MyLog.e("GrayService->onLowMemory");
        startSelf();
    }

    @Override
    public void onTrimMemory(int level) {
        MyLog.e("GrayService->onTrimMemory");
        super.onTrimMemory(level);
        startSelf();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        MyLog.e("GrayService->onTaskRemoved");
        super.onTaskRemoved(rootIntent);
        startSelf();
    }

    @Override
    public void onDestroy() {
        MyLog.e("GrayService->onDestroy");
        super.onDestroy();
        startSelf();
    }

    public void setForeground() {
        // sdk < 18 , 直接调用startForeground即可,不会在通知栏创建通知
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            this.startForeground(PID, getNotification());
            return;
        }

        if (null == mConnection) {
            mConnection = new AssistServiceConnection();
        }

        this.bindService(new Intent(this, MyAliveService.class), mConnection,
                Service.BIND_AUTO_CREATE);
    }


    private class AssistServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            MyLog.e("MyService: onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MyLog.e("MyService: onServiceConnected");

            // sdk >=18
            // 的，会在通知栏显示service正在运行，这里不要让用户感知，所以这里的实现方式是利用2个同进程的service，利用相同的notificationID，
            // 2个service分别startForeground，然后只在1个service里stopForeground，这样即可去掉通知栏的显示
            Service assistService = ((MyAliveService.LocalBinder) binder)
                    .getService();
            AliveService.this.startForeground(PID, getNotification());
            assistService.startForeground(PID, getNotification());
            assistService.stopForeground(true);

            AliveService.this.unbindService(mConnection);
            mConnection = null;
        }
    }

    private Notification getNotification() {
        return new Notification();
    }

    private void startSelf() {
        if (mScreenWakeReceiver != null) {
            unregisterReceiver(mScreenWakeReceiver);
            mScreenWakeReceiver = null;
        }
        Intent intent = new Intent(getApplicationContext(), AliveService.class);
        startService(intent);
    }
}
