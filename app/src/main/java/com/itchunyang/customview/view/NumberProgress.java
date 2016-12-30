package com.itchunyang.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.itchunyang.customview.R;

/**
 * Created by luchunyang on 2016/12/26.
 */

public class NumberProgress extends View {

    public static final String TAG = NumberProgress.class.getSimpleName();

    private float textSize;
    private int textColor;
    private int reachedColor;
    private float reachedHeight;
    private int unreachedColor;
    private float unreachedHeight;
    private int currentProgress;
    private int maxProgress;

    private Paint textPaint;
    private Paint reachedPaint;
    private Paint unreachedPaint;

    private String text;
    private float baseLineX, baseLineY;
    private boolean drawReached = false;
    private boolean drawUnreached = false;
    private RectF reachedF = new RectF();
    private RectF unreachedF = new RectF();

    public NumberProgress(Context context) {
        super(context);
    }

    public NumberProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberProgress);
        textSize = sp2px(context, array.getDimension(R.styleable.NumberProgress_textSize, 10));
        textColor = array.getColor(R.styleable.NumberProgress_textColor, Color.RED);
        reachedColor = array.getColor(R.styleable.NumberProgress_reachedColor, Color.BLUE);
        reachedHeight = dp2px(context, array.getDimension(R.styleable.NumberProgress_reachedHeight, 1.25f));
        unreachedColor = array.getColor(R.styleable.NumberProgress_unreachedColor, Color.YELLOW);
        unreachedHeight = dp2px(context, array.getDimension(R.styleable.NumberProgress_unreachedHeight, 0.75f));

        currentProgress = array.getInt(R.styleable.NumberProgress_currentProgress,0);
        maxProgress = array.getInt(R.styleable.NumberProgress_maxProgress,100);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        reachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        reachedPaint.setColor(reachedColor);

        unreachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unreachedPaint.setColor(unreachedColor);
        array.recycle();

        Log.i(TAG, "NumberProgress: defaultProgress=" + currentProgress + " maxProgress=" + maxProgress);
    }

    public NumberProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    float sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    int measureWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        int padding = getPaddingLeft() + getPaddingRight();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (textSize + padding);
        }

        return result;
    }

    int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        int padding = getPaddingBottom() + getPaddingTop();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = Math.max((int) textSize, Math.max((int) reachedHeight, (int) unreachedHeight)) + padding;
        }

        return result;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        if (currentProgress >= 0 && currentProgress <= maxProgress) {
            this.currentProgress = currentProgress;
            invalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        calcPosition();

        if (drawReached) {
            canvas.drawRect(reachedF, reachedPaint);
        }

        canvas.drawText(text,baseLineX,baseLineY,textPaint);

        if(drawUnreached){
            canvas.drawRect(unreachedF,unreachedPaint);
        }
    }

    /**
     * 1.进度为0的时候。当进度为0的时候reached是不用绘制的，需要绘制文字和unReached部分
     * 2.进度不为0的时候。当进度不为0的时候，3个部分都需要绘制。
     * 3.当reached部分和文字部分大于等于控件的宽度的时候。这个时候 unReached 部分不需要绘制，且reached的右边的位置为整个宽度减去文字宽度。
     */

    void calcPosition() {
        text = String.format("%d", currentProgress * 100 / maxProgress) + "%";
        float textWidth = textPaint.measureText(text, 0, text.length());

        if (currentProgress == 0) {
            drawReached = false;
            baseLineX = getPaddingLeft();
        } else {
            drawReached = true;
            reachedF.left = getPaddingLeft();
            reachedF.top = getHeight() / 2.0f - reachedHeight / 2.0f;
            reachedF.bottom = getHeight() / 2.0f + reachedHeight / 2.0f;
            reachedF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (maxProgress * 1.0f) * (float) currentProgress;
            baseLineX = reachedF.right;
        }

        if(baseLineX + textWidth >= (getWidth() - getPaddingRight())){
            baseLineX = getWidth() - getPaddingRight() - textWidth;
            reachedF.right = baseLineX;
        }

        baseLineY =  (int) ((getHeight() / 2.0f) - ((textPaint.descent() + textPaint.ascent()) / 2.0f));
        float unreachedX = reachedF.right + textWidth;
        if(unreachedX >= getWidth() - getPaddingRight()){
            drawUnreached = false;
        }else{
            drawUnreached = true;
            unreachedF.left = unreachedX;
            unreachedF.right = getWidth() - getPaddingRight();
            unreachedF.bottom = getHeight() / 2f + unreachedHeight / 2f;
            unreachedF.top = getHeight() / 2f - unreachedHeight / 2f;
        }


    }

}
