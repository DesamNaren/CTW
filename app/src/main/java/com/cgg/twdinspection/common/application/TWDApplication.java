package com.cgg.twdinspection.common.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.cgg.twdinspection.common.utils.AppConstants;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TWDApplication extends MultiDexApplication {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Context context;
    private static ExecutorService executor;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    public static TWDApplication get(Context context) {
        return (TWDApplication) context.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public SharedPreferences getPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public SharedPreferences.Editor getPreferencesEditor() {
        if (editor == null) {
            editor = getPreferences().edit();
        }
        return editor;
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public Context getContext() {
        if (context == null) {
            context = getApplicationContext();
        }
        return context;
    }

    public static ExecutorService getExecutorService() {
        if (executor == null)
            executor = Executors.newSingleThreadExecutor();
        return executor;
    }

    public static Handler getHandler() {
        if (handler == null)
            handler = new Handler(Looper.getMainLooper());
        return handler;
    }
}
