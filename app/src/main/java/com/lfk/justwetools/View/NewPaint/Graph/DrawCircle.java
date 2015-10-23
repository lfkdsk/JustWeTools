package com.lfk.justwetools.View.NewPaint.Graph;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by liufengkai on 15/10/23.
 */
public class DrawCircle extends DrawBase {
    private Point CirclePoint;
    private int radius = 0;
    private Canvas mCanvas;
    public DrawCircle(Canvas canvas) {
        CirclePoint = new Point();
        this.mCanvas = canvas;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(CirclePoint.x, CirclePoint.y, radius, mPaint);
    }

    @Override
    public void Touch_Move(float x, float y) {
        super.Touch_Move(x, y);
        radius = (int) Math.sqrt((x - CirclePoint.x)
                * (x - CirclePoint.x)
                + (y - CirclePoint.y)
                * (y - CirclePoint.y));
        Log.e("r",radius+"");
    }

    @Override
    public void Touch_Up() {
        super.Touch_Up();
        mCanvas.drawCircle(CirclePoint.x, CirclePoint.y, radius, mPaint);
        radius = 0;
    }

    @Override
    public void Touch_Down(float x, float y) {
        super.Touch_Down(x, y);
        int mx = (int) x;
        int my = (int) y;
        CirclePoint.set(mx,my);
    }
}
