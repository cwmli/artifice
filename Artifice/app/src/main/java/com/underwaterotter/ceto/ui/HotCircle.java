package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.Group;
import com.underwaterotter.math.Vector2;

public class HotCircle extends Group {

    protected float x;
    protected float y;

    protected float radius;

    public HotCircle() {
        this(0, 0);
    }

    public HotCircle(float x, float y){
        super();

        this.x = x;
        this.y = y;

        createContent();
    }

    public void resize(float r){
        radius = r;

        updateHitbox();
    }

    public float radius(){
        return radius;
    }

    public Vector2 position(){
        return new Vector2(x, y);
    }

    public void position(float x, float y){
        this.x = x;
        this.y = y;

        updateHitbox();
    }

    public Vector2 center(){
        return new Vector2(x + radius, y + radius);
    }

    //define sub-entities to this ui card
    protected void createContent(){};

    protected void updateHitbox(){};

}
