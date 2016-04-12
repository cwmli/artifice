package com.underwaterotter.artifice.entities.main;

import android.graphics.RectF;

import com.underwaterotter.artifice.entities.Mob;
import com.underwaterotter.artifice.sprites.MobSprite;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Animation;
import com.underwaterotter.ceto.Camera;
import com.underwaterotter.math.Vector3;

import java.util.ArrayList;

public class Char extends Mob {

    public static final int SIZE_W = 16;
    public static final int SIZE_H = 16;
    
    public static final int SATK_LIM = 3;
    public static final int[] SPCATK_LIM = { 1, 1, 2 }

    public String currentAction;
    public int comboCounter;

    public Char(){
        super();

        name = "player";
        max_hp = 100;
        hp = 100;
        def = 0;
        currentAction = "none";
        comboCounter = 0;
        worldPosition(new Vector3(0,0,0));
        //the player can see everything in the room/screen if in open area
        agroRadius = -1;

        actions = new ArrayList<String>();
        actions.add("bsc_atk");
        actions.add("hvy_atk");
        actions.add("dodge");
        actions.add("use");
        actions.add("climb");

        sprite = new MobSprite(Assets.HERO){ //FIXME EXTEND MOBSPRITE FOR CUSTOM CHARSPRITE CLASS
            @Override
            public void setMob(Mob mob){
                super.setMob(mob);
                width = SIZE_W;
                height = SIZE_H;
            }
       
            @Override
            protected void setAnimations(){
                idle = new Animation(5f, true);
                idle.setFrames(new RectF(0, 0, 0.125f, 0.5f));

                run = new Animation(5f, true);
                run.setFrames(new RectF(0, 0, 0.125f, 0.5f), new RectF(0.125f, 0, 0.25f, 0.5f),  new RectF(0.25f, 0, 0.375f, 0.5f), new RectF(0.125f, 0, 0.25f, 0.5f),
                        new RectF(0, 0, 0.125f, 0.5f),  new RectF(0.375f, 0, 0.5f, 0.5f),  new RectF(0.5f, 0, 0.625f, 0.5f),  new RectF(0.375f, 0, 0.5f, 0.5f));
            }
        };
        sprite.setMob(this);
        add(sprite);
        sprite.idle();
    }

    @Override
    public void update(){
        super.update();

        Camera.main.setFocusPoint(worldPosition.x, worldPosition.y);
        checkCollision();
        parseAction();
    }

    public void checkCollision(){
        Mob[] mobs = getCollided();
        if(mobs == null){
            return;
        }

        float netX = 0;
        float netY = 0;
        //get the net velocity
        for(Mob m : mobs){
            netX += m.sprite.velocity.x ;
            netY += m.sprite.velocity.y;
        }
        //add velocity of itself
        netX += sprite.velocity.x;
        netY += sprite.velocity.y;
        sprite.velocity.set(netX, netY);

        for(Mob m : mobs){
            m.sprite.velocity.set(netX, netY);
        }

    }

    public void parseAction(){

        if(!isAlive()){
            return;
        }

        switch (currentAction){
            case "bsc_atk":
                ;
                basicAttack();
                break;
            case "hvy_atk":
                sprite.attack(1);
                heavyAttack();
                break;
            case "dodge":
                sprite.dodge();
                dodge();
                break;
            case "climb":
                break;
            default:
                if(speed > 0){
                    sprite.run();
                } else {
                    sprite.idle();
                }
                break;
        }
    }

    public void basicAttack(){}

    public void heavyAttack(){}

    public void dodge(){}

    public void use(Object o){}

    public String desc(){
        return "This is you.";
    }
}
