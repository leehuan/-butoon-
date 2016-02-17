package com.wuyun.myapplication.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LEE on 2016-02-11.
 */
public class HomeTextView extends TextView {
    public HomeTextView(Context context) {
//        super(context);
        this(context, null);
    }

    public HomeTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, null, 0);
    }


    public HomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setFocusableInTouchMode(true);
        setFocusable(true);
        setMarqueeRepeatLimit(-1);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        //当textview没有焦点时候就不去调用父类的焦点
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }
}
