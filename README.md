# JustWeTools - Some useful tools
![logo](http://www.imgjav.com/images/justwe97bd9.png)

##JustWe 现在有哪些模块？

* `PaintView`画图工具
 
* `CodeView`代码编辑
* `ExplorerView`文件管理器

##模块如何使用：
* 将Demo作为library加入项目，或是直接将代码拷入

##JustWe 模块介绍：
* `PaintView`画图工具
    *  可直接使用设定按钮来实现已拥有的方法，且拓展性强
    *  基础功能：更换颜色、更换橡皮、以及更换橡皮和笔的粗细、清屏、倒入图片
    *  `特殊功能`：`保存画笔轨迹帧动画`、帧动画导入导出、ReDo和UnDo

###效果图：

![p1](https://github.com/lfkdsk/JustWeTools/blob/master/picture/gif.gif)
![p2](https://github.com/lfkdsk/JustWeTools/blob/master/picture/io.gif)

* 使用基础功能只需要： 

1.添加xml：

```xml
    <com.lfk.drawapictiure.PaintView
        android:id="@+id/paint"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
         />
```
2.在activity里找到：
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




##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件:lfk_dsk@hotmail.com
* weibo: [@亦狂亦侠_亦温文](http://www.weibo.com/u/2443510260)

