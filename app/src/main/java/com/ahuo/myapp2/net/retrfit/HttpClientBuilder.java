package com.ahuo.myapp2.net.retrfit;

import com.ahuo.myapp2.MyApp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created on 2017-8-1
 *
 * @author LiuHuiJie
 */
public class HttpClientBuilder {

    private static final long TIMEOUT_CONNECT = 5;
    private static final long TIMEOUT_READ = 60;
    private static final long TIMEOUT_WRITE = 60;

    private static OkHttpClient mClient;

    public static OkHttpClient build(){
        if(mClient == null){
            mClient = new OkHttpClient.Builder()
                    .cache(new Cache(new File(MyApp.getInstance().getExternalCacheDir(), "http_cache"), 1024 * 1024 * 100))
                    .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                    .cookieJar(new CookieManager())
                    .addInterceptor(new CommonInterceptor())
                    .retryOnConnectionFailure(true)
                    .build();
        }

        return mClient;
    }


}
