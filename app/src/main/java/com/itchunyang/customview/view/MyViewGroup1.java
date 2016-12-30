package com.itchunyang.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.itchunyang.customview.util.ViewUtil;

/**
 * Created by luchunyang on 2016/12/29.
 */

public class MyViewGroup1 extends ViewGroup {

    public static final String TAG = MyViewGroup1.class.getSimpleName();

    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.i(TAG, "onMeasure: " + ViewUtil.getDesc(widthMode, heightMode, widthSize, heightSize));

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() == 0)
            setMeasuredDimension(0, 0);
        else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //ViewGroup，宽，高都是wrap_content，根据我们的需求，宽度是子控件的宽度，高度则是所有子控件的总和
            int width = 0, height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
                width = Math.max(width, child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                height = height + child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }

            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int width = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
                width = Math.max(width, child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
            }
            setMeasuredDimension(width, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();

                height = height + child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
            setMeasuredDimension(widthSize, height);
        }

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //ViewGroup本身的布局信息
        Log.i(TAG, "onLayout: " + l + " " + t + " " + r + " " + b);
        int height = 0;
        int count = getChildCount();
        View child;
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int left, top, right, bottom;

            left = layoutParams.leftMargin;
            top = height + layoutParams.topMargin;
            right = left + child.getMeasuredWidth();
            bottom = top + child.getMeasuredHeight();

            height = bottom + layoutParams.bottomMargin;
            child.layout(left, top, right, bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
