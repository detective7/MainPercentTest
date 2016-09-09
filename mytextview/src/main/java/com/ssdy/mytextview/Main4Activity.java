package com.ssdy.mytextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        if (stickyEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMainEvent(MessageEvent event) {
        Toast.makeText(Main4Activity.this, event.message + "_Main4Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            Log.d("EventBus:", "Register");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("EventBus:", "unregister");
        EventBus.getDefault().unregister(this);
    }
}
