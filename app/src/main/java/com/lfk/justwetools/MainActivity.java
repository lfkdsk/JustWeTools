package com.lfk.justwetools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private  final int BUFFER_SIZE = 400000;
    public static final String FILE_NAME = "codeview.java";
    public static final String READ_FILE_NAME = "exm.txt";
    public static final String PACKAGE_NAME = "com.lfk.justwetools";
    public static final String FILE_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() +"/"
            + PACKAGE_NAME+ "/bin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.paintview_button).setOnClickListener(this);
        findViewById(R.id.explorerview_button).setOnClickListener(this);
        findViewById(R.id.codeview_button).setOnClickListener(this);
        findViewById(R.id.readerview_button).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void copytext(String name,int type) {
        try {
            File myDataPath = new File(FILE_PATH);
            if (!myDataPath.exists()) {
                myDataPath.mkdirs();
            }
            String file = myDataPath+"/"+name;
            if (!(new File(file).exists())) {
                InputStream is;
                if(type == 0) {
                    is = this.getResources().openRawResource(R.raw.codeview);
                }else {
                    is = this.getResources().openRawResource(R.raw.exm);
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0 ;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.paintview_button:
                intent.setClass(MainActivity.this,PaintViewActivity.class);
                break;
            case R.id.explorerview_button:
                intent.setClass(MainActivity.this,ExplorerActivity.class);
                break;
            case R.id.codeview_button:
                copytext(FILE_NAME,0);
                intent.setData(Uri.parse(FILE_PATH + "/" + FILE_NAME));
                intent.setClass(MainActivity.this, CodeActivity.class);
                break;
            case R.id.readerview_button:
                copytext(READ_FILE_NAME,1);
                intent.setData(Uri.parse(FILE_PATH + "/" + READ_FILE_NAME));
                intent.setClass(MainActivity.this, ReadActivity.class);
                break;

        }
        startActivity(intent);
    }
}
