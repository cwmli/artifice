package com.underwaterotter.artifice.entities;

import com.underwaterotter.ceto.Article;
import com.underwaterotter.ceto.Group;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MobMapper extends Article implements Storable{
    
    public static final String LEVEL_MOBS = "levelmobs";
    public static final String MOBS = "mobs";

    public HashSet<String> levelMobs;

    public HashMap<UUID, Mob> mobs;

    public MobMapper(){

        levelMobs = new HashSet<String>();
        mobs = new HashMap<UUID, Mob>();
        //load all potential mobs from level and addMob to game scene
    }

    @Override
    public void update(){
        for(Mob m : mobs.values()){
            if(m.isActive() && m.isAlive())
                m.update();
        }
    }

    @Override
    public void draw() {
        for(Mob m : mobs.values()){
            if(m.isVisible()){
                m.draw();
            }
        }
    }

    public void destroy(){
        for(Mob m : mobs.values()){
            m.destroy();
        }
        mobs.clear();
        mobs = null;
        levelMobs.clear();
        levelMobs = null;
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
            mobs.put(m.getMobID(), m);
        }
    }
    
    public void addMob(Mob mob){
        mobs.put(mob.setMobID(UUID.randomUUID()), mob);
    }

    public void removeMob(Mob mob){
        if(mob == null){
            return;
        } else if(mob.id() > 0){
            mobs.remove(mob.getMobID());
        }
    }

    public void destroyMob(Mob mob){
        if(mob == null)
            return;
        else if(mob.id() > 0) {
            mob.destroy();
            mobs.remove(mob.getMobID());
        }
    }

    public Mob findByID(UUID id){
        return mobs.get(id);
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

    public Mob[] findAllAlive(){
        ArrayList<Mob> mobCollection = new ArrayList<Mob>();

        for(Mob m : mobs.values()){
            if(m.isAlive()){
                mobCollection.add(m);
            }
        }
        Mob[] collection = new Mob[mobCollection.size()];
        mobCollection.toArray(collection);

        return collection;
    }
}
