package com.lfk.drawapictiure;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.File;

import afzkl.development.colorpickerview.dialog.ColorPickerDialogFragment;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends Activity implements ColorPickerDialogFragment.ColorPickerDialogListener{
    private PaintView paintView;
    private TextView PenWidthView;
    private LayoutInflater inflater;
    private int PenWidth;
    private int EraserWidth;
    private static boolean VISIBLE = true;
    private static boolean OPEN = false;
    private MaterialDialog mMaterialDialog;
    private FloatingActionButton rightLowerButton;
    private FloatingActionMenu rightLowerMenu;
    private SubActionButton.Builder rLSubBuilder;
    private static final int DIALOG_ID = 0;
    private static final int PREFERENCE_DIALOG_ID = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int SELECT_FILE = 3;
    private PathNode pathNode;
    private File dir;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath()+"/DrawAPicture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pathNode = (PathNode)getApplication();
        paintView = new PaintView(this);
        paintView.setIsRecordPath(true,pathNode);
        Intent intent = getIntent();
        if(intent.getData() != null){
            paintView.JsonToPathNodeToHandle(intent.getData());
        }
        paintView.setOnPathListener(new OnPathListener() {
            @Override
            public void AddNodeToPath(float x, float y, int event, boolean IsPaint) {
                PathNode.Node tempnode = pathNode.new Node();
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
                pathNode.AddNode(tempnode);
            }
        });
        setContentView(paintView);
        Init_View();
    }

    @Override
    protected void onPause() {
        super.onPause();
        paintView.save();
    }

    private void Init_View(){
        dir = new File(PATH);
        if(!dir.exists()){
            dir.mkdirs();
            Log.e(PATH,dir.mkdirs()+"");
        }

        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
        rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .build();
        rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);
        ImageView rlIcon5 = new ImageView(this);
        ImageView rlIcon6 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.iconfont_color));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.iconfont_bi));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.iconfont_daoru));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.iconfont_xpc));
        rlIcon5.setImageDrawable(getResources().getDrawable(R.drawable.iconfont_clear));
        rlIcon6.setImageDrawable(getResources().getDrawable(R.drawable.icon_chonghui));

        rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon6).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon5).build())
                .attachTo(rightLowerButton)
                .build();

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                OPEN = true;
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                OPEN = false;
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });
        rlIcon3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("file/*");
                startActivityForResult(intent, SELECT_FILE);
                return true;
            }
        });


        rlIcon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!paintView.isShowing()) {
                    paintView.preview(pathNode.getPathList());
                }
            }
        });

        rlIcon6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                paintView.PathNodeToJson(pathNode,dir);
                return false;
            }
        });
        rlIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!paintView.isShowing()) {
                    paintView.clean();
                    pathNode.ClearList();
                    paintView.clearReUnList();
                }
            }
        });
        rlIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.Eraser();
            }
        });
        rlIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.Paint();
            }
        });
        Init_Eraser_View();
        rlIcon2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Init_First_Dialog();
                mMaterialDialog.show();
                return true;
            }
        });
        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogFragment f = ColorPickerDialogFragment
                        .newInstance(DIALOG_ID, null, null, Color.BLACK, true);
                f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
                f.show(getFragmentManager(), "d");
            }
        });
        rlIcon4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Init_Eraser_View();
                mMaterialDialog.show();
                return true;
            }
        });
        rlIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
            }
        });
        rightLowerButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.e("", Environment.getExternalStorageDirectory().getPath() + "/DrawAPicture");
                paintView.BitmapToPicture(dir);
                return true;
            }
        });
    }


    private void Init_First_Dialog(){
        View view = View.inflate(this,R.layout.activity_paint_width,null);
        PenWidthView = (TextView)view.findViewById(R.id.width_text);
        PenWidthView.setText(UserInfo.PaintWidth + "");
        SeekBar seekBar = (SeekBar)view.findViewById(R.id.width_seek);
        seekBar.setProgress(UserInfo.PaintWidth);
        PenWidth = UserInfo.PaintWidth;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                PenWidth = i;
                PenWidthView.setText("" + i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("设定笔粗")
                .setContentView(view)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paintView.setPenWidth(PenWidth);
                        UserInfo.PaintWidth = PenWidth;
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMaterialDialog.dismiss();
                    }
                });
    }


    private void Init_Eraser_View(){
        View view = View.inflate(this,R.layout.activity_paint_width,null);
        PenWidthView = (TextView)view.findViewById(R.id.width_text);
        PenWidthView.setText(UserInfo.EraserWidth + "");
        SeekBar seekBar = (SeekBar)view.findViewById(R.id.width_seek);
        seekBar.setProgress(UserInfo.EraserWidth);
        EraserWidth = UserInfo.EraserWidth;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                EraserWidth = i;
                PenWidthView.setText("" + i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("设定橡皮粗")
                .setContentView(view)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paintView.setmEraserPaint(EraserWidth);
                        UserInfo.EraserWidth = EraserWidth;
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMaterialDialog.dismiss();
                    }
                });
    }


    @Override
    public void onColorSelected(int dialogId, int color) {
        switch(dialogId) {
            case PREFERENCE_DIALOG_ID:
                ((ColorPickerDialogFragment.ColorPickerDialogListener)this)
                        .onColorSelected(dialogId, color);
                break;
            case DIALOG_ID:
                paintView.setColor(color);
                UserInfo.PaintColor = color;
                break;
        }

    }


    @Override
    public void onDialogDismissed(int dialogId) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_HOME:
                moveTaskToBack(true);
                break;
            case KeyEvent.KEYCODE_MENU:
                if(!OPEN) {
                    if (VISIBLE) {
                        rightLowerButton.setVisibility(View.INVISIBLE);
                    } else {
                        rightLowerButton.setVisibility(View.VISIBLE);
                    }
                    paintView.isFocusable();
                    VISIBLE = !VISIBLE;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                paintView.ReDoORUndo(true);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                paintView.ReDoORUndo(false);
                break;
        }
        return true;
    }

}
