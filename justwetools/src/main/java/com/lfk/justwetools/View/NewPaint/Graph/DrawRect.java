package com.lfk.justwetools.View.NewPaint.Graph;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.lfk.justwetools.View.NewPaint.PaintView;


/**
 * Created by liufengkai on 15/10/23.
 */
public class DrawRect extends DrawBase {
    private Canvas mCanvas;
    private Point firstPoint;
    private Point secondPoint;
    private int mX, mY;
    private boolean IsMoved = false;
    private Rect rect;

    public DrawRect(Canvas canvas) {
        this.mCanvas = canvas;

    }

    @Override
    public void Touch_Down(float x, float y) {
        super.Touch_Down(x, y);
        rect = new Rect();
        firstPoint = new Point();
        mX = (int) x;
        mY = (int) y;
        firstPoint.set(mX, mY);
    }

    @Override
    public void Touch_Up() {
        super.Touch_Up();
        if (IsMoved) {
            mCanvas.drawRect(rect, mPaint);
        }
        firstPoint = null;
        secondPoint = null;
        IsMoved = false;
        rect = null;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (IsMoved) {
            canvas.drawRect(rect, mPaint);
        }
    }

    @Override
    public void Touch_Move(float x, float y) {
        super.Touch_Move(x, y);
        if (Math.abs(mX - x) > PaintView.TOUCH_TOLERANCE
                || Math.abs(mY - y) > PaintView.TOUCH_TOLERANCE) {
            Log.e("==>move", "x:" + x + " " + "y:" + y);
            if (!IsMoved) {
                IsMoved = true;
                secondPoint = new Point();
            }
            secondPoint.set((int) x, (int) y);
            if (firstPoint.x > secondPoint.x) {
                if (firstPoint.y > secondPoint.y) {
                    rect.set(secondPoint.x, secondPoint.y,
                            firstPoint.x, firstPoint.y);
                } else {
                    rect.set(secondPoint.x, firstPoint.y,
                            firstPoint.x, secondPoint.y);
                }
            } else {
                if (firstPoint.y > secondPoint.y) {
                    rect.set(firstPoint.x, secondPoint.y,
                            secondPoint.x, firstPoint.y);
                } else {
                    rect.set(firstPoint.x, firstPoint.y,
                            secondPoint.x, secondPoint.y);
                }
            }
            mX = (int) x;
            mY = (int) y;
        }
    }
}
