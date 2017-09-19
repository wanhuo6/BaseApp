package com.ahuo.myapp2.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ahuo on 17-9-19.
 */

public class NetWorkUtils {
    private static final int NETTYPE_WIFI = 1;
    private static final int NETTYPE_MOBILE = 2;
    private static final int NETTYPE_ETHERNET = 3;

    /**
     * 判断网络是否可以用
     *
     * @param context Context上下文
     * @return boolean值.
     */
    public static boolean isNetConnect(Context context) {
        int net_status = getNetworkType(context);
        if (net_status == 0) {
            return false;
        }
        return true;
    }


    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return 0;
        }

        int nType = networkInfo.getType();

        switch (nType) {
            case ConnectivityManager.TYPE_MOBILE: {
                return NETTYPE_MOBILE;
            }
            case ConnectivityManager.TYPE_WIFI: {
                return NETTYPE_WIFI;
            }
            case ConnectivityManager.TYPE_ETHERNET: {
                return NETTYPE_ETHERNET;
            }
            default:
                return 0;
        }
    }
}
