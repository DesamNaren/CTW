package com.cgg.twdinspection.common.application;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.cgg.twdinspection.common.utils.AppConstants;
import com.google.gson.Gson;

public class TWDApplication extends MultiDexApplication {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Context context;

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
}
