package com.yyds.bitmapcompress.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.yyds.bitmapcompress.app.AppApplication;

import java.util.concurrent.ConcurrentHashMap;

public class SharedPreferenceUtil {
    public static final String SP_CONFIG = "config";
    //Key
    public static final String KEY_QUALITY = "quality";
    public static final String KEY_MAX_PROPORTION = "proportion";
    public static final String KEY_MODE = "mode";

    private static volatile SharedPreferenceUtil mInstance;
    private SharedPreferenceUtil(){}
    public static SharedPreferenceUtil getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferenceUtil();
                }
            }
        }
        return mInstance;
    }

    private ConcurrentHashMap<String, SharedPreferences> mHaps = new ConcurrentHashMap<>();

    public void putInt(String name, String key, int value) {
        checkForNull(name);
        checkForNull(key);
        getSharePreference(name).edit().putInt(key, value).apply();
    }

    private void checkForNull(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    public SharedPreferences getSharePreference(String name) {
        if (mHaps.contains(name)) {
            return mHaps.get(name);
        } else {
            SharedPreferences sharedPreferences = AppApplication.application.getSharedPreferences(name, Context.MODE_PRIVATE);
            mHaps.put(name, sharedPreferences);
            return sharedPreferences;
        }
    }

    public int getInt(String name, String key) {
        checkForNull(name);
        checkForNull(key);
        return getInt(name, key, 0);
    }

    public int getInt(String name, String key, int def) {
        checkForNull(name);
        checkForNull(key);
        return getSharePreference(name).getInt(key, def);
    }


    public String getString(String name, String key) {
        checkForNull(name);
        checkForNull(key);
        return getString(name, key, null);
    }

    public String getString(String name, String key, String def) {
        checkForNull(name);
        checkForNull(key);
        return getSharePreference(name).getString(key, def);
    }

    public void putString(String name, String key, String value) {
        checkForNull(name);
        checkForNull(key);
        getSharePreference(name).edit().putString(key, value).apply();
    }

    public boolean getBoolean(String name, String key) {
        checkForNull(name);
        checkForNull(key);
        return getBoolean(name, key, false);
    }

    private boolean getBoolean(String name, String key, boolean def) {
        return getSharePreference(name).getBoolean(key, def);
    }

    public void putBoolean(String name, String key, boolean value) {
        getSharePreference(name).edit().putBoolean(key, value).apply();
    }

}
