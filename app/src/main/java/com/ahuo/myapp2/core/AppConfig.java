package com.ahuo.myapp2.core;


import com.ahuo.myapp2.BuildConfig;

/**
 * Created on 17-8-1
 *
 * @author liuhuijie
 */

public interface AppConfig {

    String API_HOST = BuildConfig.API_HOST;

    String API_HOST_WEB = BuildConfig.API_HOST+"hello";

    String APP_LOGO="http://www.liuhuijie.cn/image/user/headImage/hua.jpg";

    String APP_LOG_TAG = "MY_APP";

    String APP_SP_NAME = "a_huo_share_data";

}
