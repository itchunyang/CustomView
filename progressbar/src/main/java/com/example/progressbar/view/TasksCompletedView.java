package com.example.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.progressbar.R;

/**
 * Created by luchunyang on 16/8/25.
 */
public class TasksCompletedView extends View {

    private int taskRadius = 150;
    private int taskColor = Color.BLUE;
    private int ringColor = Color.RED;
    private int textColor = Color.WHITE;
    private int textSize = 30;

    private Paint mCirclePaint;
    private Paint mRingPaint;
    private Paint mTextPaint;

    //圆心坐标
    private int mXCenter;
    private int mYCenter;

    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;

    private float baseLine = 0;
    private float mRingRadius;


    public TasksCompletedView(Context context) {
        this(context,null);
    }

    public TasksCompletedView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TasksCompletedView);
        taskRadius = array.getDimensionPixelSize(R.styleable.TasksCompletedView_task_radius,taskRadius);
        taskColor = array.getColor(R.styleable.TasksCompletedView_task_color,taskColor);
        ringColor = array.getColor(R.styleable.TasksCompletedView_task_ring_color,ringColor);
        textColor = array.getColor(R.styleable.TasksCompletedView_task_text_color,textColor);
        textSize = array.getDimensionPixelSize(R.styleable.TasksCompletedView_task_text_size,textSize);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(ringColor);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
        int req = Math.min(width,height);

        mXCenter = req / 2;
        mYCenter = req / 2;
        mCirclePaint.setColor(taskColor);
        canvas.drawCircle(mXCenter,mYCenter,taskRadius,mCirclePaint);

        if(mProgress >= 0 && mProgress <= 100) {
            //环中线的半径
            mRingRadius = taskRadius + (req/2 - taskRadius)/ 2;
            mRingPaint.setStrokeWidth(req/2 - taskRadius);

            RectF rect = new RectF();
            rect.left = mXCenter - mRingRadius;
            rect.top = mYCenter - mRingRadius;
            rect.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            rect.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);

            canvas.drawArc(rect, -90, ((float)mProgress / mTotalProgress) * 360, false, mRingPaint);

            String text = mProgress + "%";
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();

            baseLine = (rect.bottom + rect.top - metrics.bottom - metrics.top)/2;
            canvas.drawText(text,mXCenter,baseLine,mTextPaint);
        }
    }

    public void setProgress(int progress){
        mProgress = progress;
        postInvalidate();
    }
}
