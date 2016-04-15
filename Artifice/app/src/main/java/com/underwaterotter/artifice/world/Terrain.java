package com.underwaterotter.artifice.world;

public class Terrain {

    public static final int EMPTY = 511;

    public static final int WATER = 27;
    public static final int DEEP_WATER = 28;

    public static final int TEMP_SOIL = 29;

    //S = solid, E = edge, C = corner, T = top, D = diagonal

    public static final int SGRASS_1 = 0;
    public static final int EGRASS_1 = 1;
    public static final int CGRASS_1 = 2;
    public static final int TGRASS_1 = 3;
    public static final int D1GRASS_1 = 4;
    public static final int D2GRASS_1 = 5;

    public static final int SGRASS_2 = 6;
    public static final int EGRASS_2 = 7;
    public static final int CGRASS_2 = 8;
    public static final int TGRASS_2 = 9;
    public static final int D1GRASS_2 = 10;
    public static final int D2GRASS_2 = 11;

    public static final int SGRASS_3 = 12;
    public static final int EGRASS_3 = 13;
    public static final int CGRASS_3 = 14;
    public static final int TGRASS_3 = 15;
    public static final int D1GRASS_3 = 16;
    public static final int D2GRASS_3 = 17;

    public static final int ROCK_1 = 18;
    public static final int ROCK_2 = 19;
    public static final int ROCK_3 = 20;

    public static final int STUMP_1 = 21;
    public static final int STUMP_2 = 22;
    public static final int STUMP_3 = 23;

    public static final int TALL_GRASS_1 = 24;
    public static final int TALL_GRASS_2 = 25;
    public static final int FLOWER = 26;

    public static final int DUNGEON_FLOOR = 100;
    public static final int DUNGEON_WALL = 101;
    public static final int WOOD_DOOR = 102;
    public static final int TREE = 103;
    public static final int THICK_TREE = 104;
    public static final int BUSH = 105;
    public static final int THICK_BUSH = 106;
    public static final int STONE = 107;
    public static final int LARGE_STONE = 108;
    public static final int LARGE_STONE_C = 109;


    //Flag Bitmasks
    public static final int PASSABLE = 0x01;
    public static final int CLIMBABLE = 0x02;
    public static final int FLAMMABLE = 0x04;
    public static final int UNSTABLE = 0x08;

    public static final int SOLID = 0x10; //UNPASSABLE
    public static final int LIQUID = 0x20;

    public static final int[] flags = new int[512];
    static {
        flags[EMPTY] = SOLID;
        flags[TEMP_SOIL] = SOLID;

        flags[SGRASS_1] = PASSABLE | FLAMMABLE;
        flags[EGRASS_1] = PASSABLE | FLAMMABLE;
        flags[CGRASS_1] = PASSABLE | FLAMMABLE;
        flags[TGRASS_1] = SOLID | FLAMMABLE;
        flags[D1GRASS_1] = PASSABLE | FLAMMABLE;
        flags[D2GRASS_1] = SOLID | FLAMMABLE;

        flags[SGRASS_2] = PASSABLE | FLAMMABLE;
        flags[EGRASS_2] = PASSABLE | FLAMMABLE;
        flags[CGRASS_2] = PASSABLE | FLAMMABLE;
        flags[TGRASS_2] = SOLID | FLAMMABLE;
        flags[D1GRASS_2] = PASSABLE | FLAMMABLE;
        flags[D2GRASS_2] = SOLID | FLAMMABLE;

        flags[SGRASS_3] = PASSABLE | FLAMMABLE;
        flags[EGRASS_3] = PASSABLE | FLAMMABLE;
        flags[CGRASS_3] = PASSABLE | FLAMMABLE;
        flags[TGRASS_3] = SOLID | FLAMMABLE;
        flags[D1GRASS_3] = PASSABLE | FLAMMABLE;
        flags[D2GRASS_3] = SOLID | FLAMMABLE;

        flags[WATER] = SOLID | LIQUID;
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
