package com.ssdy.mytextview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ssdy.Bean.Book;
import com.ssdy.Bean.User;
import com.ssdy.greendao.BookDao;
import com.ssdy.greendao.DaoSession;
import com.ssdy.greendao.UserDao;

import java.util.List;


public class MainActivity extends BaseActivty<MessageEvent> {

    private Button bt1;
    private UserDao _UserDao;
    private BookDao _BookDao;
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) this.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
        initData();
        iv = (ImageView) findViewById(R.id.ima);
        Picasso.with(MyApplication.getContext()).load("http://172.16.2.124:8080/english/rest/show/listenrecord").into(iv);

    }

    private void initData() {
        DaoSession _daoSession = ((MyApplication) getApplication()).getDaoSession();
        _UserDao = _daoSession.getUserDao();
        _BookDao = _daoSession.getBookDao();
        //插入、保存数据：如果key属性不为null，会更新这个对象；如果为null，会插入这个对象：
        if(_UserDao.queryBuilder().where(UserDao.Properties.Name.eq("OK")).build().list().size()==0) {
            User _User = new User(null, "OK", 16, 162, "King");
            _UserDao.insert(_User);
            Book _Book = new Book(null,"King");
            _BookDao.insert(_Book);
        }
        //查询数据
        User _SelectUser = _UserDao.queryBuilder().where(UserDao.Properties.Name.eq("OK")).build().unique();
        //unique()表示查询结果为一条数据，若数据不存在，_SelectUser为null。
        if (_SelectUser!=null) {
            Log.d("GreenDao", _SelectUser.getUser_id() + "");
        }
        //获取多个结果
        List<User> _Users = _UserDao.queryBuilder()
                .where(UserDao.Properties.User_id.notEq(10)) //查询条件
                .orderAsc(UserDao.Properties.User_id) //按首字母排列
                .limit(10)  //限制查询结果个数
                .build().list(); //结果放进list中
        Log.d("GreenDao", _Users.size() + "");

    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
//    @Subscribe(priority = 1,threadMode = ThreadMode.MAIN)
//    public void onMainEvent(MessageEvent event) {
//        bt1.setText(event.message);
//        Log.d("EventBus_thread_main",Thread.currentThread().getName());
//    }

    @Override
    public void onBackEvent(MessageEvent event) {
        super.onBackEvent(event);
        Log.d("EventBus_thread_back", Thread.currentThread().getName());
    }

    /*@Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackEvent(MessageEvent event){
        Log.d("thread_test_background",Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onASYNCEvent(MessageEvent event){
        Log.d("thread_test_Asyn",Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(MessageEvent event){
        Log.d("thread_test_posting",Thread.currentThread().getName());
    }*/

    /**
     * 这里加上了判断，因为在下一个界面跳转回来之后会再次执行onStart
     * 所以要加上判断，以免注册两次，会出错
     * java.lang.RuntimeException: Unable to resume activity {com.ssdy.mytextview/com.ssdy.mytextview.MainActivity}:
     * Cause By ：org.greenrobot.eventbus.EventBusException: Subscriber class com.ssdy.mytextview.MainActivity already registered to event class com.ssdy.mytextview.MessageEvent
     */
//    @Override
//    public void onStart() {
//        super.onStart();
//        if(!EventBus.getDefault().isRegistered(this)) {
//            Log.d("EventBus:","Register");
//            EventBus.getDefault().register(this);
//        }
//    }

    /**
     * 这里我从最初的教程用的onStop换成了onDestroy，因为在跳转到下个Activity会执行onStop
     * 会订阅不到消息
     */
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("EventBus:","unregister");
//        EventBus.getDefault().unregister(this);
//    }
}
