package com.itchunyang.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.itchunyang.customview.R;
import com.itchunyang.customview.util.ViewUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by luchunyang on 2016/12/20.
 */

public class CustomView1 extends View {

    private Paint paint;
    private float startRadius, stopRadius, radius;

    public CustomView1(Context context) {
        super(context);
    }

    /**
     * @param attrs 这个参数的作用是解析 layout_width和layout_height或者什么id啊、padding啊、margin啊之类的属性
     */
    public CustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "CustomView1: ");
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.DKGRAY);

        //AttributeSet 里面包含里所有View的属性(android标准的和自定义的)
        //需要注意的是，如果属性的值是引用，则会都变成了@+数字的字符串，比如背景色是引用@color--》background=@2131361810
        //可以根据你解析出来的id，来获取实际的属性 getResources().getDimension(widthDimensionId)
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            System.out.println(attrs.getAttributeName(i) + "=" + attrs.getAttributeValue(i));
        }


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView1);

        startRadius = typedArray.getDimension(R.styleable.CustomView1_start_radius, 0);
        stopRadius = typedArray.getDimension(R.styleable.CustomView1_stop_radius, 0);

        //也可以吧android:background这个android定义的标准属性，定义在自己的属性里，这样就可以用TypedArray获取value了
//        int color = typedArray.getColor(R.styleable.CustomView1_android_background,0);

        typedArray.recycle();
    }

    public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i(TAG, "draw: ");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.i(TAG, "onMeasure: " + ViewUtil.getDesc(widthMode, heightMode, widthSize, heightSize));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        Log.i(TAG, "getSuggestedMinimumHeight: " + getMinimumHeight());
        return super.getSuggestedMinimumHeight();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        Log.i(TAG, "getSuggestedMinimumWidth: " + getMinimumWidth());
        return super.getSuggestedMinimumWidth();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout: changed=" + changed + " left=" + left + " top=" + top + " right=" + right + " bottom" + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged: w=" + w + " h=" + h + " oldw=" + oldw + " oldh=" + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void start() {
//        ValueAnimator animator = ValueAnimator.ofFloat(startRadius,stopRadius);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                radius = (float) animation.getAnimatedValue();
//                postInvalidate();
//            }
//        });
//        animator.setDuration(2000);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.start();
    }
}
