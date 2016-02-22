package com.lfk.demo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lfk.demo.R;
import com.lfk.justwetools.View.CartoonView.CartoonView;

public class CartoonActivity extends AppCompatActivity {
    private CartoonView cartoonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_activiy);

        cartoonView = (CartoonView) findViewById(R.id.cartoon_view);
        cartoonView.start();
    }
}
