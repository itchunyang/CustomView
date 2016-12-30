package com.itchunyang.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.itchunyang.customview.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunyang on 2016/12/29.
 */

public class FlowLayout extends ViewGroup {

    public static final String TAG = FlowLayout.class.getSimpleName();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.i(TAG, "onMeasure: " + ViewUtil.getDesc(widthMode, heightMode, widthSize, heightSize));
        int childCount = getChildCount();
        int height = 0;
        int currentLineWidth = 0, currentLineHeight = 0, maxLineWidth = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE)
                continue;

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            if (currentLineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                height += currentLineHeight;
                currentLineWidth = childWidth;
                currentLineHeight = childHeight;
            } else {//不换行
                currentLineWidth += childWidth;
                currentLineHeight = Math.max(currentLineHeight, childHeight);
            }

            if (i == (childCount - 1)) {
                maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                height += childHeight;
            }
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : maxLineWidth, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }


    private List<List<View>> allViews = new ArrayList<>();//存储所有的View，按行记录
    private List<Integer> lineHeights = new ArrayList<>();//记录每一行的最大高度

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        allViews.clear();
        lineHeights.clear();

        int width = getWidth();
        int lineWidth = 0, lineHeight = 0;

        //每一行的Views
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
                continue;

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + params.leftMargin + params.rightMargin + lineWidth > width) {
                lineHeights.add(lineHeight);
                allViews.add(lineViews);
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }

            lineWidth += childWidth + params.leftMargin + params.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + params.topMargin + params.bottomMargin);
            lineViews.add(child);

        }

        // 记录最后一行
        lineHeights.add(lineHeight);
        allViews.add(lineViews);

        Log.i(TAG, "onLayout: " + lineHeights.size() + " " + allViews.size());

        int left = 0, top = 0;
        for (int i = 0; i < allViews.size(); i++) {
            lineViews = allViews.get(i);
            lineHeight = lineHeights.get(i);// 当前行的最大高度
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE)
                    continue;

                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
                Log.i(TAG, "onLayout: martop->"+params.topMargin);
                int lc = left + params.leftMargin;
                int tc = top + params.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            }

            left = 0;
            top += lineHeight;
        }
    }
}
