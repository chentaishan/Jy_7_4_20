package com.example.testyoumeng;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(this, "5ca360b02036571f99001242",null,  UMConfigure.DEVICE_TYPE_PHONE, null);

        UMConfigure.setLogEnabled(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
