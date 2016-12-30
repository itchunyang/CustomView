package com.example.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.example.progressbar.R;

/**
 * Created by luchunyang on 16/8/25.
 * <p>
 * 注意 要在xml里设置  style="?android:attr/progressBarStyleHorizontal" 否则设置进度无效
 */
public class LineProgressBar extends ProgressBar {

    private int progress_text_color = 0xfffc00d1;
    private int progress_text_size = 10;
    private int progress_text_offset = 10;
    private int progress_unreach_color = 0xffd3d6da;
    private int progress_unreach_height = 2;
    private int progress_reach_color = progress_text_color;
    private int progress_reach_height = 2;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int realWidth;

    public LineProgressBar(Context context) {
        this(context, null);
    }

    public LineProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineProgressBar);

        progress_text_size = (int) array.getDimension(R.styleable.LineProgressBar_progress_text_size, progress_text_size);
        progress_text_color = array.getColor(R.styleable.LineProgressBar_progress_text_color, progress_text_color);
        progress_text_offset = (int) array.getDimension(R.styleable.LineProgressBar_progress_text_offset, progress_text_offset);
        progress_unreach_color = array.getColor(R.styleable.LineProgressBar_progress_unreach_color, progress_unreach_color);
        progress_unreach_height = (int) array.getDimension(R.styleable.LineProgressBar_progress_unreach_height, progress_unreach_height);
        progress_reach_color = array.getColor(R.styleable.LineProgressBar_progress_reach_color, progress_reach_color);
        progress_reach_height = (int) array.getDimension(R.styleable.LineProgressBar_progress_reach_height, progress_reach_height);

        mPaint.setTextSize(progress_text_size);
        array.recycle();

        setHorizontalScrollBarEnabled(true);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = measureHeight(heightMeasureSpec);
        //宽度需要用户明确指定,或者是一个明确的值,或者是一个match_parent,不能使wrap_content,所以不需要测量宽度
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);

        realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;

        if (heightMode == MeasureSpec.EXACTLY)
            height = heightSize;
        else {//另外的情况需要自己测试,具体是:去unreach高度 reach高度  和 字体高度三者的最大值!
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            height = getPaddingBottom() + getPaddingTop() + Math.max(Math.max(progress_reach_height, progress_unreach_height), Math.abs(textHeight));

            //不能比最大值大
            if (heightMode == MeasureSpec.AT_MOST)
                height = Math.min(height, heightSize);
        }

        return height;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();

        //把坐标移动到垂直的中间,并且过滤到左边的padding
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        //是否需要绘制unreach
        boolean needDrawUnreach = true;
        //百分比
        float radio = getProgress() * 1.0f / getMax();


        //画reachBar
        String text = getProgress() + "%";

        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        float progressX = radio * realWidth;

        //如果到达最后，则未到达的进度条不需要绘制
        if (progressX + textWidth > realWidth) {
            needDrawUnreach = false;
            progressX = realWidth - textWidth;
        }


        float endX = progressX - progress_text_offset / 2;
        if (endX > 0) {
            mPaint.setColor(progress_reach_color);
            mPaint.setStrokeWidth(progress_reach_height);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        //drawtext
        mPaint.setColor(progress_text_color);

        Paint.FontMetricsInt metricsInt = mPaint.getFontMetricsInt();
        Rect target = canvas.getClipBounds();
        int baseLine = (target.bottom + target.top - metricsInt.bottom - metricsInt.top)/2;
        canvas.drawText(text, progressX, baseLine, mPaint);

        //drawUnreach
        if (needDrawUnreach) {
            float start = progressX + textWidth + progress_text_offset / 2;
            mPaint.setColor(progress_unreach_color);
            mPaint.setStrokeWidth(progress_unreach_height);

            canvas.drawLine(start, 0, realWidth, 0, mPaint);
        }

        canvas.restore();
    }


}
