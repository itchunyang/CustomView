package com.itchunyang.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.itchunyang.customview.R;
import com.itchunyang.customview.util.ViewUtil;

import java.util.Random;

/**
 * Created by luchunyang on 2016/12/22.
 */

public class AuthView extends View {

    public static final String TAG = AuthView.class.getSimpleName();

    private String authText;
    private int authTextColor;
    private int authTextSize;
    private Random random = new Random(System.currentTimeMillis());
    private Rect bound = new Rect();
    private Paint paint;
    private float baseLineY;

    public AuthView(Context context) {
        super(context);
    }

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuthView);
        authTextColor = typedArray.getColor(R.styleable.AuthView_authTextColor, Color.RED);
        authTextSize = typedArray.getDimensionPixelSize(R.styleable.AuthView_authTextSize, 20);
        typedArray.recycle();

        Log.i(TAG, "AuthView: authText=" + authText + " authTextColor=" + authTextColor + " authTextSize=" + authTextSize);
        authText = randomText();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(authTextColor);
        paint.setTextSize(authTextSize);
        paint.getTextBounds(authText,0,authText.length(),bound);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    private String randomText() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: "+ ViewUtil.getDesc(widthMode,heightMode,widthSize,heightSize));

        int width = 0,height = 0;
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width = widthSize + getPaddingLeft() + getPaddingRight();
                break;

            case MeasureSpec.AT_MOST://wrap_content
                width = bound.width() + getPaddingLeft() + getPaddingRight();
                break;
        }

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;

            case MeasureSpec.AT_MOST://wrap_content
                height = bound.height() + getPaddingTop() + getPaddingBottom();
                break;
        }

        setMeasuredDimension(width,height);
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        paint.setColor(authTextColor);
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        baseLineY = (getTop() + getBottom())/2 + (metrics.bottom - metrics.top)/2 - metrics.bottom;

        //如果这么画，那么文本就是整整齐齐的
//        canvas.drawText(authText,(getLeft() + getRight() )/2,baseLineY,paint);

        int dx = 20;
        for (int i = 0; i < 4; i++) {
            canvas.drawText(authText.charAt(i)+"",dx, (float) (getHeight()/2 + Math.random() * 20),paint);
            dx += (getWidth() / 2 - bound.width() / 2) + i / 5 ;
        }

        //绘制一些线段
        paint.setStrokeWidth(5);
        canvas.drawLines(generateLines(2),paint);//2条线段

        //绘制一些小圆点
        canvas.drawPoints(generatePoints(20),paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            postInvalidate();
        }
        return true;
    }

    public String getAuthCode(){
        return authText;
    }

    float[] generateLines(int count){
        float[] lines = new float[count * 4];
        for (int i = 0; i < count * 4 ; i+=2) {
            lines[i] = (float) (Math.random() * getMeasuredWidth());
            lines[i+1] = (float) (Math.random() * getMeasuredHeight());
        }
        return lines;
    }

    float[] generatePoints(int count){
        float[] points = new float[count * 2];

        for (int i = 0; i < count * 2; i+=2) {
            points[i] = (float) (Math.random() * getMeasuredWidth());
            points[i+1] = (float) (Math.random() * getMeasuredHeight());
        }
        return points;
    }

    @Override
    public void invalidate() {
        authText = randomText();
        super.invalidate();
    }
}
