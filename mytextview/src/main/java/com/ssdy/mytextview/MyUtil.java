package com.ssdy.mytextview;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 作者： Ys
 * 日期： 2016/9/16
 * 功能：
 */
public class MyUtil {

    public static String getProcessName(Context context){
        int _pid = android.os.Process.myPid();
        ActivityManager _ActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo:_ActivityManager.getRunningAppProcesses()){
            if(appProcessInfo.pid==_pid){
                return appProcessInfo.processName;
            }
        }
        return null;
    }

}
