package com.ssdy.mytextview;

import android.app.Application;
import android.util.Log;

/**
 * 作者： Ys
 * 日期： 2016/9/16
 * 功能：
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = MyUtil.getProcessName(getApplicationContext());
        Log.d(TAG,"application start,process name:"+processName);
    }
}
