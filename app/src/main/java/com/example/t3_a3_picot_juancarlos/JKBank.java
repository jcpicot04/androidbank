package com.example.t3_a3_picot_juancarlos;

import android.app.Application;
import android.content.Context;

public class JKBank extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        JKBank.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return JKBank.context;
    }
}
