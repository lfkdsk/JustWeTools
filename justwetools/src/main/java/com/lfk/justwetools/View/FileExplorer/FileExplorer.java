package com.lfk.justwetools.View.FileExplorer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lfk.justwetools.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileExplorer extends ListView {

    private String dir = Environment.getExternalStorageDirectory().getPath();
    private String root = Environment.getExternalStorageDirectory().getPath();
    private List<Map<String, Object>> fileList = new ArrayList<>();
    private OnFolderChosenListener folderChosenListener = null;
    private OnPathChangedListener pathChangedListener = null;
    private OnFileChosenListener fileChosenListener = null;
    private SimpleAdapter listAdapter = null;
    private Context context;

    ////////////////////////////////////////////////////////////////////////////
    //////////////////        Default Constructor        ///////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public FileExplorer(Context context) {
        super(context);
        initList(context);
    }

    public FileExplorer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initList(context);
    }

    public FileExplorer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initList(context);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////        Automatic Constructor        ///////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * 自定义初始化
     */
    private void initList(Context context) {
        this.context = context;
        listAdapter = new SimpleAdapter(
                context,
                fileList,
                R.layout.list_item,
                new String[]{"image", "filename"},
                new int[]{R.id.list_image, R.id.list_text});
        initListener();
        this.setAdapter(listAdapter);
        setCurrentDir(dir);
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////        Listener        ///////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    private void initListener() {
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = ((TextView) view.findViewById(R.id.list_text)).getText().toString();
                try {
                    File file = new File(dir + "/" + fileName);
                    if (file.isFile()) {
                        if(fileChosenListener == null) {
                            String mime = getMIMEType(file);
                            if (mime != null) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse("file://" + file.getPath()), mime);
                                context.startActivity(intent);
                            }
                        } else {
                            fileChosenListener.onFileChosen(Uri.parse("file://" + file.getPath()));
                        }
                    } else if (file.isDirectory()) {
                        setCurrentDir(file.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
                final String fileName = ((TextView) view.findViewById(R.id.list_text)).getText().toString();
                if(new File(dir + "/" + fileName).isDirectory()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确认选择该文件夹吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (folderChosenListener != null)
                                folderChosenListener.onItemChosen(dir + "/" + fileName);
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                return true;
            }
        });
    }

    public void setOnItemChosenListener(OnFolderChosenListener listener) {
        this.folderChosenListener = listener;
    }

    public void setOnPathChangedListener(OnPathChangedListener listener) {
        this.pathChangedListener = listener;
    }

    public void setOnFileChosenListener(OnFileChosenListener fileChosenListener) {
        this.fileChosenListener = fileChosenListener;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////        File Utils       ///////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * 获取文件的后缀名
     *
     * @param file  接受一个文件，返回该文件的后缀名，如果是目录则返回null
     */
    public String getType(File file){
        if(file.isDirectory())
            return null;

        int dotIndex = file.getName().lastIndexOf(".");
        if(dotIndex < 0)
            return "";

        return file.getName().substring(dotIndex).toLowerCase();
    }

    /**
     * 获取文件的MIME类型
     *
     * @param file   接受一个文件，返回该文件的MIME，如果是目录则返回null
     */
    private String getMIMEType(File file) {
        String type = getType(file);

        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        if(type != null) {
            boolean found = false;
            for (String[] data : MIME_Table) {
                if (type.equals(data[0])) {
                    type = data[1];
                    found = true;
                    break;
                }
            }
            if(!found)
                type = "*/*";
        }
        return type;
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////        Settings        ///////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * 设置并刷新浏览目录
     */
    public void setCurrentDir(String path) {
        File[] files = (new File(path)).listFiles();

        //如果这不是一个路径
        if (files == null)
            return;

        dir = path;

        //更新Map
        fileList.clear();

        for (File file : files) {
            Map<String, Object> fileInfo = new Hashtable<>();

            if (file.isFile())
                fileInfo.put("image", Images[1]);
            else
                fileInfo.put("image", Images[0]);

            fileInfo.put("filename", file.getName());
            fileList.add(fileInfo);
        }

        listAdapter.notifyDataSetChanged();

        if(pathChangedListener != null)
            pathChangedListener.onPathChanged(path);
    }

    /**
     * 返回上一级目录
     */
    public boolean toParentDir() {
        File file = new File(dir);
        String parentDir = file.getParent();

        if(parentDir != null && !file.getPath().equals(root)) {
            dir = parentDir;
            setCurrentDir(dir);
            return true;
        }
        return false;
    }

    public void setRootDir(String rootDir) {
        this.root = rootDir;
    }

    /**
     * 刷新重新载入
     */
    public void refresh() {
        setCurrentDir(dir);
    }

    /**
     * 获取当前路径
     */
    public String getCurrentPath() {
        return dir;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////          Extras         ///////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    /**
     * 获取文件类型大小比例
     */
    private void getProportion (TreeMap<String,Long> map,String path) throws Exception {
        File[] files = new File(path).listFiles();

        //如果这不是一个路径
        if (files == null)
            throw new Exception("EMPTY OR ROOT FILE");

        for (File file : files) {
            if (file.isFile()) {
                String type = getType(file).replace(".", "");
                if (map.containsKey(type))
                    map.put(type, map.get(type) + file.length());
                else
                    map.put(type, file.length());
            } else if (file.isDirectory()) {
                getProportion(map, file.getPath());
            }
        }
    }

    public TreeMap<String, Long> getPropotionText(String path) throws Exception {
        TreeMap<String, Long> buf = new TreeMap<>();
        getProportion(buf, path);
        TreeMap<String, Long> map = new TreeMap<>(new SizeComparator(buf));
        map.putAll(buf);
        return map;
    }

    class SizeComparator implements Comparator<String> {
        TreeMap<String, Long> map;
        SizeComparator(TreeMap<String, Long> map){
            this.map = map;
        }

        @Override
        public int compare(String a, String b) {
            if(map.get(a).equals(map.get(b)))
                return 0;
            return map.get(a) > map.get(b) ? 1 : -1;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////        Resources        ///////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    // 文件夹&文件 图片
    private int[] Images = {R.drawable.file,R.drawable.afile};


    //MIME类型集合，{后缀名，MIME类型}
    public static final String[][] MIME_Table = {
            {".3gp", "video/3gp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

}
