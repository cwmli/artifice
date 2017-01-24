package com.underwaterotter.artifice.entities.items;

import android.graphics.RectF;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.entities.Entity;
import com.underwaterotter.artifice.entities.Mob;
import com.underwaterotter.artifice.sprites.ItemSprite;
import com.underwaterotter.utils.Block;

import java.util.ArrayList;
import java.util.UUID;

public class Item extends Entity{

    public static String LEVEL = "level";
    public static String ENCHANTED = "enchanted";
    public static String UUID = "uuid";
    public static String BELONGS_TO = "belongs_to";
    public static String OWNER = "owner";

    public static int ITEM_THICKNESS = 5;

    //for tracking
    private UUID item_id;

    public ItemSprite sprite;

    public static String name = "generic_item";

    public int damage;
    public int level;
    public boolean enchanted;
    public int animationSpeed;

    public int throwSpeed;

    protected ArrayList<String> actions;

    public Mob mob; //Equipped to; therefore, will follow animations

    private Pouch host;//can be a chest or any other inventory

    @Override
    public void saveToBlock(Block block){
        super.saveToBlock(block);

        block.put(LEVEL, level);
        block.put(ENCHANTED, enchanted);
        block.put(UUID, item_id);

        block.put(BELONGS_TO, mob);
        block.put(OWNER, host);
    }

    @Override
    public void loadFromBlock(Block block){
        super.loadFromBlock(block);

        level = block.getInt(LEVEL);
        enchanted = block.getBoolean(ENCHANTED);
    }

    @Override
    public void destroy(){
        super.destroy();

        mob = null;
        host = null;

        currentLevel.getItemMapper().removeItem(this);
    }

    public UUID itemID(){
        return item_id;
    }

    public UUID setItemID(UUID id){
        return item_id = id;
    }

    protected Mob[] checkCollision(){
        //find closest mob(s) to check
        Mob[] mobs = currentLevel.getMobMapper().findByCell(cellPosition());
        ArrayList<Mob> collided = new ArrayList<Mob>();

        if(mobs != null) {
            for(Mob mob : mobs) {
                if (RectF.intersects(mob.getHitBox(), sprite.getBoundingBox()) && mob.worldPosition().y - worldPosition().y <= ITEM_THICKNESS) {
                    collided.add(mob);
                }
            }
        }
        Mob[] collidedMobs = new Mob[collided.size()];
        collided.toArray(collidedMobs);

        return collidedMobs;
    }

    public Pouch owner(){
        return host;
    }

    public void owner(Pouch host){
        this.host = host;
        worldPosition(host.worldPosition());
    }

    public void attach(Mob mob){

        boolean added = false;
        this.mob = mob;

        for(int i = 0; i < mob.getEquipped().length; i++){
            ItemSprite iSpr = mob.getEquipped()[i];
            if(iSpr == null){
                mob.getEquipped()[i] = sprite;
                sprite.setPos(mob.getAttachPositions()[i]);
                added = true;
            }
        }

        if(!added){
            return;
        }

        /// FIXME: 29/08/15 scale down sprite to match Mob size
    }

    public void detach(){
        for(int i = 0; i < mob.getEquipped().length; i++){
            ItemSprite iSpr = mob.getEquipped()[i];
            if(iSpr == sprite){
                mob.getEquipped()[i] = null;
            }
        }
    }

    public void drop(){
        ///// FIXME: 29/08/15 upscale sprite size back into regular size (icon)
    }
}
