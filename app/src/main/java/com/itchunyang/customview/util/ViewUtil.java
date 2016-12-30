package com.itchunyang.customview.util;

import android.view.View;

/**
 * Created by luchunyang on 2016/12/21.
 */

public class ViewUtil {
    public static String getDesc(int widthMode, int heightMode, int widthSize, int heightSize) {
        String desc = "[widthMode=";

        if (widthMode == View.MeasureSpec.AT_MOST)
            desc += "AT_MOST";
        else if (widthMode == View.MeasureSpec.EXACTLY)
            desc += "EXACTLY";
        else if (widthMode == View.MeasureSpec.UNSPECIFIED)
            desc += "UNSPECIFIED";

        desc += " widthSize=" + widthSize + " ,heightMode=";

        if (heightMode == View.MeasureSpec.AT_MOST)
            desc += "AT_MOST";
        else if (heightMode == View.MeasureSpec.EXACTLY)
            desc += "EXACTLY";
        else if (heightMode == View.MeasureSpec.UNSPECIFIED)
            desc += "UNSPECIFIED";

        desc += " heightSize=" + heightSize + "]";


        return desc;
    }
}
