package com.underwaterotter.ceto;

import android.graphics.RectF;

public class Animation {

    public RectF[] frames;

    public float frameDuration;
    protected boolean repeat;


    public Animation(float fps, boolean repeat){
        this.frameDuration = 1f / fps;
        this.repeat = repeat;
    }

    public void setFrameDuration(float duration) {
        frameDuration = 1f / duration;
    }

    public void setFrames(RectF...frames){
        this.frames = frames;
    }

    public int length(){
        return frames.length;
    }
}
