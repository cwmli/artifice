package com.underwaterotter.artifice.entities;

import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MobMapper implements Storable{
    
    public static final String LEVEL_MOBS = "levelmobs";
    public static final String MOBS = "mobs";

    public HashSet<String> levelMobs;

    public HashMap<UUID, Mob> mobs;

    public void init(){

        levelMobs = new HashSet<String>();
        mobs = new HashMap<UUID, Mob>();
        //load all potential mobs from level and add to game scene
    }

    @Override
    public void saveToBlock(Block block){

        block.put(LEVEL_MOBS, levelMobs);
        block.put(MOBS, mobs.values());
    }

    @Override
    public void loadFromBlock(Block block){

        String[] templv = block.getStringArray(LEVEL_MOBS);
        for(String stor : templv){
            levelMobs.add(stor);
        }

        Storable[] tempall = block.getStorableArray(MOBS);
        Mob[] tempMobs = Arrays.copyOf(tempall, tempall.length, Mob[].class);
        for(Mob m : tempMobs){
            mobs.put(m.mobID(), m);
        }
    }

    public void destroy(){

        for(Mob m : mobs.values()){
            m.destroy();
        }
        reset();
    }

    public void reset(){
        levelMobs.clear();
        mobs.clear();
    }

    public void add(Mob mob){
        mobs.put(mob.setMobID(UUID.randomUUID()), mob);
    }

    public void remove(Mob mob){
        if(mob == null){
            return;
        } else if(mob.id() > 0){
            mobs.remove(mob.mobID());
        }
    }

    public Mob[] findByCell(int pos){

        ArrayList<Mob> mobCollection = new ArrayList<Mob>();

        for(Mob m : mobs.values()){
            if(m.cellPosition() == pos){
                mobCollection.add(m);
            }
        }
        Mob[] collection = new Mob[mobCollection.size()];
        mobCollection.toArray(collection);

        return collection;
    }
    
    public Mob findByID(UUID id){
        return mobs.get(id);
    }
}
