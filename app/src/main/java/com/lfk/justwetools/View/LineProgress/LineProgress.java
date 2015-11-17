package com.lfk.justwetools.View.LineProgress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 条形进度条
 *
 * @author liufengkai
 *         Created by liufengkai on 15/11/6.
 */
public class LineProgress extends View {
    // 绘制初始线条
    private Paint mLinePaint;
    // 绘制进度条
    private Paint mProgressPiant;
    // 绘制进度条文字
    private Paint mTextPaint;
    // 线条粗细
    private int lineWidth = 10;
    // 进度条粗细
    private int progressWidth = lineWidth + 2;
    // 文字尺寸
    private int textSize = 20;
    // 线条颜色
    private int lineColor = Color.GRAY;
    // 进度条颜色
    private int progressColor = Color.BLACK;
    // 文字颜色
    private int textColor = progressColor;
    // 左右边距
    private int padding = 8;
    // 与文字的边距
    private int paddingWithText = 10;
    // 百分比!
    private float progressing = 0;
    // MAX百分比
    private int maxProgressing = 100;
    private Context context;


    public LineProgress(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public LineProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStrokeWidth(lineWidth);

        mProgressPiant = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPiant.setColor(progressColor);
        mProgressPiant.setStyle(Paint.Style.STROKE);
        mProgressPiant.setAntiAlias(true);
        mProgressPiant.setDither(true);
        mProgressPiant.setStrokeWidth(progressWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = getHeight();
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = getWidth();
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        // 转换位置
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        if (progressing >= maxProgressing) {
            progressing = maxProgressing;
        }

        float size = ((progressing * 1.0f / maxProgressing) * (getWidth() - 2 * padding)) + padding + paddingWithText;
        String s = progressing + "%";

        if (size > getWidth()) {
            size = getWidth() - s.length() * textSize / 2 - padding;
        }

        // 绘制灰色底线
        canvas.drawLine(padding, 0, getWidth() - padding, 0, mLinePaint);
        // 绘制进度条
        canvas.drawLine(padding, 0,
                (progressing * 1.0f / maxProgressing) * (getWidth() - 2 * padding) + padding, 0,
                mProgressPiant);
        // 绘制文字
        canvas.drawText(s,
                0,
                s.length(),
                size,
                textSize + progressWidth,
                mTextPaint);
    }

    /**
     * 底线宽度
     *
     * @param lineWidth
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * 进度条粗细
     *
     * @param progressWidth
     */
    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
    }

    /**
     * 字体粗细
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * 进度条颜色
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    /**
     * 底线颜色
     *
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * 文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设定两端的内边距
     *
     * @param padding
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }

    /**
     * 设定百分比
     *
     * @param progressing
     */
    public void setProgressing(float progressing) {
        this.progressing = progressing;
        invalidate();
    }

    /**
     * 获取百分比
     *
     * @return
     */
    public float getProgressing() {
        return progressing;
    }

    /**
     * 设定最大百分比
     *
     * @param maxProgressing
     */
    public void setMaxProgressing(int maxProgressing) {
        this.maxProgressing = maxProgressing;
    }

    /**
     * 设定字与进度条的间距
     *
     * @param paddingWithText
     */
    public void setPaddingWithText(int paddingWithText) {
        this.paddingWithText = paddingWithText;
    }
}
