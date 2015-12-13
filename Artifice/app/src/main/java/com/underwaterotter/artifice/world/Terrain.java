package com.underwaterotter.artifice.world;

public class Terrain {

    public static final int AIR = 0;

    public static final int GRASS = 1;
    public static final int TALL_GRASS = 2;

    public static final int DIRT_WALL = 15;
    public static final int STONE_WALL = 16;
    public static final int DUNGEON_WALL = 17;
    public static final int DUNGEON_FLOOR = 18;

    public static final int WATER = 50;
    public static final int DEEP_WATER = 51;
    public static final int LAVA = 52;

    public static final int TRAP_1 = 21;
    public static final int TRAP_2 = 22;
    public static final int TRAP_3 = 23;
    public static final int TRAP_4 = 24;
    public static final int TRAP_5 = 25;

    public static final int WOOD_DOOR = 26;
    public static final int STONE_DOOR = 27;
    public static final int IRON_DOOR = 28;

    //Decorations
    //Fauna
    public static final int TREE = 100;
    public static final int THICK_TREE = 101;
    public static final int BUSH = 102;
    public static final int THICK_BUSH = 103;
    public static final int STONE = 104;
    public static final int LARGE_STONE = 105;
    public static final int LARGE_STONE_C = 106;


    //Flag Bitmasks
    public static final int PASSABLE = 0x01;
    public static final int CLIMBABLE = 0x02;
    public static final int FLAMMABLE = 0x04;
    public static final int UNSTABLE = 0x08;

    public static final int SOLID = 0x10; //UNPASSABLE
    public static final int LIQUID = 0x20;

    public static final int[] flags = new int[512];
    static {
        flags[GRASS] = PASSABLE | FLAMMABLE;
        flags[TALL_GRASS] = flags[GRASS];
        flags[WATER] = PASSABLE | LIQUID;
        flags[DEEP_WATER] = flags[WATER];
        flags[TREE] = PASSABLE | FLAMMABLE;
        flags[THICK_TREE] = SOLID | FLAMMABLE;
        flags[BUSH] = flags[TREE];
        flags[THICK_BUSH] = flags[THICK_TREE];
        flags[STONE] = PASSABLE;
        flags[LARGE_STONE] = SOLID;
        flags[LARGE_STONE_C] = flags[LARGE_STONE] | CLIMBABLE;
    }
}
