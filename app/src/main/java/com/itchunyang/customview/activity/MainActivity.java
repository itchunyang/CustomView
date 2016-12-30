package com.itchunyang.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.itchunyang.customview.R;
import com.itchunyang.customview.view.CustomDialog;

//构造函数->onAttachedToWindow()->measure()->onMeasure()->layout()->onLayout()->draw()->onDraw()->dispatchDraw()

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void custom1(View view) {
        startActivity(new Intent(this,CustomViewActivity1.class));
    }

    public void authView(View view) {
        startActivity(new Intent(this,AuthViewActivity.class));
    }

    public void numberProgress(View view) {
        startActivity(new Intent(this,NumberProgressActivity.class));
    }

    public void customProgress(View view) {
        CustomDialog dialog = new CustomDialog(this,"透明对话框");
        dialog.show();
    }

    public void myViewGroup(View view) {
        startActivity(new Intent(this,MyViewGroupActivity.class));
    }

    public void navigationView(View view) {
        startActivity(new Intent(this,NavigationViewActivity.class));
    }

    public void flowLayout(View view) {
        startActivity(new Intent(this,FlowLayoutActivity.class));
    }
}
