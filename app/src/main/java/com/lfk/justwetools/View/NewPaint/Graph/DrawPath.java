package com.lfk.justwetools.View.NewPaint.Graph;

import android.graphics.Canvas;

import com.lfk.justwetools.View.NewPaint.PaintView;


/**
 * Created by liufengkai on 15/10/20.
 */
public class DrawPath extends DrawBase {
    private Canvas mCanvas;
    public DrawPath(Canvas canvas) {
        this.mCanvas = canvas;
    }

    @Override
    public void Touch_Down(float x, float y) {
        super.Touch_Down(x, y);
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
//        if(PaintView.IsRecordPath) {
//            listener.addNodeToPath(x, y, MotionEvent.ACTION_DOWN, IsPaint);
//        }
    }

    @Override
    public void Touch_Up() {
        super.Touch_Up();
        mPath.lineTo(mX, mY);
        if(PaintView.IsPaint) {
            mCanvas.drawPath(mPath, mPaint);
        }else {
            mCanvas.drawPath(mPath, mEraserPaint);
        }
        mPath.reset();
    }

    @Override
    public void Touch_Move(float x, float y) {
        super.Touch_Move(x, y);
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= PaintView.TOUCH_TOLERANCE || dy >= PaintView.TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
//            if(IsRecordPath) {
//                listener.addNodeToPath(x, y, MotionEvent.ACTION_MOVE, IsPaint);
//            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(PaintView.IsPaint)
            canvas.drawPath(mPath, mPaint);
        else
            canvas.drawPath(mPath, mEraserPaint);
    }
}
