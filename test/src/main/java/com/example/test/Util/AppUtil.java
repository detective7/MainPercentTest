package com.example.test.Util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Describe:
 * Created by ys on 2016/9/22 10:49.
 */

public class AppUtil {

    public static String getProcess(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

}
