package com.example.tomek.etapi;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tomek on 28.12.2016.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext(); // Grab the Context you want.
    }

    public static Context getContext() {
        return context;
    }
}