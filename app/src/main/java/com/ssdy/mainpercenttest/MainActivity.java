package com.ssdy.mainpercenttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private ListView mList;
    private Button btn;

    Observer<String> _Observer;
    Observable<String> _Observable;

    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.nextPage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
            }
        });

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

        /**
         * As these methods are often left unused, you also have the option of
         * using the Action1 interface, which contains a single method named call.
         * 意思是，如果你不用到onError和onCompleted，可以直接使用以下这个返单个call的方法
         */
        Action1<String> _Action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("OBSERVER_Action", s);
            }
        };
        _Subscription = _Observable.subscribe(_Action1);

        /**
         * To detach an observer from its observable while the observable is
         * still emitting data, you can call the unsubscribe method on the Subscription object.
         * 解除绑定（原意是把在观察observable的observer分离），如果被观察着还在不断的发消息，可以用订阅者的unsubscribe方法取消继续发送
         */
        _Subscription.unsubscribe();

        /**
         * use ReactiveX’s operators that can create,
         * transform, and perform other operations on observables
         */
        /**
         * FROM OPERATOR:one that emits items from an array of Integer objects.
         * To do so, you have to use the from operator,
         * which can generate an Observable from arrays and lists.
         * from操作符，可以将一组数据和列表,自动生成一个Observable（不用绑定）
         */
        Observable<Integer> _ArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});
        _ArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("OBSERVER_Action2", String.valueOf(integer));
            }
        });

        /**
         * Java 7 doesn’t have lambdas and higher-order functions, we’ll have to do it with classes that
         * simulate lambdas. To simulate a lambda that takes one argument,
         * you will have to create a class that implements the Func1 interface.
         * Java 8 已经实现了lambda表达式，即隐式函数或者说高阶函数？的能，比如，会将上面的new变成(Action1) (integer) -> {具体实现}这样
         * 以下我们用完整的方式，实现这种功能<输入的值类型，返回的值类型>
         */
        _ArrayObservable
                .map((Integer) -> {
                    return Integer * Integer;// Square the number
                }).subscribe((Integer) -> {
            Log.d("OBSERVER_Action3", String.valueOf(Integer));
        });

        /**
         * Operators can be chained. For example,
         * the following code block uses the skip operator to skip the first two numbers,
         * and then the filter operator to ignore odd numbers:
         * skip操作符跳过前两个数，然后继续执行剩下的数
         */
        _ArrayObservable
                .skip(2) // Skip the first two items
                .filter((Integer) -> {// Ignores any item that returns false
                    return Integer % 2 == 0;
                })
                .subscribe((Integer) -> {
                    Log.d("OBSERVER_Action4", String.valueOf(Integer));

                });

    }

}