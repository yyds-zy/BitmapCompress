package com.yyds.bitmapcompress.app;

import android.app.Application;

import com.yyds.log.BuildConfig;
import com.yyds.log.manager.FlyingManager;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/10
 * Describe:
 */
public class AppApplication extends Application {

    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        FlyingManager.getInstance().initLogFrame(application, BuildConfig.DEBUG,0,5,false);
    }
}