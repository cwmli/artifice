package com.underwaterotter.artifice.sprites;

import android.graphics.RectF;

import com.underwaterotter.ceto.Player;

public class Sprite extends Player {

    public RectF hitbox;

    public Sprite(){
        super();
    }

    public Sprite(Object id){
        super(id);
    }

    @Override
    public void update(){
        super.update();

        updateDirection();
    }

    public void updateDirection(){

        if(speed < 0){
            flipHorizontal = true;
        } else if (speed > 0){
            flipHorizontal = false;
        }
    }
}
