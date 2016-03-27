package com.underwaterotter.artifice.entities.main;

import com.underwaterotter.artifice.scenes.GameScene;

public class CharController {

    public static void setVelocity(float angle){
        GameScene.scene.player.sprite.angle = angle;
    }

    public static void setSpeed(float speed){
        GameScene.scene.player.speed = speed;
    }

    public static void setAction(String action){
        GameScene.scene.player.currentAction = action;
    }
}
