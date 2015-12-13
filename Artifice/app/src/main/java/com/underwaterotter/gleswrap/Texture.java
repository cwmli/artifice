package com.underwaterotter.gleswrap;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.IntBuffer;

public class Texture {

    public int textureHandle;

    public Texture(){
        int ids[] = new int[1];
        GLES20.glGenTextures(1, ids, 0);
        textureHandle = ids[0];
        bind();
    }

    public static void activate(int index){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE+index);
    }

    public void bind(){
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
    }

    public void wrap(int wrap_s, int wrap_t){
        bind();
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, wrap_s);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, wrap_t);
    }

    public void filter(int scale_max, int scale_min){
        bind();
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, scale_max);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, scale_min);
    }

    public void delete(){
        int ids[] = {textureHandle};
        GLES20.glDeleteTextures(1, ids, 0);
    }

    //texture methods
    public void bitmap(Bitmap bitmap){
        bind();
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    }
}
