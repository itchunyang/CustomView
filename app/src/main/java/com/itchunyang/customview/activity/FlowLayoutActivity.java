package com.itchunyang.customview.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itchunyang.customview.R;
import com.itchunyang.customview.view.FlowLayout;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
    }

    public void add(View view) {

        try {
            TextView tv = new TextView(this);
            tv.setText("dddddd");
            tv.setTextSize(35);
            tv.setBackground(ContextCompat.getDrawable(this,R.drawable.tv_bg));
            tv.setGravity(Gravity.CENTER);
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 20;
            layoutParams.topMargin = 20;
            flowLayout.addView(tv,layoutParams);
        }catch ( Exception e){
            e.printStackTrace();
        }

    }
}
