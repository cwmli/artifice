package com.underwaterotter.gleswrap;

import android.opengl.GLES20;

public class FrameBuffer {

    public static final int COLOR = GLES20.GL_COLOR_ATTACHMENT0;
    public static final int DEPTH = GLES20.GL_DEPTH_ATTACHMENT;
    public static final int STENCIL = GLES20.GL_STENCIL_ATTACHMENT;

    private int[] bufferHandle = new int[1];

    public FrameBuffer(){
        GLES20.glGenFramebuffers(1, bufferHandle, 0);
    }

    public int getFrameBuffer(){
        return bufferHandle[0];
    }

    public int status(){
        return GLES20.glCheckFramebufferStatus(bufferHandle[0]);
    }

    public void bind(){
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, bufferHandle[0]);
    }

    public void attachTexture(int attachment, int texture){
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, attachment, GLES20.GL_TEXTURE_2D, texture, 0);
    }

    public void attachRenderBuffer(int attachment, int renderBuf){
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, attachment, GLES20.GL_TEXTURE_2D, renderBuf);
    }

    public void delete(){
        GLES20.glDeleteFramebuffers(1, bufferHandle, 0);
    }

}
