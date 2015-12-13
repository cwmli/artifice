package com.underwaterotter.math;

import android.graphics.Rect;

import java.util.ArrayList;

public class Quadtree {
    private int MAX_LAYERS = 5;
    private int MAX_OBJECTS;

    private int layer;
    private ArrayList objects;
    private Rect rectangleBounds;
    private Quadtree[] nodes;

    public Quadtree(int layer, Rect rectangleBounds){
        this.layer = layer;
        objects = new ArrayList();
        this.rectangleBounds = rectangleBounds;
        nodes = new Quadtree[4];
    }

    public void clear(){
        objects.clear();

        for(int i = 0; i < nodes.length; i++){
            if (nodes[i] != null){
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    private void divide(){
        int offsetWidth = (int)rectangleBounds.width()/2;
        int offsetHeight = (int)rectangleBounds.height()/2;
        int x = rectangleBounds.width();
        int y = rectangleBounds.height();

        //-------------
        //|n[0] |n[1] |
        //|-----|-----|
        //|n[2] |n[3] |
        //-------------

        nodes[0] = new Quadtree(layer+1, new Rect(x, y, x + offsetWidth, y + offsetHeight));
        nodes[1] = new Quadtree(layer+1, new Rect(x + offsetWidth, y, rectangleBounds.width(), y + offsetHeight));
        nodes[2] = new Quadtree(layer+1, new Rect(x, y + offsetHeight, x + offsetWidth, rectangleBounds.height()));
        nodes[3] = new Quadtree(layer+1, new Rect(x + offsetWidth, y + offsetHeight, rectangleBounds.width(), rectangleBounds.height()));
    }
}
