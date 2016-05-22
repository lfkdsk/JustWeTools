package com.lfk.justwetools.View.ClassTable;

import android.content.Context;
import android.view.View;

import com.lfk.justwetools.R;

import java.util.Calendar;

/**
 * Created by liufengkai on 16/5/20.
 */
public class ClassTableDefaultInfo {
    /**
     * 屏幕高
     */
    public static int screenHeight;
    /**
     * 屏幕宽
     */
    public static int screenWidth;
    /**
     * 初始化的横向按钮大小 和普通的课表按钮是一样的
     */
    public static int defaultWeekTimeLineWidth;
    /**
     * 被选中的日期的横向按钮大小 和被选中的课表的一样的
     */
    public static int defaultCurWeekTimeLineWidth;

    /**
     * 左侧的时间线 标出的都是dp值记得转换哦!
     * 0.5 是分割线的
     */
    public static float defaultTimeLineWidth = 40f;
    /**
     * 左侧的时间线 的分割线的高度
     */
    public static int defaultTimeLineMarginTopWidth = 1;
    /**
     * 课程表的Item的内边距
     */
    public static int defaultClassTableItemPadding = 3;
    /**
     * 默认的课程高度 dp
     */
    public static int defaultTimeHeight = 55;
    /**
     * 当前课程高度
     */
    public static int courseHeight;
    /**
     * 当前是第几周?
     */
    public static int curWeekNum;
    /**
     * 被选中的颜色
     */
    public static String defaultCurBackground = "#169de5";
    /**
     * 横行时间线的日期颜色 / 也是左侧时间线的时间颜色
     */
    public static String defaultDayColor = "#bababa";
    /**
     * 左侧时间线的日期颜色
     */
    public static String defaultTimeLineNumColor = "#a9a9a9";
    /**
     * 默认的未选中颜色
     */
    public static String defaultBackground = "#646464";
    /**
     * 如果一个信息缺省如何显示?
     */
    public static String defaultNullString = "无";
    /**
     * 未被选中的大小
     */
    public static int courseTextSize = 12;
    /**
     * 被选中的文字大小
     */
    public static int courseCurTextSize = 15;
    /**
     * 当前的月份
     */
    public static int nowMonth;
    /**
     * 当前的日期
     */
    public static int nowDay;
    /**
     * 当前是周几?
     */
    public static int nowWeek;
    /**
     * 左侧的时间线宽度
     */
    public static int timeLineWidth;
    /**
     * 课表宽
     */
    public static int classTableWidth;
    /**
     * 课表高
     */
    public static int classTableHeight;
    /**
     * padding 1dp
     */
    public static int classPadding = 1;
    /**
     * 整体背景颜色
     */
    public static String classTableColor = "#ffffff";
    /**
     * 顶部颜色
     */
    public static String classTableWeekColor = "#ffffff";
    /**
     * 左侧颜色
     */
    public static String classTableTimeLineColor = "#ecf1f0";
    /**
     * 左侧内部颜色
     */
    public static String classTableTimeLineLinearColor = "#e5eae9";

    public static View defaultBashLine;

    public static int classBackgrounPic;

    public static void init(Context context) {
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        defaultWeekTimeLineWidth = (int) (getClassTableWidth(context) / 7.8f);
        defaultCurWeekTimeLineWidth = (int) (defaultWeekTimeLineWidth * 1.824f);

        curWeekNum = ClassUtils.getCurWeekNum();

        Calendar calender = Calendar.getInstance();
        nowMonth = calender.get(Calendar.MONTH) + 1;
        nowDay = calender.get(Calendar.DAY_OF_MONTH);
        nowWeek = calender.get(Calendar.DAY_OF_WEEK) - 1;
        if (nowWeek == 0) {
            nowWeek = 7;
        }

        classTableWidth = (int) (defaultWeekTimeLineWidth * 7.8f);
        classTableHeight = DensityUtil.dip2px(context, ClassTableDefaultInfo.defaultTimeHeight)
                * 12 + (DensityUtil.dip2px(context, classPadding) * 11);
        courseHeight = classTableHeight / 12;

        defaultBashLine = new View(context);
        defaultBashLine.setBackgroundResource(R.drawable.bash_line_shape);
    }

    /**
     * 测量左侧时间线的宽度
     *
     * @param context 上下文
     * @return 宽度
     */
    public static float getClassTableWidth(Context context) {
        int pxTime = timeLineWidth = DensityUtil.dip2px(context, defaultTimeLineWidth);
        int pxScreen = screenWidth;
        return (pxScreen - pxTime) / 5.8f * 7.8f;
    }
}
