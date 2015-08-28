package com.lfk.justwetools;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lfk.justwetools.FileExplorer.FileExplorer;
import com.lfk.justwetools.FileExplorer.OnFileChosenListener;
import com.lfk.justwetools.FileExplorer.OnPathChangedListener;
import com.lfk.justwetools.Proportionview.ProportionView;


public class ExplorerActivity extends ActionBarActivity {
    private FileExplorer fileExplorer;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        final ProportionView view = (ProportionView) findViewById(R.id.pv);

        fileExplorer = (FileExplorer)findViewById(R.id.ex);
        //覆盖屏蔽原有长按事件
        fileExplorer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        //新路径下分析文件比例，搞比利，比例比例摔跤的比利
        fileExplorer.setOnPathChangedListener(new OnPathChangedListener() {
            @Override
            public void onPathChanged(String path) {
                try {
                    view.setData(fileExplorer.getPropotionText(path));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "此路径下不可访问或文件夹下无文件", Toast.LENGTH_LONG).show();
                }
            }
        });

        fileExplorer.setOnFileChosenListener(new OnFileChosenListener() {
            @Override
            public void onFileChosen(Uri fileUri) {
//                Intent intent = new Intent(ExplorerActivity.this, CodeActivity.class);
//                intent.setData(fileUri);
//                startActivity(intent);
            }
        });

        fileExplorer.setCurrentDir(Environment.getExternalStorageDirectory().getPath());
        fileExplorer.setRootDir(Environment.getExternalStorageDirectory().getPath());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explorer, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(!fileExplorer.toParentDir()){
                if(System.currentTimeMillis() - exitTime < 1000)
                    finish();
                exitTime = System.currentTimeMillis();
                Toast.makeText(this, "再次点击手脚提前射精", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
