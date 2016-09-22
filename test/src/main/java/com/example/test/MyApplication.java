package com.example.test;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.example.test.Util.AppUtil;

/**
 * Describe:
 * Created by ys on 2016/9/22 10:48.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Ys", "application start, process name:"+AppUtil.getProcess(getApplicationContext(), Process.myPid()));
    }
}
