package com.example.jy_7_4_20;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }
}
