package com.underwaterotter.artifice.sprites;

import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Animation;
import com.underwaterotter.artifice.entities.Mob;
import com.underwaterotter.math.Vector2;

public abstract class MobSprite extends Sprite {

    protected Animation[] attack;
    protected Animation run;
    protected Animation dodge;
    protected Animation die;
    protected Animation idle;

    private Mob mob;

    public enum Effect{ BLEEDING, FIRE, FROZEN, SHOCK, WET }

    public MobSprite(Object id){
        super(id);

        setAnimations();
    }

    public void setMob(Mob mob){
        this.mob = mob;

        mob.setSprite(this);

        setPos(mob.worldPosition());
        mob.updateStatusEffects();
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2 vel){
        setVelocity(vel.x, vel.y);
    }

    public void setVelocity(float x, float y){
        velocity = new Vector2(x, y);
    }

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float spd){
        speed = spd;
    }

    public void idle(){
        play(idle, true);
    }

    public void run(){
        play(run, true);
    }

    public void dodge(){
        play(dodge, true);
    }

    public void attack(int type){
        play(attack[type], true);
    }

    public void die(){
        play(die, true);
    }

    public void addEffect(Effect e){

        switch (e){
            case BLEEDING:
                break;
            case FIRE:
                break;
            case FROZEN:
                break;
            case SHOCK:
                break;
            case WET:
                break;
        }
    }

    public void removeEffect(Effect e){

        switch (e){
            case BLEEDING:
                break;
            case FIRE:
                break;
            case FROZEN:
                break;
            case SHOCK:
                break;
            case WET:
                break;
        }
    }

    protected abstract void setAnimations();
}
