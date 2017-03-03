package com.underwaterotter.artifice.entities.items;

import android.graphics.RectF;

import com.underwaterotter.artifice.entities.Entity;
import com.underwaterotter.artifice.entities.mobs.Mob;
import com.underwaterotter.artifice.entities.mobs.main.Char;
import com.underwaterotter.artifice.sprites.ItemSprite;
import com.underwaterotter.utils.Block;

import java.util.ArrayList;
import java.util.UUID;

public class Item extends Entity{

    private static final String LEVEL = "level";
    private static final String ENCHANTED = "enchanted";
    private static final String BELONGS_TO = "belongs_to";
    private static final String OWNER = "owner";

    private static final int ITEM_THICKNESS = 5;

    int itemID;
    ItemSprite sprite;
    RectF hitBox;

    String name = "generic_item";

    int damage;
    int throwSpeed;
    boolean enchanted;

    ArrayList<String> actions;

    Char plr; //Equipped to; therefore, will follow animations

    //for tracking
    private UUID itemUID;
    private Pouch pouch;//can be a chest or any other inventory

    @Override
    public void saveToBlock(Block block){
        super.saveToBlock(block);

        block.put(ENCHANTED, enchanted);

        block.put(BELONGS_TO, plr);
        block.put(OWNER, pouch);
    }

    @Override
    public void loadFromBlock(Block block){
        super.loadFromBlock(block);

        enchanted = block.getBoolean(ENCHANTED);
    }

    @Override
    public void destroy(){
        super.destroy();

        plr = null;
        pouch = null;

        currentLevel.getItemMapper().removeItem(this);
    }

    public UUID itemUID(){
        return itemUID;
    }

    public UUID setItemUID(UUID id){
        return itemUID = id;
    }

    public int itemID() {
        return itemID;
    }

    public ItemSprite getSprite() {
        return sprite;
    }

    protected Mob[] checkCollision(){
        //find closest mob(s) to check
        Mob[] mobs = currentLevel.getMobMapper().findByCell(cellPosition());
        ArrayList<Mob> collided = new ArrayList<Mob>();

        // Check mobs

        Mob[] collidedMobs = new Mob[collided.size()];
        collided.toArray(collidedMobs);

        return collidedMobs;
    }

    public Pouch owner(){
        return pouch;
    }

    public void owner(Pouch host){
        pouch.add(this);
    }

    public void attach(Char mob, int pos){
        plr = mob;
        worldPosition(mob.getAttachPos()[pos]);
    }

    public void detach(){
        worldPosition(plr.worldPosition());
        // set the angle etc.. reset the item
        plr = null;
    }

    public void drop(){
        if (pouch != null) {
            pouch.remove(this);
        }

        detach();
    }
}
