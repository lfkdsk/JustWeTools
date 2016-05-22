package com.lfk.justwetools.View.ClassTable;

import android.text.Html;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 课程信息枚举类
 * 使用枚举类来规范API防止程序员发生SB错误
 *
 * @author liufengkai
 *         Created by liufengkai on 16/5/16.
 */
public class ClassUtils {
    // 日期
    public enum ClassDay {
        Monday(1),
        Tuesday(2),
        Wednesday(3),
        Thursday(4),
        Friday(5),
        Saturday(6),
        Sunday(7);
        public int value;

        ClassDay(int value) {
            this.value = value;
        }
    }

    // 节数 每天只有六节课
    public enum ClassTime {
        N1Class(1),
        N2Class(2),
        N3Class(3),
        N4Class(4),
        N5Class(5),
        N6Class(6),
        N7Class(7),
        N8Class(8),
        N9Class(9),
        N10Class(10),
        N11Class(11),
        N12Class(12);
        public int value;

        ClassTime(int value) {
            this.value = value;
        }
    }

    /**
     * 数字和课程节的转换
     *
     * @param date 数据
     * @return 课程
     */
    public static ClassTime getClassTime(int date) {
        ClassTime time;
        switch (date) {
            case 1:
                time = ClassTime.N1Class;
                break;
            case 2:
                time = ClassTime.N2Class;
                break;
            case 3:
                time = ClassTime.N3Class;
                break;
            case 4:
                time = ClassTime.N4Class;
                break;
            case 5:
                time = ClassTime.N5Class;
                break;
            case 6:
                time = ClassTime.N6Class;
                break;
            case 7:
                time = ClassTime.N7Class;
                break;
            case 8:
                time = ClassTime.N8Class;
                break;
            case 9:
                time = ClassTime.N9Class;
                break;
            case 10:
                time = ClassTime.N10Class;
                break;
            case 11:
                time = ClassTime.N11Class;
                break;
            case 12:
                time = ClassTime.N12Class;
                break;
            default:
                time = ClassTime.N6Class;
                break;
        }
        return time;
    }

    /**
     * 日期和周几的转换
     *
     * @param date 周几
     * @return 周几的枚举类
     */
    public static ClassDay getClassDay(int date) {
        ClassDay classDay;
        switch (date) {
            case 1:
                classDay = ClassDay.Monday;
                break;
            case 2:
                classDay = ClassDay.Tuesday;
                break;
            case 3:
                classDay = ClassDay.Wednesday;
                break;
            case 4:
                classDay = ClassDay.Thursday;
                break;
            case 5:
                classDay = ClassDay.Friday;
                break;
            case 6:
                classDay = ClassDay.Saturday;
                break;
            case 7:
                classDay = ClassDay.Sunday;
                break;
            default:
                classDay = ClassDay.Sunday;
        }
        return classDay;
    }

    public static int getClassDay(ClassDay day) {
        return day.value;
    }

    /**
     * 构建横向周列表时使用
     *
     * @param date     日期
     * @param week     周数
     * @param position 周几
     * @return 返回构造样式
     */
    public static CharSequence getHtmlParseTitle(String date, int week, int position) {
        String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
        String colorWeek = ClassTableDefaultInfo.defaultBackground;
        String colorDay = ClassTableDefaultInfo.defaultDayColor;
        if (position == week) {
            colorWeek = colorDay = ClassTableDefaultInfo.defaultCurBackground;
        }
        return Html.fromHtml("<font color=" + colorWeek + "><b>周"
                + weeks[position - 1] + "</b></font>" + "<br/>"
                + "<small><font color=" + colorDay + ">"
                + getDateStr(date, position - week)
                + "</font></small>");
    }

    /**
     * 绘制左侧时间线
     *
     * @param time     时间
     * @param position 那节课?
     * @return 构建样式
     */
    public static CharSequence getHtmlParseTime(String time, int position) {
        return Html.fromHtml("<small><font color='" + ClassTableDefaultInfo.defaultDayColor + "'>" + time
                + "</font></small>" + "<br/>" + "<b><font color='" + ClassTableDefaultInfo.defaultTimeLineNumColor + "'>"
                + position + "</font></b>");
    }

    public static String getDateStr(String day, int dayAddNum) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date((nowDate != null ? nowDate.getTime() : 0)
                + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(newDate2);
    }

    /**
     * 获取当前周数
     *
     * @return 当前周数
     */
    public static int getCurWeekNum() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int currentDayOfYear = c.get(Calendar.DAY_OF_YEAR) - 5;
        int distanceday;
        if (currentDayOfYear > 56 && currentDayOfYear < 244) {
            distanceday = currentDayOfYear - 56;
        } else if (currentDayOfYear > 244) {
            distanceday = currentDayOfYear - 244;
        } else {
            return 0;
        }
        return distanceday / 7 + 1;
    }

}
