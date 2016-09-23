package com.ssdy.mytextview;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.ssdy.greendao.DaoMaster;
import com.ssdy.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * 作者： Ys
 * 日期： 2016/9/16
 * 功能：
 */
public class MyApplication extends Application {

    private static Context context;
    private static final String TAG = "MyApplication";

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        String processName = MyUtil.getProcessName(getApplicationContext());
        Log.d(TAG,"application start,process name:"+processName);
        //创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"library_db");
        //DevOpenHelper helper = new DevOpenHelper(this, 是否加密 ? "notes-db-encrypted" : "notes-db");
        //获取数据库读写的权限，如果进行加密调用helper.getEncryptedWritableDb("super-secret")，参数为设置的密码
        //Database db = 是否加密 ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
    /**
     *获取应用全局上下文
     * */
    public static Context getContext() {
        return context;
    }
}
