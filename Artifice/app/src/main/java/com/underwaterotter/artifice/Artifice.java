package com.underwaterotter.artifice;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.underwaterotter.ceto.Game;
import com.underwaterotter.artifice.scenes.MenuScene;
import com.underwaterotter.artifice.world.generation.Level;


public class Artifice extends Game {

    public static Settings settings;

    private static Level level;
    private static int depth = 0;

    public Artifice() {
        super(MenuScene.class);
    }

    public static int getDepth(){
        return depth;
    }

    public static Level getLevel() {
        return level;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        settings = new Settings();

        //setup sound & music

        checkImmersiveMode();
        Game.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        checkImmersiveMode();
    }

    public static void switchLevel(Level slevel){

        if(level != null){
           level.destroy();
        }

        level = slevel;
    }

    public static void checkImmersiveMode(){
        if(settings.getBoolean(Settings.KEY_IMMERSIVE, Preferences.DEFAULT.IMMERSIVE)){
            immersiveMode();
        }
    }

    public static void nonImmersiveMode(){
        Game.instance.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    public static void immersiveMode(){

        if(Build.VERSION.SDK_INT > 19) {
            Game.instance.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    public static void immersive(boolean preference){
        settings.putValue(Settings.KEY_IMMERSIVE, preference);
        checkImmersiveMode();
    }
}
