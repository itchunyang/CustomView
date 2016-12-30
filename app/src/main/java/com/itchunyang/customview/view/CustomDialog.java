package com.itchunyang.customview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.itchunyang.customview.R;

/**
 * Created by luchunyang on 2016/12/28.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context,String text) {
        super(context,R.style.CustomDialog);

//        setContentView(R.layout.dialog_view);
        getWindow().setContentView(R.layout.dialog_view);
        TextView tv = (TextView) findViewById(R.id.tvcontent);
        tv.setText(text);

        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.9f;
        getWindow().setAttributes(layoutParams);
        setCancelable(false);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(isShowing())
                    dismiss();
                break;
        }
        return true;
    }
}
