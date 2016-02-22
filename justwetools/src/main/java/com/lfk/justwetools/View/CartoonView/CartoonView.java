package com.lfk.justwetools.View.CartoonView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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

    public CartoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CartoonView);
        try {
            this.dirPath = typedArray.getString(R.styleable.CartoonView_dir_path);
        } finally {
            typedArray.recycle();
        }
    }

    public CartoonView(Context context, String dirPath) {
        super(context);
        this.mContext = context;
        this.dirPath = dirPath;
    }

    public void start() {
        this.adapter = new CartoonAdapter(mContext, getFileList());
        this.setAdapter(adapter);
    }

    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        File[] list = new File(dirPath).listFiles();

        Arrays.sort(list);

        for (int i = 0; i < list.length; i++) {
            fileList.add(list[i].getPath());
        }

        return fileList;
    }


}
