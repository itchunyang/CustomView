package com.example.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.progressbar.R;

/**
 * Created by luchunyang on 16/8/25.
 */
public class RainbowBar extends View {

    private int rainbow_dash_width = 60;
    private int rainbow_dash_gap = 10;
    private int rainbow_color = Color.BLUE;
    private int rainbow_height = 5;
    private Paint mPaint;
    private int startX = 0;
    float delta = 10f;

    public RainbowBar(Context context) {
        this(context,null);
    }

    public RainbowBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(attrs == null)
            return;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RainbowBar);

        rainbow_dash_gap = array.getDimensionPixelSize(R.styleable.RainbowBar_rainbow_dashGap,rainbow_dash_gap);
        rainbow_dash_width = array.getDimensionPixelSize(R.styleable.RainbowBar_rainbow_dashWidth,rainbow_dash_width);
        rainbow_color = array.getColor(R.styleable.RainbowBar_rainbow_color,rainbow_color);
        rainbow_height = array.getDimensionPixelSize(R.styleable.RainbowBar_rainbow_Height,rainbow_height);
        array.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(rainbow_color);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(rainbow_height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        System.out.println(getMeasuredWidth() + "|" + getMeasuredHeight());
        int height, width;

        //宽度最大
        width = widthSize;

        if (heightMode == MeasureSpec.EXACTLY)
            height = heightSize;
        else {
            height = 20;
            height = heightSize > height ? height:heightSize;//取最小值
        }

        setMeasuredDimension(width,height);
    }

    //自己绘制
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();

        if(startX >= width + (rainbow_dash_width+rainbow_dash_gap) - (width%(rainbow_dash_gap+rainbow_dash_width))){
            startX = 0;
        }else
            startX += delta;

        int start = startX;
        while (start < width) {
            canvas.drawLine(start, getMeasuredHeight()/2, start + rainbow_dash_width, getMeasuredHeight()/2, mPaint);
            start += rainbow_dash_width + rainbow_dash_gap;
        }

        start = startX - rainbow_dash_gap - rainbow_dash_width;

        while (start >= -rainbow_dash_width){
            canvas.drawLine(start,getMeasuredHeight()/2,start+rainbow_dash_width,getMeasuredHeight()/2,mPaint);
            start -= (rainbow_dash_gap+rainbow_dash_width);
        }

        invalidate();
    }


}
