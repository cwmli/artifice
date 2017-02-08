package com.underwaterotter.artifice.sprites;

import com.underwaterotter.ceto.Animation;
import com.underwaterotter.artifice.entities.mobs.Mob;
import com.underwaterotter.math.Vector2;

public abstract class MobSprite extends Sprite {

    public static enum Orientation {SIDE, DOWN, UP}

    Animation[] attack;
    Animation[] run;
    Animation dodge;
    Animation die;
    Animation[] idle;

    private Mob mob;

    public enum Effect{ BLEEDING, FIRE, FROZEN, SHOCK, WET }

    public MobSprite(Object id){
        super(id);

        velocity = new Vector2(0, 0);

        run = new Animation[3];
        idle = new Animation[3];

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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float a) {
        angle = a;
    }

    public void idle(Orientation type){
        if (type == Orientation.SIDE) {
            play(idle[0], true);
        } else if (type == Orientation.DOWN) {
            play(idle[1], true);
        } else {
            play(idle[2], true);
        }
    }

    public void run(Orientation type){
        if (type == Orientation.SIDE) {
            play(run[0], true);
        } else if (type == Orientation.DOWN) {
            play(run[1], true);
        } else {
            play(run[2], true);
        }
    }

    public void dodge(){
        play(dodge, true);
    }

    public void attack(Orientation type){
        if (type == Orientation.SIDE) {
            play(attack[0], true);
        } else if (type == Orientation.DOWN) {
            play(attack[1], true);
        } else {
            play(attack[2], true);
        }
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
