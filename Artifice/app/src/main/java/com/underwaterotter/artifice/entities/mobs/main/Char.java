package com.underwaterotter.artifice.entities.mobs.main;

import android.util.Log;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.entities.mobs.Mob;
import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.artifice.sprites.CharSprite;
import com.underwaterotter.artifice.sprites.MobSprite;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Camera;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class Char extends Mob {

    //ALL ACTIONS
    public static final String BSC_ATK = "bsc_atk";
    public static final String HVY_ATK = "hvy_atk";
    public static final String DODGE = "dodge";
    public static final String USE = "use";
    public static final String CLIMB = "climb";

    private static final int SAFE_ZONE = 4;
    private static final int SHADOW_OFFSET = 8;

    private String currentAction;
    private MobSprite.Orientation currentOrientation;

    public Char() {
        super();

        name = "player";
        max_hp = 100;
        hp = 100;
        def = 0;
        currentAction = "none";
        currentOrientation = MobSprite.Orientation.DOWN;
        //the player can see everything in the room/screen if in open area
        agroRadius = -1;

        actions = new ArrayList<>();
        actions.add("bsc_atk");
        actions.add("hvy_atk");
        actions.add("dodge");
        actions.add("use");
        actions.add("climb");

        availableDirections = new boolean[4];
        Arrays.fill(availableDirections, true);

        shadow = new Image(Assets.SHADOWS);
        shadow.alpha_M(0.75f);

        sprite = new CharSprite(Assets.HERO);
        sprite.setMob(this);
        add(sprite);
        sprite.idle(MobSprite.Orientation.DOWN);

        hitBox = sprite.getBoundingBox();

        add(shadow);

        worldPosition(new Vector3(0,0,0));
    }

    @Override
    public void update() {
        super.update();

        Camera.main.setFocusPoint(worldPosition.x, worldPosition.y);

        worldPosition = sprite.getPos();
        hitBox = sprite.getBoundingBox();
        shadow.setPos(worldPosition.x, worldPosition.y + SHADOW_OFFSET, 0);

        parseAction();
    }

    @Override
    protected void updateBounds() {
        Level level = Artifice.getLevel();
        boolean top = level.isPassable(
                level.worldToCell(
                        (int)(hitBox.left + hitBox.width() / 2),
                        (int)hitBox.bottom - SAFE_ZONE));

        boolean left = level.isPassable(
                level.worldToCell(
                        (int)hitBox.left + SAFE_ZONE,
                        (int)hitBox.bottom - SAFE_ZONE));

        boolean bottom = level.isPassable(
                level.worldToCell(
                        (int)(hitBox.left + hitBox.width() / 2),
                        (int)hitBox.bottom));

        boolean right = level.isPassable(
                level.worldToCell(
                        (int)hitBox.right - SAFE_ZONE,
                        (int)hitBox.bottom - SAFE_ZONE));
        Arrays.fill(availableDirections, true);

        if(sprite.getVelocity().x < 0 && !left) {
            availableDirections[0] = false;
        }
        if(sprite.getVelocity().x > 0 && !right) {
            availableDirections[2] = false;
        }
        if(sprite.getVelocity().y > 0 && !bottom) {
            availableDirections[3] = false;
        }
        if(sprite.getVelocity().y < 0 && !top) {
            availableDirections[1] = false;
        }
    }

    @Override
    protected void updateMotion() {
        float vX = speed * (float) Math.cos(Math.toRadians(sprite.getAngle()));
        float vY = speed * (float) Math.sin(Math.toRadians(sprite.getAngle()));

        boolean movingLeft = vX < 0;
        boolean movingDown = vY > 0;

        if (!availableDirections[0] && movingLeft || !availableDirections[2] && !movingLeft) {
            vX = 0;
        }

        if (!availableDirections[1] && !movingDown || !availableDirections[3] && movingDown) {
            vY = 0;
        }

        if (Math.abs(vX) > Math.abs(vY)) {
            currentOrientation = MobSprite.Orientation.SIDE;
        } else if (Math.abs(vX) < Math.abs(vY)) {
            currentOrientation = MobSprite.Orientation.DOWN;
        }

        sprite.setVelocity(vX, vY);

        Vector3 lastPos = sprite.getPos();
        sprite.setPos(lastPos.x + vX, lastPos.y + vY, 0);
    }

    public String getCurrentAction(){
        return currentAction;
    }

    public void setCurrentAction(String action){
        currentAction = action;
    }

    public void parseAction() {

        if(!isAlive()) {
            return;
        }

        switch (currentAction) {
            case "bsc_atk":
                sprite.attack(MobSprite.Orientation.DOWN); //placeholder
                basicAttack();
                break;
            case "hvy_atk":
                sprite.attack(MobSprite.Orientation.DOWN); //placeholder
                heavyAttack();
                break;
            case "dodge":
                sprite.dodge();
                dodge();
                break;
            case "climb":
                break;
            default:
                if(speed > 0) {
                    sprite.run(currentOrientation);
                } else {
                    sprite.idle(currentOrientation);
                }
                break;
        }
    }

    public void basicAttack() {}

    public void heavyAttack() {}

    public void dodge() {}

    public void use(Object o) {}

    public void damage(int damage, Item source) {}

    public String desc(){
        return "This is you.";
    }
}
