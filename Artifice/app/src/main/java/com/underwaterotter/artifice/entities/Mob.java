package com.underwaterotter.artifice.entities;

import android.graphics.RectF;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.artifice.sprites.ItemSprite;
import com.underwaterotter.artifice.sprites.MobSprite;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public abstract class Mob extends Entity implements Storable {

    public static final String HP = "hp";
    public static final String STATUS = "status";
    public static final String BUFFS = "buffs";
    public static final String EQUIPS = "equips";

    //the "z" length of the mob
    public static final int MOB_THICKNESS = 5;

    //for tracking
    private UUID mob_id;

    public MobSprite sprite;
    public Image shadow;

    public ItemSprite[] equipped; //equal in length to attachPositions, corresponds to index
    public Vector3[] attachPositions;

    public String name = "generic_mob";

    public int max_hp;
    public int hp;
    public int def;

    public float feather; //Max value of 100; higher values mean lighter weight
    public float speed;
    public float hindrance;

    public ArrayList<Integer> visibleCells;
    public int agroRadius;
    //view radius is calculated by cell blocks

    protected HashSet<Status> status;
    protected HashSet<Buff> buffs;

    protected ArrayList<String> actions;

    enum Buff{ DEFENSE, MAX_HP, ATTACK, SPEED}
    //convert this into a full enum class
    enum Status{ BLEEDING, FIRE, FROZEN, SHOCK, WET }

    public Mob(){
        super();

        status = new HashSet<Status>();
        buffs = new HashSet<Buff>();

        visibleCells = new ArrayList<Integer>();
    }

    @Override
    public void destroy(){
        super.destroy();

        hp = 0;
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
        status = new HashSet<Status>(
                //Arrays.asList(
                    //    (Status[])block.getArrayOf(STATUS).toArray())
        );
        buffs = new HashSet<Buff>(
                //Arrays.asList(
                  //      (Buff[])block.getArrayOf(BUFFS).toArray())
        );
    }

    @Override
    public void update(){
        super.update();

        updateStatusEffects();
    }

    public void updateStatusEffects(){

        for(Status s : status){
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

    protected void updateAgro(){
        visibleCells.clear();
        for(int i = 0; i < Artifice.getLevel().SURROUNDING_CELLS.length; i++){
            visibleCells.add(Artifice.getLevel().SURROUNDING_CELLS[i] * 2);
        }
    }

    public UUID getMobID(){
        return mob_id;
    }

    protected Mob[] getCollided(){
        //find closest mobs to check
        Mob[] mobs = currentLevel.mobMapper.findByCell(cellPosition());
        ArrayList<Mob> collided = new ArrayList<Mob>();

        if(mobs != null) {
            for(Mob mob : mobs) {
                if (RectF.intersects(mob.sprite.boundingBox, this.sprite.boundingBox) && mob.worldPosition().y - worldPosition().y <= MOB_THICKNESS) {
                    collided.add(mob);
                    mob.hindrance = 1 - (feather * 0.01f);
                }
            }
        }
        Mob[] collidedMobs = new Mob[collided.size()];
        collided.toArray(collidedMobs);

        return collidedMobs;
    }

    public float getSpeed(){
        return speed * hindrance;
    }


    public UUID setMobID(UUID id){
        return mob_id = id;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public Vector3 center(){
        return new Vector3(worldPosition.x + sprite.width() / 2,
                worldPosition.y + sprite.height() / 2, worldPosition.z);
    }

    public void clearEquipped(){
        Arrays.fill(equipped, null);
    }

    public void damage(int damage, Item source){
        damage -= def;
        //log damage

        hp -= damage;
    }

    public void addStatus(Status s){
        status.add(s);
        updateStatusEffects();
    }

    public void removeStatus(Status s){
        status.remove(s);
        updateStatusEffects();
    }



    public abstract String desc();
}
