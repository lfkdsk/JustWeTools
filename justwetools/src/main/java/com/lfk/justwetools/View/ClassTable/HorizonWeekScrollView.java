package com.lfk.justwetools.View.ClassTable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by liufengkai on 16/5/21.
 */
public class HorizonWeekScrollView extends HorizontalScrollView {
    public HorizonWeekScrollView(Context context) {
        super(context);
    }

    public HorizonWeekScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
