package com.itchunyang.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itchunyang.customview.R;

public class MyViewGroupActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private TextView tv;
    public static final String TAG = "LayoutParams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_group);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        Log.i(TAG, "onResume: width=" + layoutParams.width + " height=" + layoutParams.height+" topMargin="+layoutParams.topMargin);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) relativeLayout.getLayoutParams();
        Log.i(TAG, "onResume: "+params);
    }
}
