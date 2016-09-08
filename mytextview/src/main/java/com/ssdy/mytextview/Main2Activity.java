package com.ssdy.mytextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class Main2Activity extends AppCompatActivity {

    private Button bt2;
    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt2 = (Button) this.findViewById(R.id.bt2);
        et1 = (EditText) this.findViewById(R.id.et1);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et1.getText().toString();
                if (str == null || str.equals("")) {
                    Toast.makeText(Main2Activity.this, "editText is null", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new MessageEvent(""));
                } else
                    EventBus.getDefault().post(new MessageEvent(str));
            }
        });
    }

}
