package com.lfk.justwetools.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.lfk.justwetools.ReadView.ReadView;

import java.io.File;


public class ReadActivity extends Activity {
    private ReadView readView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File dir = null;
        Uri fileUri = getIntent().getData();
        if (fileUri != null) {
            dir = new File(fileUri.getPath());
        }
        readView = null;
        if (dir != null) {
            readView = new ReadView(this,dir.getPath());
        }
        else
            finish();
        setContentView(readView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        readView.setOnPause();
    }
}
