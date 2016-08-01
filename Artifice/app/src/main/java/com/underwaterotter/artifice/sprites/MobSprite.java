package com.underwaterotter.artifice.sprites;

import com.underwaterotter.ceto.Animation;
import com.underwaterotter.artifice.entities.Mob;

public class MobSprite extends Sprite {

    public Animation[] attack;
    public Animation run;
    public Animation dodge;
    public Animation die;
    public Animation idle;

    public Mob mob;

    public enum Effect{ BLEEDING, FIRE, FROZEN, SHOCK, WET }

    public MobSprite(Object id){
        super(id);

        setAnimations();
    }

    public void setMob(Mob mob){
        this.mob = mob;
        mob.sprite = this;

        position(mob.worldPosition());
        mob.updateStatusEffects();
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

    protected void setAnimations(){}
}
