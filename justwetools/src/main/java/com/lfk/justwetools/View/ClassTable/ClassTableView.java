package com.lfk.justwetools.View.ClassTable;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lfk.classtabledemo.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 课程表组合控件
 * Created by liufengkai on 16/5/21.
 */
public class ClassTableView extends FrameLayout implements View.OnTouchListener, View.OnClickListener {
    private Context context;
    private RelativeLayout classTableRelative;
    // 整体的横向控制
    private HorizontalScrollView classTableHorScroll;
    // 课表
    private ScrollView classTableScroll;
    // 上方时间线
    private HorizonWeekScrollView classTableWeek;
    // 存放横行按钮的容器
    private LinearLayout classWeekLineLinear;
    private View[] classWeekLineItem = new View[7];
    // 存放纵行时间容器
    private LinearLayout classTimeLineLinear;
    // 纵行时间的数据
    private ArrayList<ClassTimeLine> classTimeLines;
    // 纵行复用Item
    private View[] classTimeLineView = new View[12];
    // 填充
    private RelativeLayout classTable;
    // 切换日期的布局
    private LinearLayout.LayoutParams defaultWeekLine = null;
    private LinearLayout.LayoutParams defaultCurWeekLine = null;
    // 课程信息
    private ArrayList<ClassInfo> classInfoList;
    // 按钮块信息
    private ArrayList<BlockInfo> classButtonList;
    // 判断位置
    private float x_temp1;
    private float x_temp2;
    private float pre_x, pre_y;
    // 当前周数
    private static int selectedWeek;
    // 点击事件接口
    private ClassTableOnClick classTableOnClick = null;
    private ClassTableOnLongClick classTableOnLongClick = null;
    // 判断时间
    private long downTime;
    private long upTime;

    private View bashLineView;

    public ClassTableView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ClassTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.class_table, this);
        initData();
        initView();
    }

    private void initData() {
        pre_x = 0;
        pre_y = 0;
        classInfoList = new ArrayList<>();
        classButtonList = new ArrayList<>();
    }

    private void initView() {
        ClassTableDefaultInfo.init(context);

        classTableRelative = (RelativeLayout) findViewById(R.id.class_table_info);
        classTableRelative.setBackgroundColor(Color.parseColor(ClassTableDefaultInfo.classTableColor));

        classTableHorScroll = (HorizontalScrollView) findViewById(R.id.class_table_horizon_scroller);

        classTableScroll = (ScrollView) findViewById(R.id.class_table_scroller);
        classTableScroll.setBackgroundColor(Color.parseColor(ClassTableDefaultInfo.classTableTimeLineColor));

        classTableWeek = (HorizonWeekScrollView) findViewById(R.id.class_table_week);
        classTableWeek.setBackgroundColor(Color.parseColor(ClassTableDefaultInfo.classTableWeekColor));

        classTimeLineLinear = (LinearLayout) findViewById(R.id.class_timeline_linear);
        classTimeLineLinear.setBackgroundColor(Color.parseColor(ClassTableDefaultInfo.classTableTimeLineLinearColor));

        classWeekLineLinear = (LinearLayout) findViewById(R.id.class_week_linear);
        classTable = (RelativeLayout) findViewById(R.id.class_table_table);

        classTableWeek.setOnTouchListener(this);
        classTableScroll.setOnTouchListener(this);
        classTableHorScroll.setOnTouchListener(this);

        // 初始化课程表的布局
        LinearLayout.LayoutParams params_relative_week_table =
                (LinearLayout.LayoutParams) classTable
                        .getLayoutParams();
        params_relative_week_table.width = ClassTableDefaultInfo.classTableWidth;
        params_relative_week_table.height = ClassTableDefaultInfo.classTableHeight;
        classTable.setLayoutParams(params_relative_week_table);

        initClassLine();
        initWeekLine();
        initBashLine();

        // 加载课表
        addClass("刘丰恺哈哈哈", ClassUtils.ClassDay.Friday, ClassUtils.ClassTime.N2Class);
        addClassOnTable(ClassTableDefaultInfo.curWeekNum);
    }

    /**
     * 初始化横行列表
     */
    private void initWeekLine() {
        int month = ClassTableDefaultInfo.nowMonth;
        int day = ClassTableDefaultInfo.nowDay;
        int week = ClassTableDefaultInfo.nowWeek;
        selectedWeek = week;
        String date = month + "-" + day;
        for (int i = 1; i <= 7; i++) {
            View view = getClassWeekItem(null, date, week, i);
            classWeekLineLinear.addView(view);
            classWeekLineItem[i - 1] = view;
            ClassWeekHolder holder = (ClassWeekHolder) view.getTag();
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            // 添加到View之后设置样式
            if (i == selectedWeek) {
                lp.width = ClassTableDefaultInfo.defaultCurWeekTimeLineWidth;
                holder.wtf.setVisibility(View.VISIBLE);
                holder.wtf.setBackgroundColor(Color.parseColor(ClassTableDefaultInfo.defaultCurBackground));
                if (defaultCurWeekLine == null)
                    defaultCurWeekLine = new LinearLayout.LayoutParams(lp);
            } else {
                lp.width = ClassTableDefaultInfo.defaultWeekTimeLineWidth;
                if (defaultWeekLine == null)
                    defaultWeekLine = new LinearLayout.LayoutParams(lp);
            }
        }
    }

    private void initBashLine() {
        // 11条虚线
        for (int i = 1; i <= 11; i++) {
            View bashLine = new View(context);
            bashLine.setBackgroundResource(R.drawable.bash_line_shape);
            bashLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.width = ClassTableDefaultInfo.classTableWidth;
            params.height = DensityUtil.dip2px(context, 2);
            params.setMargins(0, (DensityUtil.dip2px(context,
                    ClassTableDefaultInfo.defaultTimeHeight
                            + ClassTableDefaultInfo.defaultTimeLineMarginTopWidth) * i)
                    - params.height / 2, 0, 0);
            bashLine.setLayoutParams(params);
            classTable.addView(bashLine);
        }
    }

    /**
     * 初始化纵向列表数据
     * 添加TimeLine对象
     */
    private void initClassLine() {
        classTimeLines = new ArrayList<>();
        ClassTimeLine[] line = {new ClassTimeLine(ClassUtils.getHtmlParseTime("8:00", 1)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("8:50", 2)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("10:05", 3)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("10:55", 4)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("13:30", 5)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("14:20", 6)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("15:35", 7)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("16:25", 8)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("18:00", 9)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("18:55", 10)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("19:50", 11)),
                new ClassTimeLine(ClassUtils.getHtmlParseTime("20:45", 12))};

        classTimeLines.addAll(Arrays.asList(line));
        for (int i = 0; i < 12; i++) {
            View view = getClassTimeLineItem(null, i);
            classTimeLineLinear.addView(view);
            classTimeLineView[i] = view;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            lp.topMargin = DensityUtil.dip2px(context, ClassTableDefaultInfo.defaultTimeLineMarginTopWidth);
        }
    }

    /**
     * 使用ViewHolder创建横向周列表
     *
     * @param v        复用组件
     * @param date     日期
     * @param week     周
     * @param position 周几?
     * @return WeekLine
     */
    private View getClassWeekItem(View v, String date, int week, int position) {
        ClassWeekHolder holder;
        holder = new ClassWeekHolder();
        v = LayoutInflater.from(context).inflate(R.layout.class_week_item, null);
        holder.classWeek = (TextView) v.findViewById(R.id.class_week_text);
        holder.wtf = v.findViewById(R.id.class_week_line);
        holder.classWeek.setText(ClassUtils.getHtmlParseTitle(date, week, position));
        holder.classWeek.setTag(ClassUtils.getClassDay(position));
        v.setOnClickListener(this);
        holder.wtf.setVisibility(View.INVISIBLE);
        v.setTag(holder);
        return v;
    }


    /**
     * 使用ViewHolder制作的TimeLine
     *
     * @param v        复用TimeLine
     * @param position 位置
     * @return 当前位置的控件
     */
    private View getClassTimeLineItem(View v, int position) {
        ClassTimeLineHolder holder;
        holder = new ClassTimeLineHolder();
        v = LayoutInflater.from(context).inflate(R.layout.class_timeline_item, null);
        holder.classTime = (TextView) v.findViewById(R.id.class_timeline_item);
        v.setTag(holder);
        ClassTimeLine line = classTimeLines.get(position);
        holder.classTime.setText(line.timeline);
        return v;
    }

    /**
     * 添加View
     *
     * @param recentClass 当前课程
     */
    private void addViewForTable(ClassInfo recentClass, final int id) {
        // 当前是周几 / 不要误会了
        int curWeek = ClassTableDefaultInfo.nowWeek;
        // 多少节
        int counts = recentClass.getClassEndTime() - recentClass.getClassStartTime();
        // 计算高度
        int height = ClassTableDefaultInfo.courseHeight * counts;
        // 这课周几上啊?
        int classWeek = recentClass.getClassWeek();
        // 控件宽度
        int width = classWeek == curWeek
                ? ClassTableDefaultInfo.defaultCurWeekTimeLineWidth
                : ClassTableDefaultInfo.defaultWeekTimeLineWidth;
        int marginLeft = classWeek > curWeek
                ? ClassTableDefaultInfo.defaultWeekTimeLineWidth * (classWeek - 2) + ClassTableDefaultInfo.defaultCurWeekTimeLineWidth
                : ClassTableDefaultInfo.defaultWeekTimeLineWidth * (classWeek - 1);
        int marginTop = ClassTableDefaultInfo.courseHeight * (recentClass.getClassStartTime() - 1);
        // 创建view
        int padding = DensityUtil.dip2px(context, ClassTableDefaultInfo.defaultClassTableItemPadding);
        int textSize = classWeek == curWeek
                ? ClassTableDefaultInfo.courseCurTextSize
                : ClassTableDefaultInfo.courseTextSize;

        Button btForCourse = new Button(context);
        btForCourse.setTag(classWeek);
        btForCourse.setId(id);
        btForCourse.setMaxLines(6);
        btForCourse.setGravity(Gravity.LEFT);
        // 设定文字
        String content = recentClass.getClassName() + "\n@" + recentClass.getClassLocation();

        btForCourse.setText(content);
        btForCourse.setTextColor(Color.WHITE);
        if (recentClass.getBackgroundColor() != 0)
            btForCourse.setBackgroundColor(recentClass.getBackgroundColor());

        btForCourse.setPadding(padding, padding, padding, padding);
        btForCourse.setTextSize(textSize);
        // 设置参数
        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        btParams.width = width;
        btParams.height = height;
        btParams.setMargins(marginLeft, marginTop, 0, 0);
        // 将view放到课表中
        classTable.addView(btForCourse, btParams);

        BlockInfo.BlockInfoBuilder builder = new BlockInfo.BlockInfoBuilder();
        builder.addButton(btForCourse)
                .addMarginTop(marginTop)
                .addClassInfo(recentClass);

        // 添加块
        classButtonList.add(builder.creator());
    }


    /**
     * 添加课程
     *
     * @param weekNum 当前的周目
     */
    public void addClassOnTable(int weekNum) {
        for (int i = 0; i < classInfoList.size(); i++) {
            // 拿到对应课程信息
            if (classInfoList.get(i).getStartWeek() <= weekNum
                    && classInfoList.get(i).getEndWeek() >= weekNum) {
                addViewForTable(classInfoList.get(i), i);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                x_temp1 = x;
                break;
            case MotionEvent.ACTION_UP:
                pre_x = classTableHorScroll.getScrollX();
                pre_y = classTableScroll.getScrollY();

                upTime = System.currentTimeMillis();
                // 条件成立，单击事件发生
                if (upTime - downTime < 100) {
                    // 发声音
                    v.playSoundEffect(SoundEffectConstants.CLICK);
                    setCourseOnClick(x + pre_x, y + pre_y, TouchMode.Click);
                    // 长按
                } else if (upTime - downTime > 400) {
                    setCourseOnClick(x + pre_x, y + pre_y, TouchMode.LongClick);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x_temp2 = x;
                //当出现移动事件的时候只处理课程表的
                if (v.getId() == R.id.class_table_scroller) {
                    classTableHorScroll.smoothScrollTo((int) (pre_x - (x_temp2 - x_temp1)), 0);
                    classTableWeek.smoothScrollTo((int) (pre_x - (x_temp2 - x_temp1)), 0);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                pre_x = classTableHorScroll.getScrollX();
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 进行课程表Item的点击事件
     *
     * @param x    x
     * @param y    y
     * @param mode 模式{Click,LongClick}
     */
    private void setCourseOnClick(float x, float y, TouchMode mode) {
        for (int i = 0; i < classButtonList.size(); i++) {
            BlockInfo info = classButtonList.get(i);
            if (contains(info.getBlockButton(), (int) x,
                    (int) y)) {
                if (mode == TouchMode.Click && classTableOnClick != null)
                    classTableOnClick.onClick(info.getBlockButton(), info);
                else if (mode == TouchMode.LongClick && classTableOnLongClick != null) {
                    classTableOnLongClick.onLongClick(info.getBlockButton(), info);
                }
            }
        }
    }

    /**
     * 设定当前位置 横向滚动条
     * 变宽啊,变色啊什么的
     *
     * @param position 周几?
     */
    private void setCurWeekPosition(int position) {
        for (int i = 1; i <= classWeekLineItem.length; i++) {
            View view = classWeekLineItem[i - 1];
            ClassWeekHolder holder = (ClassWeekHolder) view.getTag();
            if (i == position) {
                view.setLayoutParams(defaultCurWeekLine);
                holder.wtf.setVisibility(View.VISIBLE);
            } else {
                view.setLayoutParams(defaultWeekLine);
                holder.wtf.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 判断点击事件
     *
     * @param button 拿到按钮
     * @param x      点击的X
     * @param y      点击的Y
     * @return 是否成功点击
     */
    private boolean contains(Button button, int x, int y) {
        int offset = ClassTableDefaultInfo.timeLineWidth;
        int left = button.getLeft() + offset;
        int right = left + button.getWidth();
        int top = button.getTop();
        int bottom = top + button.getHeight();
        return left < right && top < bottom  // check for empty first
                && x >= left && x < right && y >= top && y < bottom;
    }

    /**
     * 刷新课标
     */
    public void refreshClassTable() {
        classButtonList.clear();
        addClassOnTable(ClassTableDefaultInfo.curWeekNum);
    }

    /**
     * 刷新课程表
     *
     * @param weekNum 第几周
     */
    public void refreshClassTableWithWeek(int weekNum) {
        classButtonList.clear();
        addClassOnTable(weekNum);
    }

    /**
     * 动态刷新课表的样式
     * 刷新当前课表
     *
     * @param position 拿到的是点击的当前列
     */
    private void setCurTablePosition(int position) {
        if (classButtonList.size() > 0) {
            for (int i = 0; i < classButtonList.size(); i++) {
                BlockInfo info = classButtonList.get(i);

                RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                btParams.height = info.getBlockButton().getHeight();

                int marginLeft = info.getBlockInfo().getClassWeek() > position
                        ? ClassTableDefaultInfo.defaultWeekTimeLineWidth
                        * (info.getBlockInfo().getClassWeek() - 2) + ClassTableDefaultInfo.defaultCurWeekTimeLineWidth
                        : ClassTableDefaultInfo.defaultWeekTimeLineWidth * (info.getBlockInfo().getClassWeek() - 1);

                btParams.setMargins(marginLeft, classButtonList.get(i).getMarginTop(), 0, 0);

                if (info.getBlockInfo().getClassWeek() == position) {
                    info.getBlockButton().setWidth(ClassTableDefaultInfo.defaultCurWeekTimeLineWidth);
                    info.getBlockButton().setTextSize(ClassTableDefaultInfo.courseCurTextSize);
                    btParams.width = ClassTableDefaultInfo.defaultCurWeekTimeLineWidth;
                } else {
                    info.getBlockButton().setWidth(ClassTableDefaultInfo.defaultWeekTimeLineWidth);
                    info.getBlockButton().setTextSize(ClassTableDefaultInfo.courseTextSize);
                    btParams.width = ClassTableDefaultInfo.defaultWeekTimeLineWidth;
                }
                info.getBlockButton().setLayoutParams(btParams);
            }
        }
    }

    /**
     * 便携添加函数
     *
     * @param className 课程名
     * @param day       哪天?
     * @param time      课程时间 默认单节
     */
    public void addClass(String className,
                         ClassUtils.ClassDay day,
                         ClassUtils.ClassTime time) {
        addClass(className, ClassTableDefaultInfo.curWeekNum,
                ClassTableDefaultInfo.curWeekNum,
                day, time, ClassUtils.getClassTime(time.value + 1));
    }

    /**
     * 便携添加函数
     *
     * @param className 课程名
     * @param weekFrom  从第几周开始
     * @param weekTo    到第几周
     * @param day       哪天
     * @param from      从第几节
     * @param to        到第几节
     */
    public void addClass(String className,
                         int weekFrom, int weekTo,
                         ClassUtils.ClassDay day,
                         ClassUtils.ClassTime from,
                         ClassUtils.ClassTime to) {
        addClass(className, weekFrom, weekTo, day, from, to, "", "");
    }

    /**
     * 通过课程信息进行添加课程
     *
     * @param info 课程信息 通过Builder进行初始化
     */
    public void addClass(ClassInfo info) {
        classInfoList.add(info);
    }

    /**
     * 添加一门课可能是最长的构造函数
     *
     * @param className 课程名
     * @param weekFrom  从第几周开始
     * @param weekTo    到第几周
     * @param day       那天上课?
     * @param from      第几节?
     * @param to        到第几节?
     * @param content   课的内容
     * @param color     颜色?
     */
    public void addClass(String className,
                         int weekFrom,
                         int weekTo,
                         ClassUtils.ClassDay day,
                         ClassUtils.ClassTime from,
                         ClassUtils.ClassTime to,
                         String content,
                         String color) {
        ClassInfo.ClassBuilder builder = new ClassInfo.ClassBuilder();
        if (color.equals("")) color = ClassTableDefaultInfo.defaultCurBackground;
        builder.addBackgroundColor(Color.parseColor(color))
                .addClassEndTime(to.value)
                .addClassStartTime(from.value)
                .addClassName(className)
                .addStartWeek(weekFrom)
                .addEndWeek(weekTo)
                .addClassWeek(day.value)
                .addContent(content);
        addClass(builder.creator());
    }

    /**
     * 设置课程表Item的点击事件
     *
     * @param classTableOnClick 点击事件的接口
     */
    public void setClassTableOnClick(ClassTableOnClick classTableOnClick) {
        this.classTableOnClick = classTableOnClick;
    }

    /**
     * 设置课程表的Item的长按事件
     *
     * @param classTableOnLongClick 长按时间的接口
     */
    public void setClassTableOnLongClick(ClassTableOnLongClick classTableOnLongClick) {
        this.classTableOnLongClick = classTableOnLongClick;
    }

    /**
     * 点击事件 是课程表选择周几的点击事件
     *
     * @param v WeekView
     */
    @Override
    public void onClick(View v) {
        int value = ((ClassUtils.ClassDay)
                (((ClassWeekHolder) v.getTag())
                        .classWeek.getTag())).value;
        setCurWeekPosition(value);
        setCurTablePosition(value);
        selectedWeek = value;
    }
}
