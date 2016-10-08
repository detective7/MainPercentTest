package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AidlActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = AidlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }

    @Override
    public void onClick(View v) {

    }
}
