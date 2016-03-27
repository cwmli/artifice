package com.underwaterotter.ceto;

import android.app.Activity;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.underwaterotter.cetoinput.Keys;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.glesutils.GLSL;
import com.underwaterotter.glesutils.TextureCache;
import com.underwaterotter.utils.BitmapCache;
import com.underwaterotter.utils.Time;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Game extends Activity implements GLSurfaceView.Renderer, View.OnTouchListener {

    public static Game instance;

    public static int width;
    public static int height;

    protected long elapsedMilli;
    protected long lastUpdateMilli;
    protected long elapsedUpdateMilli;

    protected Scene currentScene;
    protected Scene newScene;
    protected Class<? extends Scene> sceneType;

    protected static boolean requestScene = true;

    //time from last update in seconds
    public static float elapsedTime = 0f;
    //lower values mean slower times and higher values mean sped up times, value of 1f is normal
    public static float slowFactor = 1f;

    //xhdpi = 2.0, hdpi = 1.5, mdpi = 1.0, ldpi = 0.75
    public static float density = 1.0f; //base mdpi

    protected GLSurfaceView view;


    //touch event history
    protected ArrayList<MotionEvent> motionEvents = new ArrayList<MotionEvent>();
    protected ArrayList<KeyEvent> keyEvents = new ArrayList<KeyEvent>();

    public Game( Class<? extends Scene> s){
        super();
        sceneType = s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        TextureCache.context = BitmapCache.context = instance = this;
        //Serialized.context = this;
        //set density
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;

        //setup opengles activity
        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(this);
        view.setOnTouchListener(this);
        setContentView(view);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        destroyInstance();
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(currentScene != null){
            currentScene.resume();
        }

        view.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(currentScene != null){
            currentScene.pause();
        }

        view.onPause();
        GLSL.reset();
    }

    @Override
    public void onSurfaceCreated (GL10 unused, EGLConfig config){

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);

        TextureCache.reload();
    }

    @Override
    public void onDrawFrame (GL10 unused) {

        Time.tick();
        elapsedMilli = Time.time;
        elapsedUpdateMilli = elapsedMilli - lastUpdateMilli;
        lastUpdateMilli = elapsedMilli;

        update();

        Renderer.get().resetCamera();
        GLES20.glScissor(0, 0, width, height);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(255f, 255f, 255f, 1f);

        currentScene.draw();
    }

    @Override
    public void onSurfaceChanged( GL10 unused, int width, int height){
        GLES10.glViewport(0, 0, width, height);

        Game.width = width;
        Game.height = height;
}

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //track any touch events
        synchronized (motionEvents) {
            motionEvents.add( MotionEvent.obtain( event ) );
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN ||
                keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

            return false;
        }

        synchronized (keyEvents) {
            keyEvents.add(event);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN ||
                keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

            return false;
        }

        synchronized (keyEvents){
            keyEvents.add(event);
        }
        return true;
    }

    public void update(){

        if(requestScene){
            requestScene = false;
            try{
                newScene = sceneType.newInstance();
                switchScene();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        elapsedTime = slowFactor * elapsedUpdateMilli * 0.001f;

        synchronized (motionEvents){
            Motions.processMotionEvents(motionEvents);
            motionEvents.clear();
        }
        synchronized (keyEvents){
            Keys.processKeyEvents(keyEvents);
            keyEvents.clear();
        }

        currentScene.update();
        Camera.updateCameras();
    }

    public void destroyInstance(){

        if(currentScene != null){
            currentScene.destroy();
            currentScene = null;
        }

        instance = null;
    }

    public static void resetScene(){
        switchScene(instance.sceneType);
    }

    public static void switchScene(Class<? extends Scene> scene){
        instance.sceneType = scene;
        instance.requestScene = true;
    }

    public void switchScene(){

        Camera.defaultCamera(1);

        if(currentScene != null){
            currentScene.destroy();
        }
        currentScene = newScene;
        currentScene.create();

        Game.elapsedTime = 0f;
        Game.slowFactor = 1f;
    }
}
