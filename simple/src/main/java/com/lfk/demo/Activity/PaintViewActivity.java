package com.lfk.demo.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.lfk.demo.View.PaintIt.OnPathListener;
import com.lfk.demo.View.PaintIt.PaintView;
import com.lfk.demo.View.PaintIt.PathNode;
import com.lfk.demo.View.PaintIt.UserInfo;
import com.lfk.demo.R;

import java.io.File;

public class PaintViewActivity extends Activity implements View.OnClickListener,View.OnLongClickListener {
    private PaintView paintView;
    private PathNode pathNode;
    private EditText editText;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/DrawAPicture";
    private File dir;
    private AlertDialog alertDialog;
    private AlertDialog alertDialog2;
    private static final int SELECT_PICTURE = 2;
    private static final int SELECT_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_view);
        // Just a line ,you can use a Paintview.
        paintView = (PaintView) findViewById(R.id.paintView);
        // If you want to have more function , you have to do this.
        // 1.new a PathNode (Don't forget add "android:name=".PaintIt.PathNode" in
        // AndroidManifest.xml)
        pathNode = (PathNode) getApplication();

        // 2.allow record

        paintView.setIsRecordPath(true, pathNode);

        // 3.setOnPathClickListener add your every touch message

        paintView.setOnPathListener(new OnPathListener() {
            @Override
            public void addNodeToPath(float x, float y, int event, boolean IsPaint) {
                PathNode.Node tempnode = pathNode.new Node();
                // convert px to dp
                tempnode.x = paintView.px2dip(x);
                tempnode.y = paintView.px2dip(y);
                if (IsPaint) {
                    tempnode.PenColor = UserInfo.PaintColor;
                    tempnode.PenWidth = UserInfo.PaintWidth;
                } else {
                    tempnode.EraserWidth = UserInfo.EraserWidth;
                }
                tempnode.IsPaint = IsPaint;
                Log.e(tempnode.PenColor + ":" + tempnode.PenWidth + ":" + tempnode.EraserWidth, tempnode.IsPaint + "");
                tempnode.TouchEvent = event;
                tempnode.time = System.currentTimeMillis();
                pathNode.addNode(tempnode);
            }
        });

        // 4.set buttons
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.redraw).setOnClickListener(this);
        findViewById(R.id.paint).setOnClickListener(this);
        findViewById(R.id.eraser).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
        findViewById(R.id.file).setOnClickListener(this);
        findViewById(R.id.color).setOnClickListener(this);
        findViewById(R.id.file).setOnLongClickListener(this);
        findViewById(R.id.paint).setOnLongClickListener(this);
        findViewById(R.id.eraser).setOnLongClickListener(this);
        findViewById(R.id.save).setOnLongClickListener(this);


        // 5.new a folder
        save();
    }

    private void save() {
        dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
            Log.e(PATH, dir.mkdirs() + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                save();
                paintView.BitmapToPicture(dir);
                break;
            case R.id.clear:
                // You should clean 3 (Paintview/list to record/list to build Redo/Undo)
                if (!paintView.isShowing()) {
                    paintView.clean();
                    pathNode.clearList();
                    paintView.clearReUnList();
                }
                break;
            case R.id.redraw:
                paintView.preview(pathNode.getPathList());
                break;
            case R.id.eraser:
                // use eraser
                paintView.Eraser();
                break;
            case R.id.paint:
                // use paint
                paintView.Paint();
                break;
            case R.id.file:
                // input a picture
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                break;
            case R.id.color:
                // Now you could use ColorPickerView
                // uri：https://code.google.com/p/color-picker-view/
                // then
                paintView.setColor(0xaefffeff);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE:
                    paintView.setmBitmap(data.getData());
                    break;
                case SELECT_FILE:
                    paintView.JsonToPathNodeToHandle(data.getData());
                    break;
            }

        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.file:
                // input a dir with ".lfk"
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("file/*");
                startActivityForResult(intent, SELECT_FILE);
                break;
            case R.id.save:
                save();
                // save to .lfk  frame-by-frame animation (through json)
                paintView.PathNodeToJson(pathNode, dir);
                break;
            case R.id.paint:
                // set PaintWidth
                editText = new EditText(this);
                alertDialog = new AlertDialog.Builder(this).setTitle("请输入0 - 100之间的数字")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // set in UserInfo
                                UserInfo.PaintWidth = Integer.parseInt(editText.getText().toString());
                                paintView.setPenWidth(Integer.parseInt(editText.getText().toString()));
                                alertDialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.eraser:
                // set EraserWidth
                editText = new EditText(this);
                alertDialog2 = new AlertDialog.Builder(this).setTitle("请输入0 - 100之间的数字")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // set in UserInfo
                                UserInfo.EraserWidth = Integer.parseInt(editText.getText().toString());
                                paintView.setmEraserPaint(Integer.parseInt(editText.getText().toString()));
                                alertDialog2.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog2.dismiss();
                            }
                        }).show();
                break;

        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
                moveTaskToBack(true);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                // redo
                paintView.ReDoORUndo(true);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                // undo
                paintView.ReDoORUndo(false);
                break;
            case KeyEvent.KEYCODE_BACK:
                super.onKeyDown(keyCode, event);
                break;
        }
        return true;
    }


}
