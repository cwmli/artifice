package com.underwaterotter.utils;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.HashMap;

/**
 * Load images containing several other sprites and split them by rects
 */

public class BitmapSequence {

    public HashMap<Integer, Rect> bitmaps = new HashMap<Integer, Rect>();

    private Bitmap bitmap;

    public BitmapSequence(Bitmap bitmap){
        this.bitmap = bitmap;
        this.add(1 , new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public BitmapSequence(Bitmap bitmap, int frame_width, int frame_height){
        this.bitmap = bitmap;
        int rows = bitmap.getHeight()/frame_height;
        int cols = bitmap.getWidth()/frame_width;

        for(int y = 0; y < rows-1; y++){
            for(int x = 0; x < cols-1; x++){
                this.add(x, new Rect(x * frame_width, y * frame_height, x * frame_width + frame_width, y * frame_height + frame_height));
            }
        }

    }

    public void add(int bit_id, Rect rect){
        bitmaps.put(bit_id, rect);
    }

    public void remove(int bit_id){
        if (bitmaps.containsKey(bit_id)) {
            Rect rect = bitmaps.get(bit_id);
            rect = null;
            bitmaps.remove(bit_id);
        }
    }

    public void clear(){
        for(Rect rect : bitmaps.values()){
            rect = null;
        }
        bitmaps.clear();
    }
}
