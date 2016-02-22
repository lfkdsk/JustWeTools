package com.lfk.justwetools.View.NewPaint.Graph;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.lfk.justwetools.View.NewPaint.UserInfo;


/**
 * Created by liufengkai on 15/10/20.
 */
public class DrawBase {
    protected Path mPath;
    protected Paint mPaint;
    protected Paint mEraserPaint;
    protected float mX, mY;

//    protected OnPathListener listener;

    public DrawBase() {
        mPath = new Path();
        mPaint = new Paint();
        mEraserPaint = new Paint();
        Init_Paint(UserInfo.PaintColor,UserInfo.PaintWidth);
        Init_Eraser(UserInfo.EraserWidth);
    }

    // init paint
    private void Init_Paint(int color ,int width){
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(width);
    }


    // init eraser
    private void Init_Eraser(int width){
        mEraserPaint.setAntiAlias(true);
        mEraserPaint.setDither(true);
        mEraserPaint.setColor(0xFF000000);
        mEraserPaint.setStrokeWidth(width);
        mEraserPaint.setStyle(Paint.Style.STROKE);
        mEraserPaint.setStrokeJoin(Paint.Join.ROUND);
        mEraserPaint.setStrokeCap(Paint.Cap.SQUARE);
        // The most important
        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void setmPaintWidth(float width){
        mPaint.setStrokeWidth(width);
    }

    public void Touch_Down(float x, float y){

    }

    public void Touch_Up(){

    }

    public void Touch_Move(float x, float y){

    }

    public void onDraw(Canvas canvas){

    }


}
