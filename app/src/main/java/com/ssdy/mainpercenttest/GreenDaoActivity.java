package com.ssdy.mainpercenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.bean.Information;
import com.example.bean.User;
import com.example.dao.DaoMaster;
import com.example.dao.DaoSession;
import com.example.dao.InformationDao;
import com.example.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private UserDao userdao;
    private InformationDao infoDao;
    private SimpleAdapter adapter;
    private SimpleAdapter2 adapter2;
    private Cursor cursor;
    private SQLiteDatabase db;

    private Button btn,btn2;
    private ListView listView,listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        listView = (ListView) findViewById(R.id.listView);
        listView2 = (ListView) findViewById(R.id.listView2);
        btn = (Button) findViewById(R.id.addUser);
        btn2 = (Button) findViewById(R.id.addInfo);

        //这里是获取数据库对应的session
        helper = new DaoMaster.DevOpenHelper(this, "GreenDaoDemo-db");
        db = helper.getWritableDatabase();
        master = new DaoMaster(db);
        session = master.newSession();

        //然后获取操作工具
        userdao = session.getUserDao();
        infoDao = session.getInformationDao();

        for (String str : userdao.getAllColumns()) {
            Log.d("GreenDaoDemo", str);
        }

        for (String str : infoDao.getAllColumns()){
            Log.d("GreenDaoDemo", str);
        }


        adapter = new SimpleAdapter(this, getData());
        listView.setAdapter(adapter);
        adapter2=new SimpleAdapter2(this,getInfo());
        listView2.setAdapter(adapter2);

        btn.setOnClickListener(View -> {
            User user = new User(null, "user1", 24+(int)(1+Math.random()*(10)), 175.5d);
            userdao.insert(user);
            adapter.setData(getData());
            adapter.notifyDataSetChanged();
        });
        btn2.setOnClickListener(View ->{
            //TODO 这里需要作判断，会插入重复项
            Information info = new Information(null,"Shenzhen","123",30,1L);
            info.setUser(userdao.load(1L));
//            infoDao.
            infoDao.insert(info);
            Log.d("GreenDaoDemo", "Insert success:" + info.getAddress()+info.getIcon_url()+info.getScore()+info.getUser().getAge());
        });
    }

    //selectAllUser
    public List<User> getData(){
        List<User> _users = new ArrayList<User>();
        cursor = db.query(userdao.getTablename(), userdao.getAllColumns(), null, null, null, null, null);
        Log.d("GreenDaoDemo", cursor.getCount() + "");
        User user;
        while (cursor.moveToNext()) {
            user = new User();
            user.setUserId(cursor.getLong(cursor.getColumnIndex("USER_ID")));
            user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            user.setAge(cursor.getInt(cursor.getColumnIndex("AGE")));
            user.setHigh(cursor.getDouble(cursor.getColumnIndex("HIGH")));
            _users.add(user);
        }
        return _users;
    }

    //selectAllUser
    public List<Information>  getInfo(){
        List<Information> _Informations = new ArrayList<Information>();
        cursor = db.query(infoDao.getTablename(), infoDao.getAllColumns(), null, null, null, null, null);
        Log.d("GreenDaoDemo", cursor.getCount() + "");
        Information info;
        while (cursor.moveToNext()) {
            info = new Information();
            info.setInfoId(cursor.getLong(cursor.getColumnIndex("INFO_ID")));
            info.setAddress(cursor.getString(cursor.getColumnIndex("ADDRESS")));
            info.setIcon_url(cursor.getString(cursor.getColumnIndex("ICON_URL")));
            info.setScore(cursor.getInt(cursor.getColumnIndex("SCORE")));
            info.setUser_id(cursor.getLong(cursor.getColumnIndex("USER_ID")));
            //还自动生成了获取对应User的代码
            Log.d("GreenDaoDemo", info.getUser().getName() + "");
            _Informations.add(info);
        }
        return _Informations;
    }

}
