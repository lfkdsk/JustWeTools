package com.lfk.justwetools.View.ClassTable;

import android.graphics.Rect;
import android.widget.Button;

/**
 * Created by liufengkai on 16/5/20.
 */
public class BlockInfo {

    private Button blockButton;
    // 当前携带一门课程
    private ClassInfo blockInfo;

    private int marginTop;

    private Rect blockRect;

    public BlockInfo() {

    }

    public BlockInfo(BlockInfo info) {
        this.blockButton = info.getBlockButton();
        this.blockInfo = info.getBlockInfo();
        this.blockRect = info.getBlockRect();
        this.marginTop = info.getMarginTop();
    }

    public static class BlockInfoBuilder {
        private static BlockInfo info;

        public BlockInfoBuilder() {
            info = null;
            info = new BlockInfo();
        }

        public BlockInfoBuilder addClassInfo(ClassInfo classInfo) {
            info.blockInfo = classInfo;
            return this;
        }

        public BlockInfoBuilder addButton(Button button) {
            info.blockButton = button;
            return this;
        }

        public BlockInfoBuilder addMarginTop(int marginTop) {
            info.marginTop = marginTop;
            return this;
        }

        public BlockInfoBuilder addRect(Rect rect) {
            info.setBlockRect(rect);
            return this;
        }

        public BlockInfo creator() {
            BlockInfo tempInfo = new BlockInfo();
            tempInfo.setBlockButton(info.getBlockButton());
            tempInfo.setBlockInfo(info.getBlockInfo());
            tempInfo.setMarginTop(info.getMarginTop());
            tempInfo.setBlockRect(info.getBlockRect());
            return tempInfo;
        }
    }

    public Rect getBlockRect() {
        return blockRect;
    }

    public void setBlockRect(Rect blockRect) {
        this.blockRect = blockRect;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public Button getBlockButton() {
        return blockButton;
    }

    public void setBlockButton(Button blockButton) {
        this.blockButton = blockButton;
    }

    public ClassInfo getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(ClassInfo blockInfo) {
        this.blockInfo = blockInfo;
    }
}
