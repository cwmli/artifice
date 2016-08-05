package com.underwaterotter.artifice.entities.main;

import android.graphics.RectF;

import com.underwaterotter.artifice.entities.Mob;
import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.sprites.MobSprite;
import com.underwaterotter.artifice.world.AnimatedTilemap;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Animation;
import com.underwaterotter.ceto.Camera;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class Char extends Mob {

    private static final int SIZE_W = 16;
    private static final int SIZE_H = 16;
    private static final int SHADOW_OFFSET = 8;

    private String currentAction;
    private boolean[] availableDirections;
    //{left, top, right, bottom}

    public Char(){
        super();

        name = "player";
        max_hp = 100;
        hp = 100;
        def = 0;
        currentAction = "none";
        worldPosition(new Vector3(0,0,0));
        //the player can see everything in the room/screen if in open area
        agroRadius = -1;

        actions = new ArrayList<String>();
        actions.add("bsc_atk");
        actions.add("hvy_atk");
        actions.add("dodge");
        actions.add("use");
        actions.add("climb");

        availableDirections = new boolean[4];
        Arrays.fill(availableDirections, true);

        shadow = new Image(Assets.SHADOWS);

        sprite = new MobSprite(Assets.HERO){

            @Override
            public void updateBounds(){
//                if(top && left && bottom && right){
//                    Arrays.fill(availableDirections, true);
//                }
//
//                if(sprite.velocity.x < 0 && !left) {
//                    availableDirections[0] = false;
//                }
//                if(sprite.velocity.x > 0 && !right) {
//                    availableDirections[2] = false;
//                }
//                if(sprite.velocity.y > 0 && !bottom) {
//                    availableDirections[3] = false;
//                }
//                if(sprite.velocity.y < 0 && !top){
//                    availableDirections[1] = false;
//                }
            }

            @Override
            public void updateMotion(){
                boolean movingLeft = Math.cos(Math.toRadians(angle)) * speed < 0;
                boolean movingDown = Math.sin(Math.toRadians(angle)) * speed > 0;

                float vX = speed * (float)Math.cos(Math.toRadians(angle)); //a * Math.PI / 180
                if(!availableDirections[0] && movingLeft || !availableDirections[2] && !movingLeft){
                    vX = 0;
                }

                float vY = speed * (float)Math.sin(Math.toRadians(angle));
                if(!availableDirections[1] && !movingDown || !availableDirections[3] && movingDown) {
                    vY = 0;
                }

                velocity.set(vX, vY);

                pos.x += velocity.x;
                pos.y += velocity.y;
            }

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

        add(shadow);
    }

    @Override
    public void update(){
        super.update();

        Camera.main.setFocusPoint(worldPosition.x, worldPosition.y);

        sprite.speed = speed * hindrance;
        worldPosition = sprite.position();
        shadow.position(worldPosition.x, worldPosition.y + SHADOW_OFFSET, 0);

        checkMobCollision();
        parseAction();
    }

    public String getCurrentAction(){
        return currentAction;
    }

    public void setCurrentAction(String action){
        currentAction = action;
    }

    public void checkMobCollision(){
        Mob[] mobs = getCollided();

        float netX = 0;
        float netY = 0;
        //get the net velocity
        for(Mob m : mobs){
            netX += m.sprite.velocity.x ;
            netY += m.sprite.velocity.y;
        }
        //addMob velocity of itself
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
                sprite.attack(0);
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
