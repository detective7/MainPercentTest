package com.ssdy.mytextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivty<T> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) {
            Log.d("EventBus","Register");
            EventBusUtil.register(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!EventBus.getDefault().isRegistered(this)) {
            Log.d("EventBus", "unRegister");
            EventBusUtil.unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(T event) {
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackEvent(T event){
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onASYNCEvent(T event){
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(T event){
    }

}
