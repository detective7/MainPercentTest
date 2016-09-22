package com.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.movable);
//        final Movable rabbit = new Movable(MainActivity.this);
//        rabbit.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v,MotionEvent event){
//                rabbit.bitmapX=event.getX();
//                rabbit.bitmapY=event.getY();
//                rabbit.invalidate();
//                return true;
//            }
//        });
//        relativeLayout.addView(rabbit);

        iv = (ImageView) findViewById(R.id.image);
        Picasso.with(MainActivity.this).load("http://172.16.2.124:8080/english/rest/show/listenrecord").into(iv);
    }
}
