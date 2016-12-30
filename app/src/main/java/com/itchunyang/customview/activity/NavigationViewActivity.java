package com.itchunyang.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.itchunyang.customview.R;
import com.itchunyang.customview.view.NavigationLayout;

public class NavigationViewActivity extends AppCompatActivity {

    private NavigationLayout navigationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation_view);

        navigationLayout = (NavigationLayout) findViewById(R.id.navigationLayout);
        navigationLayout.setClickCallback(new NavigationLayout.ClickCallback() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(NavigationViewActivity.this,"more",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
