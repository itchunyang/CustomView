package com.example.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.example.progressbar.R;

/**
 * Created by luchunyang on 16/8/25.
 */
public class RoundProgressBar extends ProgressBar {

    private int progress_text_color = 0xfffc00d1;
    private int progress_text_size = 10;
    private int progress_unreach_color = Color.YELLOW;
    private int progress_unreach_height = 1;
    private int progress_reach_color = progress_text_color;
    private int progress_reach_height = 2;
    private int radius = 30;
    private int maxWidth;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineProgressBar);

        progress_text_color = array.getColor(R.styleable.RoundProgressBar_progress_text_color, progress_text_color);
        progress_text_size = (int) array.getDimension(R.styleable.RoundProgressBar_progress_text_size, progress_text_size);
        progress_unreach_height = (int) array.getDimension(R.styleable.RoundProgressBar_progress_unreach_height, progress_unreach_height);
        progress_unreach_color = array.getColor(R.styleable.RoundProgressBar_progress_unreach_color, progress_unreach_color);
        progress_reach_height = (int) array.getDimension(R.styleable.LineProgressBar_progress_reach_height, progress_reach_height * 2);
        progress_reach_color = array.getColor(R.styleable.RoundProgressBar_progress_reach_color, progress_reach_color);
        radius = array.getDimensionPixelSize(R.styleable.RoundProgressBar_radius, radius);

        mPaint.setTextSize(progress_text_size);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = Math.max(progress_reach_height, progress_unreach_height);
        //默认四个padding一致 radius*2 是圆环中间线的宽度!所以maxWidth 才等于Math.max(progress_reach_height, progress_unreach_height);
        int expect = radius * 2 + maxWidth + getPaddingLeft() + getPaddingRight();

        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);

        //取最小值为半径
        int req = Math.min(width, height);

        //重新计算圆心,因为可能宽和高不一样
        radius = (req - getPaddingLeft() - getPaddingRight() - maxWidth) / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent())/2;

        canvas.save();

        canvas.translate(getPaddingLeft()+maxWidth / 2,getPaddingTop()+maxWidth /2);
        mPaint.setStyle(Paint.Style.STROKE);
        //draw unreach
        mPaint.setColor(progress_unreach_color);
        mPaint.setStrokeWidth(progress_unreach_height);
        canvas.drawCircle(radius,radius,radius, mPaint);

        //draw reach
        mPaint.setColor(progress_reach_color);
        mPaint.setStrokeWidth(progress_reach_height);
        float sweepAngle = getProgress() * 1.0f / getMax()   * 360;
        canvas.drawArc(new RectF(0,0,radius*2,radius*2),0,sweepAngle,false,mPaint);

        //draw text
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(progress_text_color);
        canvas.drawText(text,radius - textWidth /2,radius - textHeight, mPaint);

        canvas.restore();
    }
}
