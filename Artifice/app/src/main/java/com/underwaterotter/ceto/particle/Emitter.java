package com.underwaterotter.ceto.particle;

import com.underwaterotter.ceto.Game;
import com.underwaterotter.ceto.Group;
import com.underwaterotter.ceto.Overlay;
import com.underwaterotter.math.Rand;
import com.underwaterotter.math.Vector2;

public abstract class Emitter extends Group {

    public float x;
    public float y;

    public boolean active;

    //emit from anywhere in source bounds
    protected Overlay source;

    protected float rate;
    protected float timer;

    protected float targetLifetime;
    protected float targetSpeed;

    protected int limit;

    protected float variablePercent = 0.15f; //+/- 15% for all target values

    public Emitter(Overlay source, float rate){
        super();
        this.source = source;
        this.rate = rate;
    }

    public void setTargets(float lifetime, float speed){
        targetLifetime = lifetime;
        targetSpeed = speed;
    }

    public float rate(){
        return rate;
    }

    public void rate(float rate){
        this.rate = rate;
    }

    @Override
    public void update(){

        if(active) {
            int index = 0;

            timer += Game.elapsedTime;

            while(timer > rate){
                timer -= rate;

                if(index < limit){
                    emit(index);
                    index++;
                } else {
                    active = false;
                    break;
                }
            }
        }
        else if(livingEntities() == 0){
            kill();
        }

        super.update();
    }

    public Vector2 position(){
        return new Vector2(x, y);
    }

    public void position(Vector2 pos){
        x = (int)pos.x;
        y = (int)pos.y;
    }

    public void position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void position(Overlay src){

        source = src;

        x = src.getPos().x;
        y = src.getPos().y;
    }

    public void set(int rate, int lifetime, int limit, int speed){
        this.rate = rate;
        targetLifetime = lifetime;
        targetSpeed = speed;

        this.limit = limit;
    }

    public void emit(int index){

        if(source != null){
            createParticle(index,
                           Rand.range(0, source.getWidth()),
                           Rand.range(0, source.getHeight()));
        } else {
            createParticle(index, x, y);
        }
    }

    public abstract void createParticle(int index, float x, float y);
}
