package com.underwaterotter.ceto;

import android.graphics.Rect;
import android.opengl.Matrix;

import com.underwaterotter.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class Camera extends Article {

    public static ArrayList<Camera> cameras = new ArrayList<Camera>();
    private static final int SCREEN_PADDING = 30;

    public static Camera main;
    public static Overlay target;

    protected float[] mCameraMatrix = new float[16];
    protected static float invW2;
    protected static float invH2;

    //position of camera to screen
    protected Vector2 pos;

    protected Vector2 distanceToFocus;

    //view range corrected by zoom
    protected int viewWidth;
    protected int viewHeight;

    protected float zoom; //fragment zoom of 1

    //actual screen size
    protected int screenWidth;
    protected int screenHeight;

    private float aspectRatio;

    public static Camera defaultCamera(float zoom){

        invW2 = 2f / Game.width;
        invH2 = 2f / Game.height;

        //sizeof view range
        int w = (int)Math.ceil(Game.width / zoom);
        int h = (int)Math.ceil(Game.height / zoom);

        main = new Camera(
                new Vector2((int)(Game.width - w * zoom) / 2,
                        (int)(Game.height - h * zoom) / 2),
                w,
                h,
                zoom);

        return add(main);
    }

    public static Camera createCamera(float zoom){
        //sizeof view range
        int w = (int)Math.ceil(Game.width / zoom);
        int h = (int)Math.ceil(Game.height / zoom);

        Camera camera = new Camera(
                new Vector2(
                        (int)(Game.width - w * zoom) / 2,
                        (int)(Game.height - h * zoom) / 2),
                        w,
                        h,
                        zoom);

        return add(camera);
    }

    public static Camera add(Camera camera){
        cameras.add(camera);
        return camera;
    }

    public static void remove(Camera camera){
        camera.destroy();
        cameras.remove(camera);
    }

    public static void clear(){
        for(Camera c : cameras){
            c.destroy();
        }
        cameras.clear();
    }

    public static void updateCameras(){
        for(Camera c : cameras){
            c.update();
        }
    }

    public Camera(Vector2 pos, int width, int height, float zoom){

        this.pos = pos;
        viewWidth = width;
        viewHeight = height;
        this.zoom = zoom;

        screenWidth = (int)(width * zoom);
        screenHeight = (int)(height * zoom);

        distanceToFocus = new Vector2();

        aspectRatio = (float)Game.width / (float)Game.height;

        Matrix.setIdentityM(mCameraMatrix, 0);
    }

    @Override
    public void update(){

        super.update();

        if(target != null){
            setFocusPoint(target.center());
        }

        updateMatrix();
    }

    @Override
    public void destroy(){
        target = null;
        mCameraMatrix = null;
    }

    public void resize(int width, int height){
        viewWidth = width;
        viewHeight = height;

        screenWidth = (int)(viewWidth * zoom);
        screenHeight = (int)(viewHeight * zoom);
    }

    public Vector2 center(){
        return new Vector2(viewWidth / 2, viewHeight / 2);
    }

    public void zoom(float value){
        zoom(value, new Vector2(
                distanceToFocus.x + viewWidth / 2,
                distanceToFocus.y + viewHeight / 2));
    }

    public void zoom(float value, Vector2 focus) {

        zoom = value;
        viewWidth = (int) (screenWidth / zoom);
        viewHeight = (int) (screenHeight / zoom);

        setFocusPoint(focus);
    }

    public Rect getScreenBoundries(){
        return new Rect((int) this.distanceToFocus.x - SCREEN_PADDING,
                (int) this.distanceToFocus.y - SCREEN_PADDING,
                (int) this.distanceToFocus.x + viewWidth,
                (int) this.distanceToFocus.y + viewHeight);
    }

    public int getViewWidth(){
        return viewWidth;
    }

    public int getViewHeight(){
        return viewHeight;
    }

    public boolean inScreenView(float x, float y){
        return x >= this.distanceToFocus.x - SCREEN_PADDING
                && x < this.distanceToFocus.x + viewWidth &&
               y >= this.distanceToFocus.y - SCREEN_PADDING
                && y < this.distanceToFocus.y + viewHeight;
    }

    public void setFocusPoint(float x, float y){
        //check from center of current view
        distanceToFocus.set(x - viewWidth / 2, y - viewHeight / 2);
    }

    public void setFocusPoint(Vector2 vec2){
        setFocusPoint(vec2.x, vec2.y);
    }

    public Vector2 screenToCamera(int screen_x, int screen_y){
        return new Vector2(
                (screen_x - pos.x) / zoom + distanceToFocus.x,
                (screen_y - pos.y) / zoom + distanceToFocus.y);
    }

    public Vector2 cameraToScreen(int camera_x, int camera_y){
        return new Vector2(
                (camera_x - distanceToFocus.x) * zoom + pos.x,
                (camera_y - distanceToFocus.y) * zoom + pos.y);
    }

    public void updateMatrix(){
        /*
        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.translateM(mViewMatrix, 0, distanceToFocus.x, distanceToFocus.y, 0);
        Matrix.orthoM(mProjectionMatrix, 0, 0, aspectRatio, 1, 0, -1, 1);
        Matrix.scaleM(mProjectionMatrix, 0, 2f / Game.height, 2f / Game.width, 1);
        Matrix.scaleM(mProjectionMatrix, 0, zoom, zoom, 1);*/


        mCameraMatrix[0] = +zoom * invW2;
        mCameraMatrix[5] = -zoom * invH2;

        mCameraMatrix[12] = -1 + pos.x * invW2 - distanceToFocus.x * mCameraMatrix[0];
        mCameraMatrix[13] = +1 - pos.y * invH2 - distanceToFocus.y * mCameraMatrix[5];
    }
}
