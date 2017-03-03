package com.underwaterotter.artifice.entities.items;

import com.underwaterotter.ceto.Group;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

public class Pouch implements Storable {

    private static String INVENTORY = "inventory";

    private Item[] inventory;

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

    public void add(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                break;
            }
        }
    }

    public void remove(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == item) {
                inventory[i] = null;
                break;
            }
        }
    }

    public Item getItem(int slot) {
        return inventory[slot];
    }

    public void dropItem(Item item) {
        item.drop();
        remove(item);
    }
}
