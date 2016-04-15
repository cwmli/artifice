package com.underwaterotter.artifice;

import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.ceto.Game;

import java.util.HashMap;

public class AnimatedTilemap extends WorldTilemap{

    public HashMap<Terrain, int[]> tileAnimations;
    public int frames;

    private float timer;
    private float frameDuration;

    private int currentFrame;

    protected boolean repeat;

    public boolean playing;

    public AnimatedTilemap(int fps){
        super();

        currentFrame = 0;

        frameDuration = 1f / fps;
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
        for(int i = 0; i < map.length; i++){
            if(map[i] != Terrain.EMPTY){
                map[i] = tileAnimations.get(map[i])[currentFrame];
            }
        }

        if (currentFrame < frames){
            currentFrame++;
        } else if (repeat == true){
            currentFrame = 0;
        } else {
            playing = false;
        }
    }

    protected void setTileAnimations(){}
}
