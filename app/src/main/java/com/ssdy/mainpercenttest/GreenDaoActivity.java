package com.ssdy.mainpercenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.bean.User;
import com.example.dao.DaoMaster;
import com.example.dao.DaoSession;
import com.example.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private UserDao userdao;
    private SimpleAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;

    private List<User> _users;

    private Button btn;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        listView = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.addUser);

        helper = new DaoMaster.DevOpenHelper(this, "GreenDaoDemo-db");
        db = helper.getWritableDatabase();
        master = new DaoMaster(db);
        session = master.newSession();

        userdao = session.getUserDao();

        _users = new ArrayList<User>();

        for (String str : userdao.getAllColumns()) {
            Log.d("GreenDaoDemo", str);
        }
        cursor = db.query(userdao.getTablename(), userdao.getAllColumns(), null, null, null, null, null);
        Log.d("GreenDaoDemo", cursor.getCount() + "");
        int i = 0;
        User user = new User();
        while (cursor.moveToNext()) {
//            _users.get(i).setName("1");
//            _users.get(i).setAge(23);
//            _users.get(i).setHigh(170.6d);
            user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            user.setAge(Integer.valueOf(cursor.getString(cursor.getColumnIndex("AGE"))));
            user.setHigh(Double.valueOf(cursor.getString(cursor.getColumnIndex("HIGH"))));
            _users.add(user);
        }
        adapter = new SimpleAdapter(this, _users);
        listView.setAdapter(adapter);

        btn.setOnClickListener(View -> {
            User user2 = new User(null, "user1", 24, 175.5d);
            userdao.insert(user2);
//                adapter.swapCursor(cursor);
        });
    }

}
