package com.lfk.justwetools.View.NewPaint;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by liufengkai on 15/8/25.
 */
public class PathNode extends Application{
    public class Node{
        public Node() {}
        public float x;
        public float y;
        public int PenColor;
        public int TouchEvent;
        public int PenWidth;
        public boolean IsPaint;
        public long time;
        public int EraserWidth;

    }
    private ArrayList<Node> PathList;


    public ArrayList<Node> getPathList() {
        return PathList;
    }

    public void addNode(Node node){
        PathList.add(node);
    }

    public Node NewAnode(){
        return new Node();
    }


    public void clearList(){
        PathList.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PathList = new ArrayList<Node>();
    }

    public void setPathList(ArrayList<Node> pathList) {
        PathList = pathList;
    }

    public Node getTheLastNote(){
        return PathList.get(PathList.size()-1);
    }

    public void deleteTheLastNote(){
        PathList.remove(PathList.size()-1);
    }

    public PathNode() {
        PathList = new ArrayList<Node>();
    }

}
