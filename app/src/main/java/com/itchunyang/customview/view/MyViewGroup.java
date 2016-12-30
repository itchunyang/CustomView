package com.itchunyang.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luchunyang on 2016/12/28.
 */

public class MyViewGroup extends ViewGroup {

    private final int OFFSET = 100;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() == View.GONE)
                continue;
            LayoutParams layoutParams = child.getLayoutParams();
            //参数解析  1.传进去的父组件的模式 2.希望子View的margin。3.子view的宽高.
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec,0, layoutParams.width );
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec,0,layoutParams.height);
            child.measure(childWidthSpec,childHeightSpec);
        }

        int width = 0,height = 0;

        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    int widthOffset = i * OFFSET + child.getMeasuredWidth();
                    width = Math.max(width,widthOffset);
                }
                break;
        }

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    height = height + child.getMeasuredHeight();
                }
                break;
        }

        setMeasuredDimension(width,height);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left ,top = 0,right,bottom;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            left = i * OFFSET;
            right = left + child.getMeasuredWidth();
            bottom = top + child.getMeasuredHeight();

            child.layout(left,top,right,bottom);
            top += child.getMeasuredHeight() ;
        }
    }

    /**
     * 如果要自定义ViewGroup支持子控件的layout_margin参数，则自定义的ViewGroup类必须重载generateLayoutParams()函数，并且在该函数中返回一个ViewGroup.MarginLayoutParams派生类对象，这样才能使用margin参数
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return super.generateLayoutParams(p);
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }
}
