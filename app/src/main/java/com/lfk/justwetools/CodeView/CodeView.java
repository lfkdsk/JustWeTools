package com.lfk.justwetools.CodeView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class CodeView extends WebView{

    //常量表
    private final String STYLE_DIR = "file:///android_asset/";      //样式保存地址
    private final String JS_COMMAND_READ = "javascript:window.reader.read(document.body.innerText);";
    private final String JS_COMMAND_WRITE = "javascript:window.writer.write(document.body.innerText);";
    private final String JS_COMMAND_EDITABLE = "javascript:document.body.contentEditable = true;";
    private final String JS_COMMAND_UNEDITABLE = "javascript:document.body.contentEditable = false;";
    private final String JS_VAR_READ = "reader";
    private final String JS_VAR_WRITE = "writer";

    private boolean editEnable = false;

    private ProgressDialog progressDialog = null;
    private final JSContentReader reader = new JSContentReader();
    private final JSContentWriter writer = new JSContentWriter();
    private OnCodeChangedListener listener = null;
    private File codeFile = null;
    private Context context;

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////      CONSTRUCTOR     ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    public CodeView(Context context) {
        super(context);
        init(context);
    }

    public CodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CodeView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
        init(context);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////      INITIALIZE      ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     *  初始化，添加回调，以后只要运行reload就会自动执行回调
     */
    private void init(final Context context) {
        this.context = context;

        //初始化等待Dialog
        progressDialog = ProgressDialog.show(context, "请等待", "正在载入...", true);

        //初始化CodeView的样式
        this.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setDisplayZoomControls(false);
        this.getSettings().setJavaScriptEnabled(true);

        //给网页添加一个JS回调变量，名字叫"reader"，通过loadURL载入JS命令执行回调
        this.addJavascriptInterface(reader, JS_VAR_READ);
        this.addJavascriptInterface(writer, JS_VAR_WRITE);
        this.setWebViewClient(new WebViewClient());
        this.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress >= 100 && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                }
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        READER        ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     *  JS网页信息回调监听器。注意，4.2以后必须加 @JavascriptInterface 否则无法获取信息
     *  更新信息后写出到文件
     *  获取到信息后发送信息到Listener（如果存在）
     */
    class JSContentReader{
        @JavascriptInterface
        public void read(final String data){
            if(listener != null) {
                listener.onCodeChanged(data);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        WRITER        ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    class JSContentWriter {
        @JavascriptInterface
        public void write(final String data) {
            try {
                FileOutputStream out = new FileOutputStream(codeFile);
                out.write(data.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //激活Handler在主线程处理刷新事件
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setDirSource(codeFile);
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////        SOURCE        ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     *  设置文件源，以代码形式载入文件信息
     *
     *  @param dir   文件路径。注意，必须是路径形式。
     *               例：new File("dir/cache/source.java");
     *               错：new File("dir/cache/", "source.java");
     */
    public void setDirSource(File dir) {

        //先启动等待Dialog
        progressDialog.show();

        //更新文件对象
        this.codeFile = dir;
        String content = "";

        /////////////////
        //// Read File
        try {
            FileInputStream in = new FileInputStream(dir);
            ByteArrayOutputStream bufferOut = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            for(int i = in.read(buffer, 0, buffer.length); i > 0 ; i = in.read(buffer, 0, buffer.length)) {
                bufferOut.write(buffer, 0, i);
            }
            content = new String(bufferOut.toByteArray(), Charset.forName("utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        /////////////////
        //// Load File
//        try {
//            Log.e("PATH----->", content);
//            this.loadUrl(dir.toURI().toURL().toString());
            this.loadDataWithBaseURL(STYLE_DIR, HTMLcode.HTMLHEAD + content + HTMLcode.HTMLTAIL, "text/html", null, null);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }

    public void requestContentReader(){
        loadUrl(JS_COMMAND_READ);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////       EDITABLE       ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * 通过JS修改代码域“可编辑”属性
     *
     * @param enable 使能状态    True for Editable       False for UnEditable
     *                          关闭编辑模式时存储改变后内容
     */
    public void setContentEditable(boolean enable){
        editEnable = enable;

        if(editEnable)
            this.loadUrl(JS_COMMAND_EDITABLE);
        else {
            this.loadUrl(JS_COMMAND_UNEDITABLE);
            this.loadUrl(JS_COMMAND_WRITE);
        }
    }

    public boolean isEditable() {
        return editEnable;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////       LISTENER       ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    public void setListener(OnCodeChangedListener listener) {
        this.listener = listener;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////         HTML         ///////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    public static class HTMLcode {
        public static String HTMLHEAD = "<head> <script src=\"code.js\"></script> " +
                "<link href=\"code.css\" rel=\"stylesheet\"> " +
                "</head>" +
                "<body bgcolor=\"#272822\"> " +
                "    <pre class=\"line-numbers\"><code class=\"language-javascript\">";

        public static String HTMLTAIL = "</code></pre></body>";
    }

}
