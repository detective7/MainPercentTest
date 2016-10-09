package com.example.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.test.aidl.IServerHelper;

public class AidlActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = AidlActivity.class.getSimpleName();

    private IServerHelper mServiceHelper;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceHelper = IServerHelper.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceHelper=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        findViewById(R.id.btn_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_add_method).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind_service:{
                if(!serviceIsLive()){//创建绑定远程service
                    Intent intent = new Intent(this,RemoteService.class);
                    startService(intent);
                    bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
                }break;
            }
            case R.id.btn_add_method:{
                if(serviceIsLive()){
                    int result = 0;
                    try {
                        result = mServiceHelper.add(1,2);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG,"1+2="+result);
                }break;
            }
        }

    }

    //判断当前绑定的服务是否还存活
    private boolean serviceIsLive() {
        if(mServiceHelper==null){
            return false;
        }
        return true;
    }
}
