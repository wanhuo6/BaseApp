package com.ahuo.myapp2.core.alive;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ahuo.tool.util.MyLog;

/**
 * Created on 2016-10-13.
 *
 * @author GuoNing.
 */
public class KeepAliveActivity extends Activity implements FinishAliveListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.e("onCreate------------");

//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);

        AliveActivityManager.getsInstance().setFinishAliveListener(this);
    }

    @Override
    public void finishAliveActivity() {
        MyLog.e("finishAliveActivity------------");
        finish();
    }

    @Override
    protected void onDestroy() {
        AliveActivityManager.getsInstance().setFinishAliveListener(null);
        super.onDestroy();
    }
}
