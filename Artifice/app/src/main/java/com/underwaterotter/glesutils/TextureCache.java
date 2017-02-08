package com.underwaterotter.glesutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.util.HashMap;

public class TextureCache {

    public static Context context;

    private static HashMap<Object, ModelTexture> cache = new HashMap<Object, ModelTexture>();

    private static BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    static {
        bitmapOptions.inScaled = false;
        bitmapOptions.inDither = false;
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    public static ModelTexture add(Object name, ModelTexture tex){
        cache.put(name, tex);
        return tex;
    }

    public static boolean remove(Object name){
        if (!cache.containsKey(name)){
            ModelTexture res = cache.get(name);
            cache.remove(name);

            res.delete();
            res = null;

            return true;
        } else{
            return false;
        }
    }

    public static ModelTexture get(Object name){
        if (cache.containsKey(name)){
            return cache.get(name);
        } else {
            ModelTexture mt = new ModelTexture(loadBitmap((String)name));
            return add(name, mt);
        }
    }

    public static ModelTexture createRect(int w, int h, int color, boolean fill){
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bmp);

        paint.setColor(color);
        paint.setStrokeWidth(0);
        if(!fill) {
            paint.setStyle(Paint.Style.STROKE);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }

        canvas.drawRect(0, 0, w, h, paint);

        return new ModelTexture(bmp);
    }

    public static ModelTexture createCircle(int r, int color, boolean fill){
        Bitmap bmp = Bitmap.createBitmap(r * 2, r * 2, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bmp);

        paint.setColor(color);
        paint.setStrokeWidth(0);
        if(!fill) {
            paint.setStyle(Paint.Style.STROKE);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }

        canvas.drawCircle(r, r, r, paint);

        return new ModelTexture(bmp);
    }

    public static void clear(){
        for (ModelTexture tex : cache.values()){
            tex.delete();
        }
        cache.clear();
    }

    public static void reload(){
        for (ModelTexture tex : cache.values()){
            tex.reload();
        }
    }

    public static boolean contains(Object name) {
        return cache.containsKey(name);
    }

    public static Bitmap loadBitmap(String name){

        try{
            return BitmapFactory.decodeStream(context.getAssets().open(name));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
