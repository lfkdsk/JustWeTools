package com.lfk.justwetools.Proportionview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.TreeMap;

public class ProportionView extends View{
    private int ColorSet[] = {0xFFFEDD74,  0xFF82D8EF, 0xFFF76864, 0xFF80BD91, 0xFFFD9FC1, 0xFF};
    private float totalSize = 0;
    private Paint paint = new Paint();
    private Path border = new Path();
    private RectF rect = new RectF();
    private TreeMap<String, Long> map = null;

    public ProportionView(Context context) {
        super(context);
    }

    public ProportionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProportionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取长宽
        int width = this.getWidth();
        int height = this.getHeight();

        //设置画笔消除锯齿
        paint.setAntiAlias(false);

        //剪切底层模板形状
        rect.set(0, 0, width, height);
        border.addRoundRect(rect, 40, 40, Path.Direction.CCW);
        canvas.clipPath(border, Region.Op.REPLACE);

        //背景色
        canvas.drawColor(Color.LTGRAY);

        if(map != null && !map.isEmpty()) {
            //获取总大小
            totalSize = 0;
            for (String key : map.keySet()) {
                totalSize += map.get(key);
            }

            //设置色块
            int offset = 0;
            int colorOffset = 0;
            for (String key : map.keySet()) {
                paint.setColor(ColorSet[colorOffset++ % ColorSet.length]);
                canvas.drawRect(offset, 0, offset += (float) width * (float) map.get(key) / totalSize, height, paint);
            }
        }
    }

    public void setData(TreeMap<String, Long> data) {
        map = data;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}