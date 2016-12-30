package com.itchunyang.customview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.itchunyang.customview.R;
import com.itchunyang.customview.view.NumberProgress;

public class NumberProgressActivity extends AppCompatActivity {

    private NumberProgress numberProgress;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_progress);
        numberProgress = (NumberProgress) findViewById(R.id.numberProgress);
    }

    public void start(View view) {
        handler.postDelayed(new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                numberProgress.setCurrentProgress((progress+=1));
                if(progress <= 100)
                    handler.postDelayed(this,15);
            }
        },1000);
    }
}
