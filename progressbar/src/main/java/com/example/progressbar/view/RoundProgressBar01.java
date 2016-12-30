package com.example.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.progressbar.R;

/**
 * 自定义属性：
 * <p>
 * 1. 外层圆的颜色 roundColor
 * <p>
 * 2. 弧形进度圈的颜色 rouncProgressColor
 * <p>
 * 3. 中间百分比文字的颜色 textColor
 * <p>
 * 4. 中间百分比文字的大小 textSize
 * <p>
 * 5. 圆环的宽度（以及作为弧形进度的圆环宽度）
 * <p>
 * 6. 圆环的风格（Paint.Style.FILL  Paint.Syle.Stroke）
 */

public class RoundProgressBar01 extends View {

    private int ring_reach_color;
    private int ring_unreach_color;
    private int ring_width;
    private int text_size;
    private int text_color;
    private Paint textPaint;
    private Paint paint;

    private int width;
    private int height;
    private float progress = 0;
    private float maxProgress = 100;

    public RoundProgressBar01(Context context) {
        this(context, null);
    }

    public RoundProgressBar01(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = null;
        try {
            array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar01);
            ring_reach_color = array.getColor(R.styleable.RoundProgressBar01_ring_reach_color, Color.BLUE);
            ring_unreach_color = array.getColor(R.styleable.RoundProgressBar01_ring_unreach_color, Color.DKGRAY);
            ring_width = array.getDimensionPixelSize(R.styleable.RoundProgressBar01_ring_width, 12);
            text_size = array.getDimensionPixelSize(R.styleable.RoundProgressBar01_text_size, 30);
            text_color = array.getColor(R.styleable.RoundProgressBar01_text_color, Color.BLUE);

            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setDither(true);
            textPaint.setStrokeCap(Paint.Cap.ROUND);
            textPaint.setTextSize(text_size);
            textPaint.setColor(text_color);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
        } finally {
            if (array != null)
                array.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF oval = new RectF(ring_width / 2.0f, 0 +ring_width / 2f ,
                width - ring_width / 2f, height - ring_width / 2f);

        // 第一步：绘制一个圆环
        paint.setColor(ring_unreach_color);
        paint.setStrokeWidth(ring_width);
        canvas.drawArc(oval, 0, 360, false, paint);

        //第二步:绘制文字
        String text = ((int)( progress * 1.0f / maxProgress * 100 )) +"%";
        Rect rect = new Rect();
        textPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,width /2 - rect.width()/2,height / 2 + rect.height() / 2,textPaint);

        // 第三步：绘制动态进度圆环
        paint.setColor(ring_reach_color);
        canvas.drawArc(oval, 0, progress / maxProgress * 360, false, paint);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }
}
