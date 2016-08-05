package com.underwaterotter.artifice.entities.items;

import com.underwaterotter.ceto.Article;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ItemMapper extends Article implements Storable {

    public final String LEVEL_ITEMS = "levelitems";
    public final String ITEMS = "items";

    public HashSet<String> levelItems;

    public HashMap<UUID, Item> items;

    public ItemMapper(){
        levelItems = new HashSet<String>();
        items = new HashMap<UUID, Item>();
        //load all potential items form level and addMob to game scene
    }

    @Override
    public void update(){
        for(Item i : items.values()){
            if(i.isActive())
                i.update();
        }
    }

    @Override
    public void draw(){
        for(Item i : items.values()){
            if(i.isVisible())
                i.draw();
        }
    }

    @Override
    public void destroy(){
        for(Item i : items.values()){
            i.destroy();
        }
        items.clear();
        items = null;
        levelItems.clear();
        levelItems = null;
    }

    @Override
    public void saveToBlock(Block block){

        block.put(LEVEL_ITEMS, levelItems);
        block.put(ITEMS, items.values());
    }

    @Override
    public void loadFromBlock(Block block){

        String[] templv = block.getStringArray(LEVEL_ITEMS);
        for(String stor : templv){
            levelItems.add(stor);
        }

        Storable[] tempall = block.getStorableArray(ITEMS);
        Item[] tempitems = Arrays.copyOf(tempall, tempall.length, Item[].class);
        for(Item i : tempitems){
            items.put(i.itemID(), i);
        }
    }

    public void addItem(Item item){
        items.put(item.setItemID(UUID.randomUUID()), item);
    }

    public void removeItem(Item item){
        if(item != null)
            items.remove(item.itemID());
    }

    public void destroyItem(Item item){
        if(item != null && item.id() > 0) {
            item.destroy();
            items.remove(item.itemID());
        }
    }

    public Item findByID(UUID id){
        return items.get(id);
    }
}
