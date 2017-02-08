package com.underwaterotter.artifice.entities.mobs;

import android.graphics.RectF;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.entities.Entity;
import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.artifice.sprites.ItemSprite;
import com.underwaterotter.artifice.sprites.MobSprite;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public abstract class Mob extends Entity implements Storable {

    private static final String HP = "hp";
    private static final String STATUS = "status";
    private static final String BUFFS = "buffs";
    private static final String EQUIPS = "equips";

    //the "z" length of the mob
    private static final int MOB_THICKNESS = 5;

    protected MobSprite sprite;
    protected RectF hitBox;
    protected Image shadow;

    protected ItemSprite[] equipped; //equal in length to attachPositions, corresponds to index
    protected Vector3[] attachPositions;

    protected String name = "generic_mob";

    protected int max_hp;
    protected int hp;
    protected int def;

    protected float feather; //Max value of 100; higher values mean lighter weight
    protected float speed;
    protected float hindrance;

    protected ArrayList<Integer> visibleCells;
    protected int agroRadius;
    //view radius is calculated by cell blocks

    protected HashSet<Mob.STATUS> status;
    protected HashSet<BUFF> buffs;

    protected ArrayList<String> actions;

    protected boolean[] availableDirections;

    //for tracking
    private UUID mob_id;
    private enum BUFF { DEFENSE, MAX_HP, ATTACK, SPEED}
    private enum STATUS { BLEEDING, FIRE, FROZEN, SHOCK, WET }

    public Mob(){
        super();

        status = new HashSet<Mob.STATUS>();
        buffs = new HashSet<BUFF>();

        visibleCells = new ArrayList<Integer>();
    }

    @Override
    public void destroy(){
        super.destroy();
        sprite.destroy();
        sprite = null;
    }

    @Override
    public void saveToBlock(Block block){
        super.saveToBlock(block);

        block.put(HP, hp);
        // FIXME: 8/26/2015
        block.put(STATUS, status);
        block.put(BUFFS, buffs);
    }

    @Override
    public void loadFromBlock(Block block){
        super.loadFromBlock(block);

        hp = block.getInt(HP);

        // FIXME: 8/26/2015
        status = new HashSet<Mob.STATUS>(
                //Arrays.asList(
                    //    (STATUS[])block.getArrayOf(STATUS).toArray())
        );
        buffs = new HashSet<BUFF>(
                //Arrays.asList(
                  //      (BUFF[])block.getArrayOf(BUFFS).toArray())
        );
    }

    public UUID getMobID(){
        return mob_id;
    }

    public UUID setMobID(UUID id) {
        mob_id = id;
        return id;
    }

    public void setSprite(MobSprite spr){
        sprite = spr;
    }

    protected Mob[] getCollided(){
        //find closest mobs to check
        Mob[] mobs = currentLevel.getMobMapper().findByCell(cellPosition());
        ArrayList<Mob> collided = new ArrayList<Mob>();

//        if(mobs != null) {
//            for(Mob mob : mobs) {
//                if (RectF.intersects(mob.sprite.boundingBox, this.sprite.boundingBox) && mob.worldPosition().y - worldPosition().y <= MOB_THICKNESS) {
//                    collided.add(mob);
//                    mob.hindrance = 1 - (feather * 0.01f);
//                }
//            }
//        }
        Mob[] collidedMobs = new Mob[collided.size()];
        collided.toArray(collidedMobs);

        return collidedMobs;
    }

    public RectF getHitBox(){
        return hitBox;
    }

    public Vector3[] getAttachPositions(){
        return attachPositions;
    }

    public ItemSprite[] getEquipped(){
        return equipped;
    }

    public Vector2 getVelocity(){
        return sprite.getVelocity();
    }

    public void setVelocity(Vector2 vec2){
        setVelocityX(vec2.x, vec2.y);
    }

    public void setVelocityX(float x, float y){
        sprite.setVelocity(x, y);
    }

    public void setAngle(float angle){
        sprite.setAngle(angle);
    }

    public void setSpeed(float s){
        speed = s;
        sprite.setSpeed(s);
    }

    @Override
    public void worldPosition(Vector3 pos){
        super.worldPosition(pos);
        sprite.setPos(pos);
    }

    public Vector3 center(){
        return new Vector3(worldPosition.x + sprite.width() / 2,
                worldPosition.y + sprite.height() / 2, worldPosition.z);
    }

    public boolean isAlive(){
        return hp > 0;
    }

    @Override
    public void update(){
        super.update();

        updateStatusEffects();
        updateBounds();
        updateMotion();
    }

    protected abstract void updateBounds();

    protected abstract void updateMotion();

    public void updateStatusEffects(){

        for(Mob.STATUS s : status){
            switch (s){
                case BLEEDING:
                    sprite.addEffect(MobSprite.Effect.BLEEDING);
                    break;
                case FIRE:
                    sprite.addEffect(MobSprite.Effect.FIRE);
                    break;
                case FROZEN:
                    sprite.addEffect(MobSprite.Effect.FROZEN);
                    break;
                case SHOCK:
                    sprite.addEffect(MobSprite.Effect.SHOCK);
                    break;
                case WET:
                    sprite.addEffect(MobSprite.Effect.WET);
                    break;
            }
        }
    }

    protected void updateAgro() {
        visibleCells.clear();
    }

    public void clearEquipped(){
        Arrays.fill(equipped, null);
    }

    public void addStatus(Mob.STATUS s){
        status.add(s);
        updateStatusEffects();
    }

    public void removeStatus(Mob.STATUS s){
        status.remove(s);
        updateStatusEffects();
    }

    public abstract void damage(int damage, Item source);

    public abstract String desc();
}
