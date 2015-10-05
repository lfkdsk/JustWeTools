package com.lfk.justwetools.MarkDown;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by liufengkai on 15/10/3.
 */
public class MarkDownView extends WebView {

    private Context context;
    private ProgressDialog progressDialog = null;
    private final static String LOAD_HTML = "file:///android_asset/markdown.html";
    private String str = "";

    public MarkDownView(Context context) {
        super(context);
        init(context);
    }

    public MarkDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MarkDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        progressDialog = ProgressDialog.show(context, "请等待", "正在载入...", true);
        this.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setDisplayZoomControls(false);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setCacheMode(this.getSettings().LOAD_CACHE_ELSE_NETWORK);
        loadUrl(LOAD_HTML);
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100 && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void setDirSource(File dir){
        try {
            FileInputStream in = new FileInputStream(dir);
            ByteArrayOutputStream bufferOut = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            for(int i = in.read(buffer, 0, buffer.length); i > 0 ; i = in.read(buffer, 0, buffer.length)) {
                bufferOut.write(buffer, 0, i);
            }
            str = new String(bufferOut.toByteArray(), Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.loadUrl(LOAD_HTML);
        setUpWebView(str);
    }

    public void setStringSource(String str){
        this.str = str;
        this.loadUrl(LOAD_HTML);
        setUpWebView(str);
    }

    @JavascriptInterface
    private void loadMarkDown(String str) {
        loadUrl("javascript:parseMarkdown(\"" + str + "\")");
    }

    private void setUpWebView(final String mdText) {
        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadMarkDown(mdText.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n"));
            }
        });
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100 && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }


}
