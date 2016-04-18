package com.underwaterotter.artifice;

import android.util.Log;

import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.ceto.Game;

import java.util.HashMap;

public abstract class AnimatedTilemap extends WorldTilemap{

    public HashMap<Integer, int[]> tileAnimations; //int[] -> terrain id in Terrain.java
    public int frames;

    private float timer;
    private float frameDuration;

    private int currentFrame;

    protected boolean repeat;

    public boolean playing;

    public AnimatedTilemap(String tiles, int fps){
        super(tiles);

        currentFrame = 0;

        frameDuration = 1f / fps;
        playing = true;
        repeat = true;

        tileAnimations = new HashMap<>();

        setTileAnimations();
    }

    @Override
    public void update(){
        super.update();

        if(playing){
            updateFrame();
        }
    }

    public void updateFrame(){

        if(frames > 0) {

            timer += Game.elapsedTime;

            if (timer > frameDuration) {
                timer -= frameDuration;
                next();
            }
        }
    }
    public void next(){
        if (currentFrame < frames){
            for(int i = 0; i < map.length; i++){
                if(map[i] != Terrain.EMPTY && tileAnimations.containsKey(map[i])){
                    map[i] = tileAnimations.get(map[i])[currentFrame];
                }
            }
            currentFrame++;
        } else if (repeat == true){
            currentFrame = 0;
            for(int i = 0; i < map.length; i++){
                if(map[i] != Terrain.EMPTY && tileAnimations.containsKey(map[i])){
                    map[i] = tileAnimations.get(map[i])[currentFrame];
                }
            }
            currentFrame++;
        } else {
            playing = false;
        }
    }

    protected abstract void setTileAnimations();
}
