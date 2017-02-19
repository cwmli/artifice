package com.underwaterotter.artifice.entities.mobs.main;

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

    public static void attack() {
        setAction(Char.BSC_ATK);
        GameScene.scene.getPlayer().basicAttack();
    }

    public static void spcAttack() {
        setAction(Char.HVY_ATK);
        GameScene.scene.getPlayer().heavyAttack();
    }
}
