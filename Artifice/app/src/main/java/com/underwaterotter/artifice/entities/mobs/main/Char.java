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
import com.underwaterotter.ceto.Game;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class Char extends Mob {

    //ALL ACTIONS
    public static final String USE = "use";
    public static final String CLIMB = "climb";

    private static final int SAFE_ZONE = 3;
    private static final int SHADOW_OFFSET = 24;

    // Chain travel distances, based on current chain counter
    private static final int TRAVEL_DIST_C01 = 3;
    private static final int TRAVEL_DIST_C2 = 5;
    private static final int TRAVEL_DIST_C3 = 10;
    private static final int TRAVEL_DIST_C4 = 7;

    private static final float DELAY_DURATION = 1.0f;
    private static final float CHAIN_DURATION = 0.5f;

    private static final int CHAIN_LENGTH = 5;

    private MobSprite.Orientation currentOrientation;

    private float lastAngle;

    private boolean inChain;
    private float chainTimer;
    private int chainCounter;

    private Vector3 startChainPos;

    private boolean inDelay;
    private float delayTimer;

    public Char() {
        super();

        name = "player";
        max_hp = 100;
        hp = 100;
        def = 0;
        currentAction = IDLE;
        currentOrientation = MobSprite.Orientation.DOWN;
        //the player can see everything in the room/screen if in open area
        agroRadius = -1;
        inChain = false;
        inDelay = false;

        actions = new ArrayList<>();
        actions.add(BSC_ATK);
        actions.add(HVY_ATK);
        actions.add(DODGE);
        actions.add(USE);
        actions.add(CLIMB);

        availableDirections = new boolean[4];
        Arrays.fill(availableDirections, true);

        shadow = new Image(Assets.SHADOWS);
        shadow.alpha_M(0.9f);
        add(shadow);

        sprite = new CharSprite(Assets.HERO);
        sprite.setMob(this);
        add(sprite);

        sprite.idle(MobSprite.Orientation.DOWN);

        sprite.setPos(0, 0, 1);
        worldPosition(new Vector3(0,0,1));
        startChainPos = worldPosition;

        hitBox = sprite.getBoundingBox();
    }

    @Override
    public void update() {
        super.update();
        updateAttackChain();

        Camera.main.setFocusPoint(worldPosition.x, worldPosition.y);

        worldPosition = sprite.getPos();
        hitBox = sprite.getBoundingBox();
        shadow.setPos(worldPosition.x, worldPosition.y + SHADOW_OFFSET, 1);

        parseAction();
    }

    private void updateAttackChain() {

        // Update movement in attacks
        if (inChain) {
            setSpeed(0);
        }

        // Track chain delay if valid
        if (inDelay) {
            delayTimer += Game.elapsedTime;

            if (delayTimer > DELAY_DURATION) {
                inDelay = false;
                delayTimer = 0;
            }
        }

        // Track chain continuity if valid
        if (inChain && !sprite.isAnimationPlaying()) {
            chainTimer += Game.elapsedTime;

            if (chainTimer > CHAIN_DURATION) {
                // Reset counters because the chain failed
                inChain = false;
                chainCounter = 0;
                chainTimer = 0;

                currentAction = IDLE;
                setSpeed(0f);
            }

            if (chainCounter == CHAIN_LENGTH) {
                inChain = false;

                // Delay until next chain is available and reset chain count
                inDelay = true;
                chainCounter = 0;

                setSpeed(0);
            }
        }
    }

    @Override
    protected void updateBounds() {
        Level level = Artifice.getLevel();

        int topCell = level.worldToCell((int)hitBox.centerX(), (int)hitBox.bottom - SAFE_ZONE);
        boolean top = level.isPassable(topCell) &&
                level.getElevation(topCell) == worldPosition.z;

        int leftCell = level.worldToCell((int)hitBox.left, (int)hitBox.bottom);
        boolean left = level.isPassable(leftCell) &&
                level.getElevation(leftCell) == worldPosition.z;

        int bottomCell = level.worldToCell((int)hitBox.centerX(), (int)hitBox.bottom + SAFE_ZONE);
        boolean bottom = level.isPassable(bottomCell) &&
                level.getElevation(bottomCell) == worldPosition.z;

        int rightCell = level.worldToCell((int)hitBox.right, (int)hitBox.bottom);
        boolean right = level.isPassable(rightCell) &&
                level.getElevation(rightCell) == worldPosition.z;

        Arrays.fill(availableDirections, true);

        if (!left) {
            availableDirections[0] = false;
        }

        if (!right) {
            availableDirections[2] = false;
        }

        if (!bottom) {
            availableDirections[3] = false;
        }

        if (!top) {
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
            vX = 0f;
        }

        if (!availableDirections[1] && !movingDown || !availableDirections[3] && movingDown) {
            vY = 0f;
        }

        if (Math.abs(vX) > Math.abs(vY)) {
            currentOrientation = MobSprite.Orientation.SIDE;
        } else if (Math.abs(vX) < Math.abs(vY)) {
            if (vY > 0) {
                currentOrientation = MobSprite.Orientation.DOWN;
            } else {
                currentOrientation = MobSprite.Orientation.UP;
            }
        }

        sprite.setVelocity(vX, vY);

        Vector3 lastPos = sprite.getPos();
        sprite.setPos(lastPos.x + vX, lastPos.y + vY, 1);
    }

    public String getCurrentAction(){
        return currentAction;
    }

    public void setCurrentAction(String action){
        currentAction = action;
    }

    @Override
    public void setSpeed(float s) {
        if (inChain) {
            s = 1.0f;
            if((chainCounter == 0 || chainCounter == 1) &&
                    startChainPos.distance(sprite.getPos()) < TRAVEL_DIST_C01) {
                s *= 0.5f;
            } else if (chainCounter == 2 &&
                    startChainPos.distance(sprite.getPos()) < TRAVEL_DIST_C2) {
                s *= 0.75f;
            } else if (chainCounter == 3 &&
                    startChainPos.distance(sprite.getPos()) < TRAVEL_DIST_C3){
                s *= 1.0f;
            } else if (chainCounter == 4 &&
                    startChainPos.distance(sprite.getPos()) < TRAVEL_DIST_C4){
                s = 0.85f;
            } else {
                s = 0;
            }

            sprite.setAngle(lastAngle);
        }

        speed = s;
        sprite.setSpeed(s);
    }

    public void parseAction() {

        if(!isAlive()) {
            return;
        }

        if (currentAction.equals(DODGE)) {
            sprite.dodge();
            dodge();
        } else if (currentAction.equals(CLIMB)) {
            //do climbing stuff
        } else if (!inChain) {
            if (speed > 0) {
                sprite.run(currentOrientation);
            } else {
                sprite.idle(currentOrientation);
            }
        }
    }

    public void basicAttack() {
        lastAngle = sprite.getAngle();

        if (!inDelay && chainCounter < CHAIN_LENGTH &&
                sprite.attack(MobSprite.Orientation.SIDE, chainCounter)) {//placeholder

            inChain = true;
            setSpeed(0);
            startChainPos = sprite.getPos();


            // Refresh the chain timer
            chainTimer = 0;

            chainCounter++;
        }

    }

    public void heavyAttack() {
        lastAngle = sprite.getAngle();

        Log.v("TRY", "Attempt to continue to combo: " + (chainCounter + 3));
        if (chainCounter >= 2 && sprite.attack(MobSprite.Orientation.SIDE, chainCounter + 3)) {

            inChain = true;
            setSpeed(0);
            startChainPos = sprite.getPos();

            // End the chain
            chainCounter = CHAIN_LENGTH;
        }
    }

    public void dodge() {}

    public void use(Object o) {}

    public void damage(int damage, Item source) {}

    public String desc(){
        return "This is you.";
    }
}
