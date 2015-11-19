package com.lfk.justwetools.View.CircleGraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.lfk.justwetools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 环形统计图
 *
 * @author liufengkai
 *         Created by liufengkai on 15/11/6.
 */
public class CircleGraph extends View {
    // 原点
    private int mCircleXY;
    // r
    private float mRadius;
    // 弧线外围的矩形
    private RectF mArcRectF;
    // 三个画笔
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private int mSweepValue = 100;
    private int mStartValue = 0;
    private int mCircleColor;
    private int mArcColor;
    private int ColorSet[] = {0xFFFEDD74, 0xFF82D8EF, 0xFFF76864, 0xFF80BD91, 0xFFFD9FC1, 0xFF};
    private int mArcWidth = 40;
    // 文字
    private String text = null;
    // 文字尺寸
    private float textSize = 40;
    private Context context;
    private boolean jsonFlag = false;
    private JSONArray Json = null;

    public CircleGraph(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    public CircleGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int length = manager.getDefaultDisplay().getWidth();
        mCircleXY = length / 2;
        mRadius = (float) (length * 0.5) / 2;
        mArcRectF = new RectF(
                (float) (length * 0.1),
                (float) (length * 0.1),
                (float) (length * 0.9),
                (float) (length * 0.9));
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        mArcPaint.setStrokeWidth(mArcWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 圆形
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        // 弧线

        // 弧线的外围矩形/ 起始角度 / 角度 / 是否封闭 / 画笔
        if (jsonFlag && Json != null) {
            int start = mStartValue;
            for (int i = 0; i < Json.length(); i++) {
                try {
                    JSONObject object = Json.getJSONObject(i);
                    if (object.has("color"))
                        mArcPaint.setColor(object.getInt("color"));
                    canvas.drawArc(mArcRectF, start, object.getInt("sweep"), false, mArcPaint);
                    start += object.getInt("sweep");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            canvas.drawArc(mArcRectF, mStartValue, mSweepValue, false, mArcPaint);
        }
        // 文字
        if (text != null) {
            canvas.drawText(text, 0, text.length(),
                    mCircleXY - (text.length() * textSize / 2),
                    mCircleXY + (text.length() * textSize / 2),
                    mTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 800;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 800;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    /**
     * 设定起始角度
     *
     * @param mStartValue
     */
    public void setmStartValue(int mStartValue) {
        this.mStartValue = mStartValue;
        invalidate();
    }

    /**
     * 设定百分比
     *
     * @param mSweepValue
     */
    public void setmSweepValue(int mSweepValue) {
        this.mSweepValue = mSweepValue;
        invalidate();
    }

    /**
     * 弧线颜色
     *
     * @param mArcColor
     */
    public void setmArcColor(int mArcColor) {
        this.mArcColor = mArcColor;
        mArcPaint.setColor(mArcColor);
        invalidate();
    }

    /**
     * 圆形颜色
     *
     * @param mCircleColor
     */
    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
        mCirclePaint.setColor(mCircleColor);
        invalidate();
    }

    /**
     * 设定文本
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 设定圆弧宽度
     *
     * @param mArcWidth
     */
    public void setmArcWidth(int mArcWidth) {
        this.mArcWidth = mArcWidth;
    }

    /**
     * 设定文字尺寸
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setJson(String json) {
        try {
            Json = new JSONArray(json);
            jsonFlag = true;
        } catch (JSONException e) {
            e.printStackTrace();
            jsonFlag = false;
        }
    }
}
