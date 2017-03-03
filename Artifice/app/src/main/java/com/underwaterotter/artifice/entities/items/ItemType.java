package com.underwaterotter.artifice.entities.items;

/**
 * Created by Calvin on 02/03/2017.
 */

public class ItemType {

    public static final int TESTWEP = 0;


    // ItemType Bitmasks
    public static final int HEAD  = 0x0001;
    public static final int BODY  = 0x0002;
    public static final int LEG   = 0x0004;
    public static final int FOOT  = 0x0016;
    public static final int HANDS = 0x0032;

    public static final int LEFT  = 0x0064;
    public static final int RIGHT = 0x0128;
    public static final int TWO_H = 0x0256;

    public static final int[] flags = new int[512];
    static {
        flags[TESTWEP] = LEFT | RIGHT;
    }
}
