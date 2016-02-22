package com.lfk.justwetools.View.CartoonView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;

import com.lfk.justwetools.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author liufengkai
 *         Created by liufengkai on 16/2/22.
 */
public class CartoonView extends ListView {
    private Context mContext;
    private String dirPath;
    private CartoonAdapter adapter;
    private int mScreenHeight;

    public CartoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CartoonView);
        try {
            this.dirPath = Environment.getExternalStorageDirectory() +
                    typedArray.getString(R.styleable.CartoonView_dir_path);
        } finally {
            typedArray.recycle();
        }
        init();
    }

    public CartoonView(Context context, String dirPath) {
        super(context);
        this.mContext = context;
        this.dirPath = dirPath;
        init();
    }

    private void init(){
        WindowManager manager = (android.view.WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mScreenHeight = manager.getDefaultDisplay().getHeight();
    }

    public void start() {
        this.adapter = new CartoonAdapter(mContext, getFileList());
        this.setAdapter(adapter);
    }

    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        File[] list = new File(dirPath).listFiles();

        for (int i = 0; i < list.length; i++) {
            Log.e("lfk", list[i].getName());
            if (list[i].getName().endsWith(".jpg")
                    || list[i].getName().endsWith(".png"))
                fileList.add(list[i].getPath());
        }

        Arrays.sort(list);

        return fileList;
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        int count = getChildCount();
//        // 为ViewGroup设定高度
//        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
//        params.height = mScreenHeight * count;
//
//        setLayoutParams(params);
//
//        for (int i = 0; i < count; i++) {
//            View childView = getChildAt(i);
//            if (childView.getVisibility() != View.GONE) {
//                // 摆放每子View的位置
//                childView.layout(1, mScreenHeight * i,
//                        r, (i + 1) * mScreenHeight);
//            }
//        }
//    }
}
