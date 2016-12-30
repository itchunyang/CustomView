package com.itchunyang.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itchunyang.customview.R;
import com.itchunyang.customview.view.AuthView;

/**
 * View{
 *     onAttachedToWindow()
 *     final measure()
 *     onMeasure()
 *     layout(int l, int t, int r, int b)
 *     onLayout(boolean changed, int left, int top, int right, int bottom)
 *     dispatchDraw(Canvas canvas)
 *     draw(Canvas canvas)
 *     onDraw(Canvas canvas)
 *     onSizeChanged(int w, int h, int oldw, int oldh)
 * }
 *
 *
 * 视图的布局过程是从ViewRoot对象调调用根视图的layout()方法开始，接着layout()方法调用根视图的onLayout()方法,
 * onLayout()方法会对所包含的子视图逐一执行layout操作，如果子视图是ViewGroup子类对象，则继续调用子视图的layout()，
 * 重复这一过程。如果子视图是View子类对象，则在子视图重载的onLayout()内部只需要将自己布局到视图中，不需要对子视图进行layout操作
 *
 */
public class AuthViewActivity extends AppCompatActivity {

    private EditText et;
    private AuthView authView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_view);

        et = (EditText) findViewById(R.id.et);
        authView = (AuthView) findViewById(R.id.authView);

    }

    public void check(View view) {
        if(et.getText().toString().equals(authView.getAuthCode())){
            Toast.makeText(this,"验证码正确",Toast.LENGTH_SHORT).show();
            authView.postInvalidate();
        }else{
            Toast.makeText(this,"验证码错误",Toast.LENGTH_SHORT).show();
        }
        et.getText().clear();
    }
}
