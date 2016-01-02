package com.underwaterotter.ceto;

import com.underwaterotter.glesutils.TextureCache;

public class Player extends Image {

    public Animation activeAnimation;

    private int currentFrame;
    private float timer;

    public boolean playing;

    public Player(){
        super();

        currentFrame = 0;
        playing = false;
    }

    public Player(Object id){
        super(TextureCache.get(id));

        currentFrame = 0;
        playing = false;
    }

    public void play(Animation animation, boolean interrupt){

        if(activeAnimation != null && playing && interrupt){
            done();

            activeAnimation = animation;
            currentFrame = 0;
            timer = 0;

            playing = true;
        } else {
            return;
        }
    }

    public void forceStop(){

        if(activeAnimation != null && playing){
            playing = false;
            done();
        }
    }

    @Override
    public void update(){
        super.update();

        if(playing){
            updateFrame();
        }
    }

    public void updateFrame(){

        if(activeAnimation.frames.length > 1) {

            timer += Game.elapsedTime;

            while (timer > activeAnimation.frameDuration) {
                timer -= activeAnimation.frameDuration;
                next();
            }
        }
    }

    public void next(){
        if (currentFrame < activeAnimation.frames.length){
            textureRect(activeAnimation.frames[currentFrame++]);
        } else if (activeAnimation.repeat == true){
            currentFrame = 0;
            textureRect(activeAnimation.frames[currentFrame++]);
            done();
        } else {
            playing = false;
            done();
        }
    }

    public void prev(){
        if (currentFrame > 0){
            textureRect(activeAnimation.frames[currentFrame--]);
        } else {
            textureRect(activeAnimation.frames[currentFrame]);
            currentFrame = activeAnimation.frames.length-1;
        }
    }

    public void done(){}
}