package com.underwaterotter.artifice.entities.mobs.main;

import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.artifice.entities.items.ItemType;
import com.underwaterotter.math.Vector3;

/**
 * Created by Calvin on 02/03/2017.
 */

public class EquipSlots {

    static final int SIZE = 8;

    static final int HEAD  = 0;
    static final int BODY  = 1;
    static final int LEG   = 2;
    static final int FOOT  = 3;
    static final int HANDS = 4;
    static final int LEFT  = 5;
    static final int RIGHT = 6;
    static final int BACK  = 7;

    static int[] flags = new int[8];
    static {
        flags[HEAD] = ItemType.HEAD;
        flags[BODY] = ItemType.BODY;
        flags[LEG]  = ItemType.LEG;
        flags[FOOT] = ItemType.FOOT;
        flags[HANDS]= ItemType.HANDS;

        flags[LEFT] = ItemType.LEFT  | ItemType.TWO_H;
        flags[RIGHT]= ItemType.RIGHT | ItemType.TWO_H;
        flags[BACK] = ItemType.LEFT | ItemType.RIGHT | ItemType.TWO_H;
    }
}
