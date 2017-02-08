package com.underwaterotter.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class BitmapCache {

    public static Context context;

    private static BitmapFactory.Options options = new BitmapFactory.Options();
    static { options.inDither = false; }

    private static HashMap<String,Bit> bmpCache = new HashMap<String, Bit>();

    public static Bitmap get(String cache, String assetName){
        Bit bit = new Bit();
        if (!bmpCache.containsKey(cache)){
            bmpCache.put(cache, bit);
        } else {
            bit = bmpCache.get(cache);
        }

        if (bit.containsKey(assetName)){
            return bit.get(assetName);
        } else {
            InputStream is;
            try{
                is =  context.getResources().getAssets().open(assetName);
            } catch (IOException e) {
                return null;
                //e.printStackTrace();
            }
            Bitmap asset = BitmapFactory.decodeStream(is, null, options);
            bit.put(assetName, asset);
            return asset;
        }
    }

    public static Bitmap get(String cache, int resID){
        Bit bit = new Bit();
        if (!bmpCache.containsKey(cache)){
            bmpCache.put(cache, bit);
        } else {
            bit = bmpCache.get(cache);
        }

        if (bit.containsKey(resID)){
            return bit.get(resID);
        } else {
            Resources resource = context.getResources();
            Bitmap asset = BitmapFactory.decodeResource(resource, resID, options);
            bit.put(resID, asset);
            return asset;
        }
    }

    public static void clear(String cache){
        if (bmpCache.containsKey(cache)){
            bmpCache.get(cache).clear();
            bmpCache.remove(cache);
        }
    }

    public static void clear(){
        for ( Bit bit : bmpCache.values()){
            bit.clear();
        }
        bmpCache.clear();
    }

    @SuppressWarnings("serial")
    private static class Bit extends HashMap<Object, Bitmap> {

        @Override
        public void clear() {
            for (Bitmap bmp : values()) {
                bmp.recycle();
            }
            super.clear();
        }
    }
}
