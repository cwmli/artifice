package com.underwaterotter.artifice.sprites;

import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Animation;
import com.underwaterotter.artifice.entities.Mob;

public abstract class MobSprite extends Sprite {

    private static final int SAFE_ZONE = 4;

    protected Animation[] attack;
    protected Animation run;
    protected Animation dodge;
    protected Animation die;
    protected Animation idle;

    private Mob mob;
    private Level level;

    public enum Effect{ BLEEDING, FIRE, FROZEN, SHOCK, WET }

    public MobSprite(Object id){
        super(id);

        setAnimations();
    }

    public void updateBounds(){
        boolean top = level.isPassable(
                level.getTilemap().worldToCell(
                        (int)(boundingBox.left + boundingBox.width() / 2),
                        (int)boundingBox.bottom - SAFE_ZONE));

        boolean left = level.isPassable(
                level.getTilemap().worldToCell(
                        (int)(boundingBox.left + SAFE_ZONE),
                        (int)boundingBox.bottom - SAFE_ZONE));

        boolean bottom = level.isPassable(
                level.getTilemap().worldToCell(
                        (int)(boundingBox.left + boundingBox.width() / 2),
                        (int)boundingBox.bottom));

        boolean right = level.isPassable(
                level.getTilemap().worldToCell(
                        (int)(boundingBox.right - SAFE_ZONE),
                        (int)boundingBox.bottom - SAFE_ZONE));
    }

    public void setMob(Mob mob){
        this.mob = mob;
        level = mob.level();
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

    protected abstract void setAnimations();
}
