package com.ssdy.mytextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) this.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = "onMessageEvent：" + event.message;
        bt1.setText(event.message);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 这里加上了判断，因为在下一个界面跳转回来之后会再次执行onStart
     * 所以要加上判断，以免注册两次，会出错
     * java.lang.RuntimeException: Unable to resume activity {com.ssdy.mytextview/com.ssdy.mytextview.MainActivity}:
     * Cause By ：org.greenrobot.eventbus.EventBusException: Subscriber class com.ssdy.mytextview.MainActivity already registered to event class com.ssdy.mytextview.MessageEvent
     */
    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) {
            Log.d("EventBus:","Register");
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 这里我从最初的教程用的onStop换成了onDestroy，因为在跳转到下个Activity会执行onStop
     * 会订阅不到消息
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("EventBus:","unregister");
        EventBus.getDefault().unregister(this);
    }
}
