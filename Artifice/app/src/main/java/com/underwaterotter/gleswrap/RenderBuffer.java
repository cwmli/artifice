package com.underwaterotter.gleswrap;

import android.opengl.GLES20;

public class RenderBuffer {

    public static final int RGB = GLES20.GL_RGB;
    public static final int RGBA = GLES20.GL_RGBA;
    public static final int DEPTHC = GLES20.GL_DEPTH_COMPONENT;
    public static final int STENCILI = GLES20.GL_STENCIL_INDEX8;

    private int[] bufferHandle = new int[1];

    public RenderBuffer(){
        GLES20.glGenRenderbuffers(1, bufferHandle, 0);
    }

    public int getRenderBuffer(){
        return bufferHandle[0];
    }

    public int getParamiv(int param){
        int[] result = new int[1];
        GLES20.glGetRenderbufferParameteriv(bufferHandle[0], param, result, 0);

        return result[0];
    }

    public void bind(){
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, bufferHandle[0]);
    }

    public void allocate(int format, int width, int height){
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, format, width, height);
    }
}
