package com.lfk.demo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.lfk.demo.R;
import com.lfk.justwetools.View.NewPaint.PaintView;

public class NewPaintActivity extends ActionBarActivity {
    private PaintView paintView;
    private PaintView.mode checkit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_paint);
        paintView = (PaintView) findViewById(R.id.paint);
        Button button = (Button) findViewById(R.id.clear_it);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clearReUnList();
                paintView.clean();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_paint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LayoutInflater i = LayoutInflater.from(NewPaintActivity.this);
            View view = i.inflate(R.layout.choose_type, null);
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.draw_group);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.draw_circle:
                            checkit = PaintView.mode.CIRCLE;
                            break;
                        case R.id.draw_line:
                            checkit = PaintView.mode.LINE;
                            break;
                        case R.id.draw_path:
                            checkit = PaintView.mode.PATH;
                            break;
                        case R.id.draw_rect:
                            checkit = PaintView.mode.RECT;
                            break;
                    }
                }
            });
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setTitle("选择模式")
                    .setView(view)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            paintView.setDrawBase(checkit);
                            Log.e("check", "" + checkit);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
