package com.underwaterotter.artifice;

import android.content.SharedPreferences;

import com.underwaterotter.ceto.Game;

public class Settings {

    public static final String KEY_UI_SCALE = "uiscale";
    public static final String KEY_IMMERSIVE = "immersive";
    public static final String KEY_SOUND = "sound";
    public static final String KEY_MUSIC = "music";
    public static final String KEY_GRAPHICS = "graphics";
    public static final String KEY_GOOGLE_PLAY = "googleplay";

    private SharedPreferences instancePrefs;

    public Settings(){
        instancePrefs = Game.instance.getPreferences(Game.MODE_PRIVATE);
    }

    public boolean getBoolean(String key, Boolean defaultValue){
        return instancePrefs.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue){
        return instancePrefs.getInt(key, defaultValue);
    }

    public String getString(String key, String defaultValue){
        return instancePrefs.getString(key, defaultValue);
    }

    public void putValue(String key, Boolean value){
        instancePrefs.edit().putBoolean(key, value).commit();
    }

    public void putValue(String key, int value){
        instancePrefs.edit().putInt(key, value).commit();
    }

    public void putValue(String key, String value){
        instancePrefs.edit().putString(key, value).commit();
    }
}
