package com.ahuo.myapp2.net;

import android.net.ParseException;

import com.ahuo.myapp2.MyApp;
import com.ahuo.myapp2.R;
import com.alibaba.fastjson.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * Created on 17-8-1
 *
 * @author liuhuijie
 */

public class HttpExceptionUtils {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static String handleException(Throwable e) {
        String ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex = MyApp.getInstance().getString(R.string.http_hint_net_protocol_error);  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            ex = MyApp.getInstance().getString(R.string.error_parse_data_error);            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException) {
            ex = MyApp.getInstance().getString(R.string.public_net_connect_error);  //均视为网络错误
            return ex;
        } else {
            ex = MyApp.getInstance().getString(R.string.error_un_know);          //未知错误
            return ex;
        }
    }

}
