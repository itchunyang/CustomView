package com.itchunyang.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itchunyang.customview.R;

/**
 * Created by luchunyang on 2016/12/29.
 */

public class NavigationLayout extends RelativeLayout implements View.OnClickListener{

    private ImageView backView;
    private TextView titleView;
    private ImageView moreView;
    private ClickCallback clickCallback;

    public NavigationLayout(Context context) {
        super(context);
    }

    public NavigationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_navigation,this,true);
        backView = (ImageView) view.findViewById(R.id.back);
        titleView = (TextView) findViewById(R.id.titleTv);
        moreView = (ImageView) findViewById(R.id.more);
        backView.setOnClickListener(this);
        moreView.setOnClickListener(this);
    }

    public NavigationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.back){
            clickCallback.onBackClick();
        }else if(id == R.id.more){
            clickCallback.onRightClick();
        }

    }

    /**
     * 导航栏点击回调接口
     * </br>如若需要标题可点击,可再添加
     * @author Asia
     *
     */
    public static interface ClickCallback{
        /**
         * 点击返回按钮回调
         */
        void onBackClick();

        void onRightClick();
    }
}
