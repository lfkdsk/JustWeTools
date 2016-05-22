package com.lfk.justwetools.View.ClassTable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

@SuppressLint("ClickableViewAccessibility")
public class HorizonAtScrollView extends HorizontalScrollView {

    public HorizonAtScrollView(Context context) {
        this(context, null);
    }

    public HorizonAtScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizonAtScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

}