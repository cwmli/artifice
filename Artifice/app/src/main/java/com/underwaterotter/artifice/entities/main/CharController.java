package com.underwaterotter.artifice.entities.main;

import com.underwaterotter.artifice.scenes.GameScene;

public class CharController {

    public static void setAngle(float angle){
        GameScene.scene.getPlayer().setAngle(angle);
    }

    public static void setSpeed(float speed){
        GameScene.scene.getPlayer().setSpeed(speed);
    }

    public static void setAction(String action){
        GameScene.scene.getPlayer().setCurrentAction(action);
    }
}
