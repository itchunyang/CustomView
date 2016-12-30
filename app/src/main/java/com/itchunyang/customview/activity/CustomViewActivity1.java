package com.itchunyang.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itchunyang.customview.R;
import com.itchunyang.customview.view.CustomView1;

public class CustomViewActivity1 extends AppCompatActivity {

    private CustomView1 customView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view1);

        customView1 = (CustomView1) findViewById(R.id.customView1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        customView1.start();
    }

}
