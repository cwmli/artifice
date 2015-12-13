package com.underwaterotter.gleswrap;

import android.opengl.GLES20;

public class Program {

    private int programHandle;

    public Program(){
        programHandle = GLES20.glCreateProgram();
    }

    public int getProgramHandle(){
        return programHandle;
    }

    public void attach(Shader shader, boolean keep){
        GLES20.glAttachShader(programHandle, shader.getShaderHandle());

        if(keep == false){
            shader.delete();
        }
    }

    public void detach(Shader shader){
        GLES20.glDetachShader(programHandle, shader.getShaderHandle());
    }

    public void link(){
        GLES20.glLinkProgram(programHandle);

        final int[] status = new int[1];
        GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, status, 0);
        if (status[0] == 0){
            throw new Error( GLES20.glGetProgramInfoLog(programHandle) );
        }
    }

    public void bindAttrib(int index, String name){
        GLES20.glBindAttribLocation(programHandle, index, name);
    }

    public Uniform getUniform(String name){
        return new Uniform(GLES20.glGetUniformLocation(programHandle, name));
    }

    public Attribute getAttribute(String name){
        return new Attribute(GLES20.glGetAttribLocation(programHandle, name));
    }

    public void use(){
        GLES20.glUseProgram(programHandle);
    }

    public void delete(){
        GLES20.glDeleteProgram(programHandle);
    }
}
