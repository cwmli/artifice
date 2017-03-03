package com.underwaterotter.artifice.entities.mobs.main;

import android.util.Log;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.entities.items.ItemType;
import com.underwaterotter.artifice.entities.items.Pouch;
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
    private static final float CHAIN_DURATION = 0.75f;

    private static final int CHAIN_LENGTH = 5;

    // The maximum equip length is 8
    // [0] = HEAD   [1] = BODY  [2] = LEGS  [3] = BOOTS
    // [4] = GLOVES [5] = LEFT  [6] = RIGHT [7] = BACK
    private Item[] equipped = new Item[EquipSlots.SIZE];
    private Vector3[] attachPos = new Vector3[EquipSlots.SIZE];
    // Attach position offsets

    private Pouch inventory;

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
        startChainPos = position;

        attachPos[0] = new Vector3(position.x + 5, position.y + 7, position.z);
        attachPos[1] = new Vector3(position.x + 4, position.y + 14, position.z);
        attachPos[2] = new Vector3(position.x + 6, position.y + 24, position.z);

        hitBox = sprite.getBoundingBox();
    }

    @Override
    public void update() {
        super.update();
        updateAttackChain();

        Camera.main.setFocusPoint(position.x, position.y);

        position = sprite.getPos();
        hitBox = sprite.getBoundingBox();
        shadow.setPos(position.x, position.y + SHADOW_OFFSET, 1);

        updateAnimation();
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
                level.getElevation(topCell) == position.z;

        int leftCell = level.worldToCell((int)hitBox.left, (int)hitBox.bottom);
        boolean left = level.isPassable(leftCell) &&
                level.getElevation(leftCell) == position.z;

        int bottomCell = level.worldToCell((int)hitBox.centerX(),
                (int)hitBox.bottom + SAFE_ZONE);
        boolean bottom = level.isPassable(bottomCell) &&
                level.getElevation(bottomCell) == position.z;

        int rightCell = level.worldToCell((int)hitBox.right, (int)hitBox.bottom);
        boolean right = level.isPassable(rightCell) &&
                level.getElevation(rightCell) == position.z;

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

    private void updateAnimation() {

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

    public String getCurrentAction(){
        return currentAction;
    }

    void setCurrentAction(String action){
        currentAction = action;
    }

    public Item[] getEquipped(){
        return equipped;
    }

    public Vector3[] getAttachPos() {
        return attachPos;
    }

    @Override
    public void setSpeed(float s) {
        if (inChain) {
            s = 1.0f;
            if((chainCounter == 0 || chainCounter == 1 || chainCounter == 6) &&
                    startChainPos.distance(sprite.getPos()) < TRAVEL_DIST_C01) {
                s *= 0.5f;
            } else if ((chainCounter == 2 || chainCounter == 5) &&
                    startChainPos.distance(sprite.getPos()) < TRAVEL_DIST_C2) {
                s *= 0.75f;
            } else if ((chainCounter == 3 || chainCounter == 7) &&
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

    void basicAttack() {
        lastAngle = sprite.getAngle();

        if (!inDelay && chainCounter < CHAIN_LENGTH &&
                sprite.attack(currentOrientation, chainCounter)) {//placeholder

            inChain = true;
            setSpeed(0);
            startChainPos = sprite.getPos();


            // Refresh the chain timer
            chainTimer = 0;

            chainCounter++;
        }

    }

    void heavyAttack() {
        lastAngle = sprite.getAngle();

        Log.v("TRY", "Attempt to continue to combo: " + (chainCounter + 3));
        if (chainCounter >= 2 && sprite.attack(currentOrientation, chainCounter + 3)) {

            inChain = true;
            setSpeed(0);
            startChainPos = sprite.getPos();

            // End the chain
            chainCounter = CHAIN_LENGTH;
        }
    }

    void dodge() {}

    public void use(Object o) {}

    public boolean equip(Item item, int typePos) {

        int itemtypeFlag = ItemType.flags[item.itemID()];
        int typeposFlag = EquipSlots.flags[typePos];

        if ((itemtypeFlag & typeposFlag) != 0) {
            if ((typePos & ItemType.TWO_H) != 0) {
                unequip(equipped[EquipSlots.LEFT]);
                unequip(equipped[EquipSlots.RIGHT]);

                equipped[EquipSlots.LEFT] = item;
                equipped[EquipSlots.RIGHT] = item;

                item.attach(this, EquipSlots.RIGHT);
            } else {
                unequip(equipped[typePos]);

                equipped[typePos] = item;
                item.attach(this, typePos);
            }

            return true;
        } else {
            return false;
        }
    }

    public void unequip(Item item) {

        if (item == null) {
            return;
        }

        for (int i = 0; i < 8; i++) {
            if (equipped[i] == item) {
                equipped[i] = null;
                item.detach();
                break;
            }
        }
    }

    public void damage(int damage, Item source) {}

    public String desc(){
        return "This is you.";
    }
}
