# JustWeTools - Some useful tools
![logo](http://www.imgjav.com/images/justwe97bd9.png)

##JustWe 现在有哪些模块?  

* View自定义控件  
   * `PaintView`画图工具  
   * `CodeView`代码编辑  
   * `ExplorerView`文件管理器  
   * `ReadView`小说阅读器  
   * `MarkDownView`支持MarkDown语法的文字渲染器  
   * `VerTextView`支持竖行排版/下划线的TextView  
   


##模块如何使用：
* 将Demo作为library加入项目，或是直接将代码拷入

##JustWe 模块介绍:  

* View自定义控件:  
* `PaintView`画图工具:
    *  可直接使用设定按钮来实现已拥有的方法，且拓展性强
    *  基础功能：更换颜色、更换橡皮、以及更换橡皮和笔的粗细、清屏、倒入图片
    *  `特殊功能`：`保存画笔轨迹帧动画`、帧动画导入导出、ReDo和UnDo

###效果图：

![p1](https://github.com/lfkdsk/JustWeTools/blob/master/picture/gif.gif)
![p2](https://github.com/lfkdsk/JustWeTools/blob/master/picture/io.gif)

* 使用基础功能只需要： 

####1.1 添加xml：

```xml
    <com.lfk.drawapictiure.PaintView
        android:id="@+id/paint"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
         />
```
####1.2 在activity里找到：

``` java
    paintView = (PaintView)findViewById(R.id.paint);
```

* 若想使用帧动画相关功能：
需要新建数据集，设定纪录paintview，并且设定onPathListener（）

``` java
    pathNode = (PathNode)getApplication();
    paintView.setIsRecordPath(true,pathNode);
    paintView.setOnPathListener(new OnPathListener() {
        @Override
    public void AddNodeToPath(float x, float y, int event, boolean IsPaint) {
        PathNode.Node tempnode = pathNode.new Node();
        tempnode.x = x;
        tempnode.y = y;
        if (IsPaint) {
            tempnode.PenColor = UserInfo.PaintColor;
            tempnode.PenWidth = UserInfo.PaintWidth;
        } else {
            tempnode.EraserWidth = UserInfo.EraserWidth;
        }
        tempnode.IsPaint = IsPaint;
        Log.e(tempnode.PenColor + ":" + tempnode.PenWidth + ":" + tempnode.EraserWidth, tempnode.IsPaint + "");
        tempnode.TouchEvent = event;
        tempnode.time = System.currentTimeMillis();
        pathNode.AddNode(tempnode);
    }
});
```
相关的教程和解析请看：[PaintView 绘图控件解析](http://www.cnblogs.com/lfk-dsk/p/4768850.html)  
图例中出现的Demo:  [图例Demo](https://github.com/lfkdsk/JustWeTools/tree/master/demo-project/DrawAPictiure)  
图例中使用了两个开源控件:  
[CircularFloatingActionMenu](https://github.com/oguzbilgener/CircularFloatingActionMenu) 和 [android-ColorPickerPreference](https://github.com/attenzione/android-ColorPickerPreference)  

***
* `CodeView`代码查看／修改工具: 
    * 基于WebView制作的代码编辑器
    * 实现代码高亮，暗色主题 
    * 代码及时修改

###效果图：
![p3](https://github.com/lfkdsk/JustWeTools/blob/master/picture/accomplish.png)
![p4](https://github.com/lfkdsk/JustWeTools/blob/master/picture/edit.png)

* 使用基础功能只需要：

####2.1 添加xml：
    
``` xml
    <com.lfk.justwetools.CodeView.CodeView
        android:id="@+id/mcodeview"
        android:layerType="hardware"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
####2.2 在Activity中获取路径：


``` java
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
```

如果是手动复制代码的话，需要复制assests文件夹下的js文件。

####2.3 编辑修改：
``` java
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
```
***

* ExplorerView 文件浏览器：
    * 继承自ListView
    * 可拓展性强
    * 可进行文件类型分析

### 效果图：
![p5](https://github.com/lfkdsk/JustWeTools/blob/master/picture/explorer1.png)
![p6](https://github.com/lfkdsk/JustWeTools/blob/master/picture/explorer2.png)

* 使用基础功能

####3.1 添加xml：
``` xml
    <com.lfk.justwetools.FileExplorer.FileExplorer
        android:id="@+id/ex"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
####3.2 在Activity里面：

``` java
        fileExplorer = (FileExplorer)findViewById(R.id.ex);
```
此时默认的打开路径为sd卡根目录：
可通过如下修改：
``` java
    // 打开路径
    fileExplorer.setCurrentDir(Environment.getExternalStorageDirectory().getPath());
    // 根路径（能到达最深的路径，以此避免用户进入root路径）
    fileExplorer.setRootDir(Environment.getExternalStorageDirectory().getPath());
```
Item的点击事件：
``` java
        //覆盖屏蔽原有长按事件
        fileExplorer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        //选择文件 默认打开CodeView
        fileExplorer.setOnFileChosenListener(new OnFileChosenListener() {
            @Override
            public void onFileChosen(Uri fileUri) {
                Intent intent = new Intent(ExplorerActivity.this, CodeActivity.class);
                intent.setData(fileUri);
                startActivity(intent);
            }
        });
```
返回键返回上一级：
``` java 
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(!fileExplorer.toParentDir()){
                if(System.currentTimeMillis() - exitTime < 1000)
                    finish();
                exitTime = System.currentTimeMillis();
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
```

* `高级功能`: 根据文件夹内的各种文件类型的大小比例，分析比例图，不建议在sd卡根目录使用
    内容过多反应较慢

####3.3 添加xml：

``` xml
    <com.lfk.justwetools.Proportionview.ProportionView
        android:id="@+id/pv"
        android:layout_margin="10dp"
        android:layout_width="match_parent"
        android:layout_height="30dp" />
```

####3.4 在Activity中添加：
``` java
    final ProportionView view = (ProportionView) findViewById(R.id.pv);
```
注册分析文件比例的接口：
``` java
    //新路径下分析文件比例
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
```
***  

* `ReadView`小说阅读：
    * 基于Canvas制作的小说阅读  
    * 可更换字体、字号、字颜色 
    * 拓展性强  

### 效果图：  
![p7](https://github.com/lfkdsk/JustWeTools/blob/master/picture/readbook.png)  

* 使用基础功能只需要：
``` java
   ReadView readView = new ReadView(this,dir.getPath());
   setContentView(readView);
```  
* 如果需要打开文件时调用请修改manifest和：  
``` java
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
```
***
##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件:lfk_dsk@hotmail.com  
* weibo: [@亦狂亦侠_亦温文](http://www.weibo.com/u/2443510260)  
* 博客:  [刘丰恺](http://www.cnblogs.com/lfk-dsk/)  

## License

    Copyright 2015 [刘丰恺](http://www.cnblogs.com/lfk-dsk/)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

