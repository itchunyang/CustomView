package com.example.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.progressbar.view.LineProgressBar;
import com.example.progressbar.view.RoundProgressBar;
import com.example.progressbar.view.RoundProgressBar01;
import com.example.progressbar.view.TasksCompletedView;

public class MainActivity extends AppCompatActivity {

    private TasksCompletedView tasksCompletedView ;
    private LineProgressBar lineProgressBar;
    private RoundProgressBar roundProgressBar;
    private RoundProgressBar01 roundProgressBar01;

    private Handler handler = new Handler();
    private int mProgress = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tasksCompletedView.setProgress(mProgress);
            lineProgressBar.setProgress(mProgress);
            roundProgressBar.setProgress(mProgress);
            roundProgressBar01.setProgress(mProgress);
            mProgress+=1;
            if(mProgress <=100)
                handler.postDelayed(this,30);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksCompletedView = (TasksCompletedView) findViewById(R.id.taskBar);
        lineProgressBar = (LineProgressBar) findViewById(R.id.lineBar);
        roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        roundProgressBar01 = (RoundProgressBar01) findViewById(R.id.roundProgressBar01);
    }

    public void start(View view) {
        mProgress = 0;
        handler.postDelayed(runnable,30);
    }

    public void stop(View view) {
        handler.removeCallbacks(runnable);
    }
}
