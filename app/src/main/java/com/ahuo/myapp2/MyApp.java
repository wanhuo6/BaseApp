package com.ahuo.myapp2;

import android.app.Application;

import com.ahuo.myapp2.core.AppConfig;
import com.ahuo.tool.util.MyLog;
import com.ahuo.tool.util.ToastUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ahuo on 17-9-19.
 */

public class MyApp extends Application {

    public static MyApp mApplication;

    public static MyApp getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        MyLog.init(BuildConfig.MY_LOG, AppConfig.APP_LOG_TAG);
        ToastUtil.init(this);
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

}
