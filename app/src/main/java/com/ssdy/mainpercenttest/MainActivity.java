package com.ssdy.mainpercenttest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private ListView mList;

    Observer<String> _Observer;
    Observable<String> _Observable;

    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button speakButton = (Button) findViewById(R.id.ibtn_study);
        mList = (ListView) findViewById(R.id.ibtn_analysis);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }

        _Observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("OBSERVER_", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("OBSERVER_", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.d("OBSERVER_onNext", s);
            }
        };
        _Observable = Observable.just("Hello"); // Emits "Hello"
        Subscription _Subscription = _Observable.subscribe(_Observer);//这个操作会经过onNext和onCompleted
        /*As these methods are often left unused, you also have the option of
        using the Action1 interface, which contains a single method named call.
        意思是，如果你根本不用到onError和onCompleted，可以直接使用以下这个返单个call的方法
         */
        Action1<String> _Action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("OBSERVER_Action",s);
            }
        };
        _Subscription = _Observable.subscribe(_Action1);
        /*To detach an observer from its observable while the observable is
        still emitting data, you can call the unsubscribe method on the Subscription object.
        解除绑定（原意是把在观察observable的observer分离），如果被观察着还在不断的发消息，可以用订阅者的unsubscribe方法取消继续发送
         */
        _Subscription.unsubscribe();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.ibtn_study) {

        }
    }


}