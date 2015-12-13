package com.underwaterotter.gleswrap;

import android.opengl.GLES20;

import java.nio.FloatBuffer;

public class Attribute {

    private int location;

    public Attribute(int location){
        this.location = location;
    }

    public int getLocation(){
        return location;
    }

    public void enable(){
        GLES20.glEnableVertexAttribArray(location);
    }

    public void disable(){
        GLES20.glDisableVertexAttribArray(location);
    }

    public void setAttribPointer(int size, int stride, FloatBuffer vertices){
        GLES20.glVertexAttribPointer(location, size, GLES20.GL_FLOAT, false, stride * Float.SIZE / 8, vertices);
    }
}
