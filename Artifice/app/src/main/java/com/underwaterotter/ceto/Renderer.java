package com.underwaterotter.ceto;

import android.opengl.GLES20;

import com.underwaterotter.glesutils.GLSL;
import com.underwaterotter.glesutils.VertexQuad;
import com.underwaterotter.gleswrap.Attribute;
import com.underwaterotter.gleswrap.Shader;
import com.underwaterotter.gleswrap.Uniform;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

//TO DO:
//Optimize rendering through opengles instancing

public class Renderer extends GLSL {

    protected Uniform uPMatrix;
    protected Uniform uModel;
    protected Uniform uCamera;
    protected Uniform uTexture;

    protected Uniform mColor;
    protected Uniform aColor;

    protected Attribute aXY;
    protected Attribute aST;

    private Camera oldCamera;

    public Renderer(){

        super();
        attach(Shader.create(GLES20.GL_VERTEX_SHADER, VERTEX), true);
        attach(Shader.create(GLES20.GL_FRAGMENT_SHADER, FRAGMENT), true);
        link();

        uPMatrix = getUniform("uPMatrix");
        uCamera = getUniform("uCamera");
        uModel = getUniform("uModel");
        uTexture = getUniform("uTexture");

        mColor = getUniform("mColor");
        aColor = getUniform("aColor");

        aXY = getAttribute("aPosition");
        aST = getAttribute("aST");
    }

    @Override
    public void use(){

        super.use();

        aXY.enable();
        aST.enable();
    }

    public void resetCamera(){
        oldCamera = null;
    }

    public void changeCamera(Camera camera){

        if(camera == null){
            camera = Camera.main;
        }

        if(camera != oldCamera){
            oldCamera = camera;

            uCamera.valueMat4( camera.mCameraMatrix );

            GLES20.glScissor(
                    (int)camera.pos.x,
                    Game.height - camera.screenHeight - (int)camera.pos.y,
                    camera.screenWidth,
                    camera.screenHeight);
        }
    }

    public void drawElement(FloatBuffer vXY, FloatBuffer vST,
                            ShortBuffer indices, int indices_per_element){

        vXY.position(0);
        aXY.setAttribPointer(2, 2, vXY);

        vST.position(0);
        aST.setAttribPointer(2, 2, vST);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices_per_element,
                GLES20.GL_UNSIGNED_SHORT, indices);
    }

    public void drawVQuad(FloatBuffer vXY, FloatBuffer vST){

        vXY.position(0);
        aXY.setAttribPointer(2, 2, vXY);

        vST.position(0);
        aST.setAttribPointer(2, 2, vST);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, VertexQuad.INDICES_PER_QUAD,
                GLES20.GL_UNSIGNED_SHORT, VertexQuad.quadIndices(1));
    }

    public void drawVQuads(FloatBuffer vXY, FloatBuffer vST, int size){

        vXY.position(0);
        aXY.setAttribPointer(2, 2, vXY);

        vST.position(0);
        aST.setAttribPointer(2, 2, vST);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, VertexQuad.INDICES_PER_QUAD * size,
                GLES20.GL_UNSIGNED_SHORT, VertexQuad.quadIndices(size));
    }

    public static Renderer get(){
        return GLSL.use(Renderer.class);
    }

    public static final String VERTEX =
            "uniform mat4 uPMatrix;" +
                    "uniform mat4 uCamera;" +
                    "uniform mat4 uModel;" +
                    "attribute vec4 aPosition;" +
                    "attribute vec2 aST;" +
                    "varying vec2 vST;" +
                    "void main() {" +
                    "  gl_Position = uCamera * uModel * aPosition;" +
                    "  vST = aST;" +
                    "}";


    public static final String FRAGMENT =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "uniform vec4 mColor;" +
                    "uniform vec4 aColor;" +
                    "varying vec2 vST;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTexture, vST) * mColor + aColor;" +
                    "}";

}
