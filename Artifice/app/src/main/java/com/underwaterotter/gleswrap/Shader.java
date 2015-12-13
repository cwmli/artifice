package com.underwaterotter.gleswrap;

import android.opengl.GLES20;

public class Shader {

    private int shaderHandle;

    public Shader(int type){
        shaderHandle = GLES20.glCreateShader(type);
    }

    public int getShaderHandle(){
        return shaderHandle;
    }

    public void source(String src){
        GLES20.glShaderSource(shaderHandle, src);
    }

    public void compile(){
        final int[] status = new int[1];
        GLES20.glCompileShader(shaderHandle);

        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, status, 0);

        if (status[0] == 0){
            delete();
            shaderHandle = 0;

            final String shaderType;

            final int[] type = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_SHADER_TYPE, type, 0);

            switch (type[0]){
                case GLES20.GL_VERTEX_SHADER:
                    shaderType = "vertex";
                    break;
                case GLES20.GL_FRAGMENT_SHADER:
                    shaderType = "fragment";
                    break;
                default:
                    shaderType = null;
                    break;
            }

            throw new RuntimeException("Error creating "+shaderType+" shader.");
        }
    }

    public void delete(){
        GLES20.glDeleteShader(shaderHandle);
    }

    public static Shader create(int type, String source){

        Shader shader = new Shader(type);

        shader.source(source);
        shader.compile();

        return shader;
    }
}
