package com.ahuo.myapp2.net.retrfit;


import com.ahuo.myapp2.MyApp;
import com.ahuo.myapp2.util.MyLog;
import com.ahuo.myapp2.util.NetWorkUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 2017-8-1
 *
 * @author LiuHuiJie
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder newBuilder = originalRequest.newBuilder();
        Request compressedRequest;
        if (!NetWorkUtils.isNetConnect(MyApp.getInstance())) {
            newBuilder.cacheControl(CacheControl.FORCE_CACHE);//从缓存中读取
        } else {
            newBuilder.cacheControl(CacheControl.FORCE_NETWORK);
        }

        newBuilder.header("User-Agent", "KKZBPOS/Android");

        compressedRequest = newBuilder.build();

        Response response = chain.proceed(compressedRequest);

        printRequest(originalRequest);

        if (NetWorkUtils.isNetConnect(MyApp.getInstance())) {
            int maxAge = 60 * 60; // 有网络时 设置缓存超时时间一小时
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public, max-age=" + maxAge)//设置缓存超时时间
                    .build();
        } else {
            int maxStale = 60 * 5; // 无网络时，设置超时为5分钟
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    //设置缓存策略，及超时策略
                    .build();
        }

        return response;
    }

    private void printRequest(Request request) throws UnsupportedEncodingException {
        MyLog.e("request method:" + request.method());

        //打印GET请求参数
        Set<String> set = request.url().queryParameterNames();
        for (String s : set) {
            MyLog.e("request key:" + s
                    + " --> value:" + request.url().queryParameterValues(s).toString());
        }

        //打印FormBody类型参数
        if (request.body() instanceof FormBody) {
            FormBody oldFormBody = (FormBody) request.body();
            for (int i = 0; i < oldFormBody.size(); i++) {
                MyLog.e("request key:" + URLDecoder.decode(oldFormBody.encodedName(i), "utf-8")
                        + " --> value:" + URLDecoder.decode(oldFormBody.encodedValue(i), "utf-8"));
            }
        }
    }

}
