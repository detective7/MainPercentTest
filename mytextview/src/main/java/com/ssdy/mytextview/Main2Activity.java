package com.ssdy.mytextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ssdy.Bean.Site;
import com.ssdy.Bean.User;
import com.ssdy.greendao.DaoSession;
import com.ssdy.greendao.SiteDao;
import com.ssdy.greendao.UserDao;

public class Main2Activity extends AppCompatActivity {

    private Button bt2,bt3;
    private EditText et1;
    private UserDao _UserDao;
    private SiteDao _SiteDao;
    static int a=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt2 = (Button) this.findViewById(R.id.bt2);
        et1 = (EditText) this.findViewById(R.id.et1);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession _daoSession = ((MyApplication) getApplication()).getDaoSession();
                _UserDao = _daoSession.getUserDao();
                _SiteDao = _daoSession.getSiteDao();
                User _User = new User(null,"name"+a,16+a,168+a,null);
                _UserDao.save(_User);
                Site site = new Site(null,1L);
                _SiteDao.save(site);
                a++;
//                String str = et1.getText().toString();
//                if (str == null || str.equals("")) {
//                    Toast.makeText(Main2Activity.this, "editText is null", Toast.LENGTH_SHORT).show();
//                } else {
////                    EventBus.getDefault().post(new MessageEvent(str));
//                    EventBusUtil.postEvent(new MessageEvent(str));
//                    Main2Activity.this.finish();
//                }
            }
        });

        bt3=(Button)this.findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et1.getText().toString();
                if (str == null || str.equals("")) {
                    Toast.makeText(Main2Activity.this, "editText is null", Toast.LENGTH_SHORT).show();
                } else {
//                    EventBus.getDefault().postSticky(new MessageEvent(str));
                    EventBusUtil.postStickyEvent(new MessageEvent(str));
                    startActivity(new Intent(Main2Activity.this,Main3Activity.class));
                }
            }
        });
    }

}
