package com.lfk.demo.Activity;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lfk.demo.R;
import com.lfk.demo.View.VerText.VerTextView;

public class VerTextActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_text);
        VerTextView verTextView = (VerTextView)findViewById(R.id.vertextview);
        verTextView.setFontSize(40);
        verTextView.setText(getResources().getString(R.string.poem));

        VerTextView verTextView1 = (VerTextView)findViewById(R.id.vertextview1);
        verTextView1.setText(getResources().getString(R.string.poem));
        verTextView1.setIsOpenUnderLine(true);
        verTextView1.setFontSize(40);
        verTextView1.setUnderLineColor(Color.RED);
        verTextView1.setUnderLineWidth(3);
        verTextView1.setUnderLineSpacing(10);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_text, menu);
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
