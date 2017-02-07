package com.underwaterotter.artifice.world;

import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Game;

import java.util.HashMap;

public abstract class AnimatedTilemap extends WorldTilemap{

    HashMap<Integer, int[]> tileAnimations; //int[] -> terrain id in Terrain.java
    int frames;

    private boolean playing;

    private boolean repeat;

    private float timer;
    private float frameDuration;

    private int currentFrame;

    public AnimatedTilemap(String tiles, Level level, WorldTilemap.TILEMAP type, int fps){
        super(tiles, level, type);

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
        } else if (repeat){
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
