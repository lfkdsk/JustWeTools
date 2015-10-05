package com.lfk.justwetools.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lfk.justwetools.MarkDown.MarkDownView;
import com.lfk.justwetools.R;

import java.io.File;

public class MarkDownActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_down);
        MarkDownView markDownView = (MarkDownView)findViewById(R.id.markdownview);
        if(getIntent().getStringExtra("str") != null){
            markDownView.setStringSource(getIntent().getStringExtra("str"));
        }
        File dir = null;
        Uri fileUri = getIntent().getData();
        if (fileUri != null) {
            dir = new File(fileUri.getPath());
        }

        if (dir != null) {
            markDownView.setDirSource(dir);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mark_down, menu);
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
}
