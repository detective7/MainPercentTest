package com.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.test.aidl.IServerHelper;

/**
 * Describe:
 * Created by ys on 2016/10/8 16:59.
 */

public class RemoteService extends Service {

    private final static String TAG=RemoteService.class.getSimpleName();

    private IServerHelper.Stub mServerHelper = new IServerHelper.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return a+b;
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServerHelper;
    }
}
