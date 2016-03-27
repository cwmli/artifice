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

        hitbox.set(pos.x, pos.y, pos.x + width, pos.y + height);

        updateDirection();
    }

    public void updateDirection(){

        if(velocity.x < 0){
            flipHorizontal = true;
        } else if (velocity.x > 0){
            flipHorizontal = false;
        }
    }
}
