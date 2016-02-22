package com.lfk.justwetools.View.Clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义绘制时钟
 * <p/>
 * Created by liufengkai on 15/11/8.
 */
public class Clock extends View {
    private Context context;
    // 内边距
    private int padding = 10;
    // 宽度
    private int mWidth = 500;
    // 高度
    private int mHeight = 500;
    // 秒\分\小时 宽度
    private int secondWidth = 3;
    private int minWidth = 5;
    private int hourWidth = 7;
    // 外圆环宽度
    private int circleWidth = 5;
    // 整点和非整点的线宽度
    private int thehourLineWidth = 5;
    private int unthehourLineWidth = 3;
    // 整点和非整点的字体大小
    private int thehourLineSize = 30;
    private int unthehourLineSize = 15;
    // 秒\分\小时 长度
    private int secondSize = 100;
    private int minSize = 70;
    private int hourSize = 50;

    private int needleColor = Color.BLACK;
    private int circleColor = Color.BLACK;
    private int textColor = Color.BLACK;
    private int thehourLineColor = Color.BLACK;
    private int unthehourLineColor = Color.BLACK;
    Paint paintDegree;
    Paint paintCircle;
    Paint paintArrow;

    public Clock(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        paintCircle = new Paint();
        // init circle
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(circleColor);
        paintCircle.setStrokeWidth(circleWidth);

        paintDegree = new Paint();

        paintArrow = new Paint();
        paintArrow.setColor(needleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - padding, paintCircle);

        Time time = new Time();
        time.setToNow();
        int second = time.second;
        int min = time.minute;
        int hour = time.hour;
        // draw second
        paintArrow.setStrokeWidth(secondWidth);
        canvas.rotate(6 * second, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mWidth / 2 - secondSize,
                mWidth / 2,
                mHeight / 2,
                paintArrow);
        canvas.rotate(360 - 6 * second, mWidth / 2, mHeight / 2);
        // draw min
        paintArrow.setStrokeWidth(minWidth);
        canvas.rotate(6 * min, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mWidth / 2 - minSize,
                mWidth / 2,
                mHeight / 2,
                paintArrow);
        canvas.rotate(360 - 6 * min, mWidth / 2, mHeight / 2);
        // draw hour
        paintArrow.setStrokeWidth(hourWidth);
        canvas.rotate(15 * hour + (min * 1.0f / 60) * 15, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mWidth / 2 - hourSize,
                mWidth / 2,
                mHeight / 2,
                paintArrow);
        canvas.rotate(360 - 15 * hour - (min * 1.0f / 60) * 15, mWidth / 2, mHeight / 2);
        for (int i = 0; i <= 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                paintDegree.setColor(thehourLineColor);
                paintDegree.setStrokeWidth(thehourLineWidth);
                paintDegree.setTextSize(thehourLineSize);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2 + padding, mWidth / 2
                        , mHeight / 2 - mWidth / 2 + 60, paintDegree);
                String degree = String.valueOf(i);
                paintDegree.setColor(textColor);
                canvas.drawText(degree,
                        mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 90,
                        paintDegree);
            } else {
                paintDegree.setColor(unthehourLineColor);
                paintDegree.setStrokeWidth(unthehourLineWidth);
                paintDegree.setTextSize(unthehourLineSize);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2 + padding, mWidth / 2
                        , mHeight / 2 - mWidth / 2 + 30, paintDegree);
                String degree = String.valueOf(i);
                canvas.drawText(degree,
                        mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 60,
                        paintDegree);
            }
            canvas.rotate(15, mWidth / 2, mHeight / 2);
        }
        postInvalidateDelayed(500);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = mHeight;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = mWidth;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setSecondWidth(int secondWidth) {
        this.secondWidth = secondWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setHourWidth(int hourWidth) {
        this.hourWidth = hourWidth;
    }

    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
    }

    public void setThehourLineWidth(int thehourLineWidth) {
        this.thehourLineWidth = thehourLineWidth;
    }

    public void setThehourLineSize(int thehourLineSize) {
        this.thehourLineSize = thehourLineSize;
    }

    public void setUnthehourLineWidth(int unthehourLineWidth) {
        this.unthehourLineWidth = unthehourLineWidth;
    }

    public void setUnthehourLineSize(int unthehourLineSize) {
        this.unthehourLineSize = unthehourLineSize;
    }

    public void setSecondSize(int secondSize) {
        this.secondSize = secondSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setHourSize(int hourSize) {
        this.hourSize = hourSize;
    }

    public void setThehourLineColor(int thehourLineColor) {
        this.thehourLineColor = thehourLineColor;
    }

    public void setUnthehourLineColor(int unthehourLineColor) {
        this.unthehourLineColor = unthehourLineColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setNeedleColor(int needleColor) {
        this.needleColor = needleColor;
    }

    public void setColor(int color) {
        this.circleColor = color;
        this.needleColor = color;
        this.textColor = color;
        this.thehourLineColor = color;
        this.unthehourLineColor = color;
        paintArrow.setColor(needleColor);
        paintCircle.setColor(circleColor);
    }
}
