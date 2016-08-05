package com.underwaterotter.artifice.entities.main;

import com.underwaterotter.artifice.scenes.GameScene;

public class CharController {

    public static void setVelocity(float angle){
        GameScene.scene.getPlayer().sprite.angle = angle;
    }

    public static void setSpeed(float speed){
        GameScene.scene.getPlayer().speed = speed;
    }

    public static void setAction(String action){
        GameScene.scene.getPlayer().setCurrentAction(action);;
    }
}
