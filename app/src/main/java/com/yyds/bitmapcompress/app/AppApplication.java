package com.yyds.bitmapcompress.app;

import android.app.Application;
import android.util.Log;

import com.yyds.bitmapcompress.util.SharedPreferenceUtil;
import com.yyds.log.BuildConfig;
import com.yyds.log.manager.FlyingManager;
import com.yyds.log.util.LogUtils;

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
        int mode = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MODE);
        int quality = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_QUALITY);
        int proportion = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MAX_PROPORTION);
        if (mode == 0) {
            SharedPreferenceUtil.getInstance().putInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MODE,1);
        }
        if (quality == 0) {
            SharedPreferenceUtil.getInstance().putInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_QUALITY,80);
        }
        if (proportion == 0)  {
            SharedPreferenceUtil.getInstance().putInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MAX_PROPORTION,800);
        }
    }
}