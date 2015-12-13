package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.Group;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;

public class HotCircle extends Group {

    protected float x;
    protected float y;

    protected float radius;

    public HotCircle() {
        this(0, 0, 0);
    }

    public HotCircle(float x, float y, float radius){
        super();

        this.x = x;
        this.y = y;
        this.radius = radius;

        createContent();
    }

    public void resize(float r){
        radius = r;

        updateHitbox();
    }

    public void radius(float r){
        radius = r;
    }

    public float radius(){
        return radius;
    }

    public Vector3 position(){
        return new Vector3(x, y, 0);
    }

    public void position(float x, float y){
        this.x = x;
        this.y = y;

        updateHitbox();
    }

    public Vector3 center(){
        return new Vector3(x + radius, y + radius, 0);
    }

    //define sub-entities to this ui card
    protected void createContent(){};

    protected void updateHitbox(){};

}
