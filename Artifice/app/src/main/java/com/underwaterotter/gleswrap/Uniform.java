package com.underwaterotter.gleswrap;

import android.opengl.GLES20;

public class Uniform {

    private int location;

    public Uniform(int location){
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

    public void value1i(int value){
        GLES20.glUniform1i(location, value);
    }

    public void value1f(float value){
        GLES20.glUniform1f(location, value);
    }

    public void value2i(int x, int y){
        GLES20.glUniform2i(location, x, y);
    }

    public void value2f(float x, float y){
        GLES20.glUniform2f(location, x, y);
    }

    public void value3f(float x, float y, float z){
        GLES20.glUniform3f(location, x, y, z);
    }

    public void value4f(float x, float y, float z, float w){
        GLES20.glUniform4f(location, x, y, z, w);
    }

    public void valueMat3(float[] matrix){
        GLES20.glUniformMatrix3fv(location, 1, false, matrix, 0);
    }

    public void valueMat4(float[] matrix){
        GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0);
    }
}
