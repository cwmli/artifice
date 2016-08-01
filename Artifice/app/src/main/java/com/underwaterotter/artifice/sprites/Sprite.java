package com.underwaterotter.artifice.sprites;

import com.underwaterotter.ceto.Player;

public class Sprite extends Player {

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

    @Override
    public void updateBounds(){

    }

    public void updateDirection(){

        if(velocity.x < 0){
            flipHorizontal = true;
        } else if (velocity.x > 0){
            flipHorizontal = false;
        }
    }
}
