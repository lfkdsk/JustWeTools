package com.lfk.justwetools.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lfk.justwetools.CodeView.CodeView;
import com.lfk.justwetools.R;

import java.io.File;


public class CodeActivity extends ActionBarActivity {
    private CodeView codeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        codeView = (CodeView)findViewById(R.id.mcodeview);

        File dir = null;
        Uri fileUri = getIntent().getData();
        if (fileUri != null) {
            dir = new File(fileUri.getPath());
        }

        if (dir != null) {
            codeView.setDirSource(dir);
            getSupportActionBar().setSubtitle(dir.getName());
        }
        else
            finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_code) {
            if (!codeView.isEditable()) {
                item.setTitle("完成");
                codeView.setContentEditable(true);
            } else {
                item.setTitle("编辑");
                codeView.setContentEditable(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
