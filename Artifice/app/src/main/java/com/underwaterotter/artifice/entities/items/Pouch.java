package com.underwaterotter.artifice.entities.items;

import com.underwaterotter.artifice.entities.Entity;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

public class Pouch extends Entity implements Storable {

    public static String INVENTORY = "inventory";

    public Item[] inventory;

    @Override
    public void saveToBlock(Block block){
        block.put(INVENTORY, inventory);
    }

    @Override
    public void loadFromBlock(Block block){
        Storable[] buffer = block.getStorableArray(INVENTORY);
        for(int i = 0; i < buffer.length; i++){
            inventory[i] = (Item)buffer[i];
        }
    }

    //create instances of the items
}
