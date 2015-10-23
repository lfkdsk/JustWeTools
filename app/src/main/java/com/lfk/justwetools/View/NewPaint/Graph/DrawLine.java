package com.lfk.justwetools.View.NewPaint.Graph;

import android.graphics.Canvas;
import android.graphics.Point;

import com.lfk.justwetools.View.NewPaint.PaintView;

/**
 * Created by liufengkai on 15/10/23.
 */
public class DrawLine extends DrawBase {
    private Canvas mCanvas;
    private Point firstPoint = null;
    private Point secondPoint = null;
    private int mX, mY;
    private boolean IsMoved = false;

    public DrawLine(Canvas canvas) {
        this.mCanvas = canvas;
    }

    @Override
    public void Touch_Down(float x, float y) {
        super.Touch_Down(x, y);
        firstPoint = new Point();
        mX = (int) x;
        mY = (int) y;
//        Log.e("==>down", "x:" + x + " " + "y:" + y);
        firstPoint.set(mX, mY);
    }

    @Override
    public void Touch_Up() {
        super.Touch_Up();
        if(IsMoved) {
            mCanvas.drawLine(firstPoint.x, firstPoint.y,
                    secondPoint.x, secondPoint.y,
                    mPaint);
        }
        firstPoint = null;
        secondPoint = null;
        IsMoved = false;
    }

    @Override
    public void Touch_Move(float x, float y) {
        super.Touch_Move(x, y);
        if (Math.abs(mX - x) > PaintView.TOUCH_TOLERANCE
                || Math.abs(mY - y) > PaintView.TOUCH_TOLERANCE) {
//            Log.e("==>move", "x:" + x + " " + "y:" + y);
            mX = (int) x;
            mY = (int) y;
            if(!IsMoved){
                IsMoved = true;
                secondPoint = new Point();
            }
            secondPoint.set(mX, mY);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (IsMoved) {
            canvas.drawLine(firstPoint.x, firstPoint.y,
                    secondPoint.x, secondPoint.y,
                    mPaint);
        }
    }
}
