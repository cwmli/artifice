package com.underwaterotter.ceto.particle;

import android.graphics.Bitmap;

import com.underwaterotter.ceto.Game;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Rand;
import com.underwaterotter.math.Vector3;

public class Particle extends Image {

    public boolean dissipate;

    private float timer;
    private float lifespan;

    private float sizeUpper = 1;
    private float sizeLower = 1;

    public Particle(){
        super();

        lifespan = 0;

        dissipate = true;
    }

    public Particle(float x, float y, Bitmap bitmap, int lifespan, int angle){
        super(new Vector3(x, y, 0), bitmap);

        this.lifespan = lifespan;
        dissipate = true;
    }

    public void setPulsateBounds(int low, int high){
        this.sizeLower = low;
        this.sizeUpper = high;
    }

    //will use the already defined lifespan + velocity;
    public void reset(int x, int y, int width, int height){

        timer = 0;

        setPos(x, y, 0);
        this.width = width;
        this.height = height;

        revive();
    }

    public void pulsateSize(){
        scale(Rand.range(sizeLower, sizeUpper));
    }

    @Override
    public void update(){
        super.update();

        timer += Game.elapsedTime;

        pulsateSize();

        if(dissipate){
            int defaultSize = 1;

            float s = defaultSize * (1 / timer);

            if(s > 0 && s < 1){
                scale(s);
            }
        }

        if(timer > lifespan){
            kill();
        }
    }
}
