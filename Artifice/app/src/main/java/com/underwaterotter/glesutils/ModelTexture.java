package com.underwaterotter.glesutils;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.opengl.GLES20;

import com.underwaterotter.gleswrap.Texture;

public class ModelTexture extends Texture {

    private int width;
    private int height;

    private TextureAtlas atlas;

    private Bitmap bitmap;

    public ModelTexture(Bitmap bitmap){
        this(bitmap, GLES20.GL_NEAREST, GLES20.GL_CLAMP_TO_EDGE);
    }

    public ModelTexture(Bitmap bitmap, int filtermethod, int wrapmethod){
        super();

        bitmap(bitmap);
        filter(filtermethod, filtermethod);
        wrap(wrapmethod, wrapmethod);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void bitmap(Bitmap bitmap){
        super.bitmap(bitmap);

        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    public void reload(){
        textureHandle = new ModelTexture(bitmap).getTextureHandle();
        //support saved wrap and filter methods
    }

    @Override
    public void delete(){
        super.delete();

        bitmap.recycle();
        bitmap = null;
    }

    public RectF stRect(float left, float top, float right, float bottom){

        //normalized texture coordinates
        return new RectF(
                left / width,
                top / height,
                right / width,
                bottom / height);
    }
}
