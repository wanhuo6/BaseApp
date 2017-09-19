package com.ahuo.myapp2.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by ahuo on 17-9-19.
 */

public class ToastUtil {
    private static Context mContext;
    private static String mOldMsg;
    protected static Toast mToast = null;
    private static long mOneTime = 0L;
    private static long mTwoTime = 0L;

    public ToastUtil() {
    }

    public static void init(Context ctx) {
        mContext = ctx;
    }

    public static void showToast(int resId) {
        showToast(mContext.getString(resId));
    }

    public static void showToast(String s) {
        if (!TextUtils.isEmpty(s)) {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, s, Toast.LENGTH_LONG);
                mToast.setGravity(17, 0, 0);
                mToast.show();
                mOneTime = System.currentTimeMillis();
            } else {
                mTwoTime = System.currentTimeMillis();
                if (s.equals(mOldMsg)) {
                    if (mTwoTime - mOneTime > 0L) {
                        mToast.setGravity(17, 0, 0);
                        mToast.show();
                    }
                } else {
                    mOldMsg = s;
                    mToast.setText(s);
                    mToast.setGravity(17, 0, 0);
                    mToast.show();
                }
            }

            mOneTime = mTwoTime;
        }
    }
}
