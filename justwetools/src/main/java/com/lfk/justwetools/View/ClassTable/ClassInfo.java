package com.lfk.justwetools.View.ClassTable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 单个课程的详情信息
 * Created by liufengkai on 16/5/16.
 */
public class ClassInfo implements Parcelable {
    /**
     * 课程名称
     */
    private String className;
    /**
     * 课程的计算位置
     */
    private int classPosition;
    /**
     * 课程的唯一标示
     */
    private String classId;
    /**
     * 教师
     */
    private String classTeacher;
    /**
     * 上课位置
     */
    private String classLocation;
    /**
     * 上课类型
     */
    private String classType;
    /**
     * 周几上?
     */
    private int classWeek;
    /**
     * 开始时间
     */
    private int classStartTime;
    /**
     * 结束时间
     */
    private int classEndTime;
    /**
     * 背景颜色
     */
    private int backgroundColor = 0;
    // =-=
//    private int background = 0;
    /**
     * 开始周目
     */
    private int startWeek;
    /**
     * 结束周目
     */
    private int endWeek;
    private String content;

    // 空构造
    public ClassInfo() {

    }

    public ClassInfo(ClassInfo info) {
        className = info.getClassName();
        classPosition = info.getClassPosition();
        classTeacher = info.getClassTeacher();
        classLocation = info.getClassLocation();
        classType = info.getClassType();
        classWeek = info.getClassWeek();
        classStartTime = info.getClassStartTime();
        classEndTime = info.getClassEndTime();
        backgroundColor = info.getBackgroundColor();
        startWeek = info.getStartWeek();
        endWeek = info.getEndWeek();
        content = info.getContent();
    }

    protected ClassInfo(Parcel in) {
        className = in.readString();
        classPosition = in.readInt();
        classId = in.readString();
        classTeacher = in.readString();
        classLocation = in.readString();
        classType = in.readString();
        classWeek = in.readInt();
        classStartTime = in.readInt();
        classEndTime = in.readInt();
        backgroundColor = in.readInt();
        startWeek = in.readInt();
        endWeek = in.readInt();
        content = in.readString();
    }

    public static final Creator<ClassInfo> CREATOR = new Creator<ClassInfo>() {
        @Override
        public ClassInfo createFromParcel(Parcel in) {
            return new ClassInfo(in);
        }

        @Override
        public ClassInfo[] newArray(int size) {
            return new ClassInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(className);
        dest.writeInt(classPosition);
        dest.writeString(classId);
        dest.writeString(classTeacher);
        dest.writeString(classLocation);
        dest.writeString(classType);
        dest.writeInt(classWeek);
        dest.writeInt(classStartTime);
        dest.writeInt(classEndTime);
        dest.writeInt(backgroundColor);
        dest.writeInt(startWeek);
        dest.writeInt(endWeek);
        dest.writeString(content);
    }

    public static class ClassBuilder {

        private static ClassInfo info;

        public ClassBuilder() {
            info = new ClassInfo();
        }

        public ClassBuilder addContent(String content) {
            info.setContent(content);
            return this;
        }

        public ClassBuilder addClassName(String className) {
            info.setClassName(className);
            return this;
        }

        public ClassBuilder addClassPosition(int classPosition) {
            info.setClassPosition(classPosition);
            return this;
        }

        public ClassBuilder addClassTeacher(String classTeacher) {
            info.setClassTeacher(classTeacher);
            return this;
        }

        public ClassBuilder addClassId(String classId) {
            info.setClassId(classId);
            return this;
        }

        public ClassBuilder addClassLocation(String classLocation) {
            info.setClassLocation(classLocation);
            return this;
        }

        public ClassBuilder addClassType(String classType) {
            info.setClassType(classType);
            return this;
        }

        public ClassBuilder addClassWeek(int classWeek) {
            info.setClassWeek(classWeek);
            return this;
        }

        public ClassBuilder addClassStartTime(int classStartTime) {
            info.setClassStartTime(classStartTime);
            return this;
        }

        public ClassBuilder addClassEndTime(int classEndTime) {
            info.setClassEndTime(classEndTime);
            return this;
        }

        public ClassBuilder addBackgroundColor(int classBackground) {
            info.setBackgroundColor(classBackground);
            return this;
        }

        public ClassBuilder addStartWeek(int startWeek) {
            info.setStartWeek(startWeek);
            return this;
        }

        public ClassBuilder addEndWeek(int endWeek) {
            info.setEndWeek(endWeek);
            return this;
        }

        public ClassInfo creator() {
            return new ClassInfo(info);
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassPosition() {
        return classPosition;
    }

    public void setClassPosition(int classPosition) {
        this.classPosition = classPosition;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        if (classTeacher.length() <= 0) {
            this.classTeacher = ClassTableDefaultInfo.defaultNullString;
        } else {
            this.classTeacher = classTeacher;
        }
    }

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        if (classLocation.length() <= 0) {
            this.classLocation = ClassTableDefaultInfo.defaultNullString;
        } else {
            this.classLocation = classLocation;
        }
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getClassWeek() {
        return classWeek;
    }

    public void setClassWeek(int classWeek) {
        this.classWeek = classWeek;
    }

    public int getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(int classStartTime) {
        this.classStartTime = classStartTime;
    }

    public int getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(int classEndTime) {
        this.classEndTime = classEndTime;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
