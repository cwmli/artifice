package com.underwaterotter.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon {

    public ArrayList vertices;

    private Bitmap bitmap;
    private int bmpWidth;
    private int bmpHeight;

    public Polygon( Bitmap bitmap ){
        vertices = new ArrayList();
        bmpWidth = bitmap.getWidth();
        bmpHeight = bitmap.getHeight();
        if (bitmap.hasAlpha()) {
            this.bitmap = bitmap.extractAlpha();
            boundingPolygon();
        } else {
            this.bitmap = bitmap;
        }
    }

    public Polygon(Polygon polygon){
        this.vertices = polygon.vertices;
        this.bitmap = polygon.bitmap;
        this.bmpWidth = polygon.bmpWidth;
        this.bmpHeight = polygon.bmpHeight;
    }

    //Used only if bitmap has an Alpha channel to dictate vertices;
    //otherwise, use built-in Rect supplied by android.
    public void boundingPolygon(){
        ArrayList<int[]> AlphaData = splitPixels();
        for (int row = 0; row < AlphaData.size() - 1; row++) {
            for (int x = 0; x < bmpWidth - 1; x++) {
                if (AlphaData.get(row)[x] < 0x80) {
                    continue;
                } else { //alpha is above 50%

                }
            }
        }
    }

    private ArrayList<int[]> splitPixels(){
        int[] pixels = new int[bmpHeight * bmpWidth];
        int chunkSize = bmpWidth;
        ArrayList<int[]> sampleBitmapData = new ArrayList<int[]>();
        int elapsedRows = 1;
        bitmap.getPixels(pixels,0, bmpWidth, 0, 0, bmpWidth, bmpHeight);
        for(int i = 0; i < pixels.length - 1; i++){
            //collect sample size and separate into rows according to bitmap width
            if (i == bmpWidth * elapsedRows){
                sampleBitmapData.add(Arrays.copyOfRange(pixels, i, bmpWidth * elapsedRows));
                elapsedRows += 1;
            }
        }
        return sampleBitmapData;
    }
}
