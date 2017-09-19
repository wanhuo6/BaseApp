package com.ahuo.myapp2.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created on 2016-10-12.
 *
 * @author GuoNing.
 */
public class MyAliveService extends Service {

    public class LocalBinder extends Binder {
        public MyAliveService getService() {
            return MyAliveService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }
}