package com.underwaterotter.artifice.world;

public class Terrain {

    public static final int EMPTY = 96;

    public static final int TWATER_1 = 77;
    public static final int TWATER_2 = 78;
    public static final int TWATER_3 = 79;

    public static final int DWATER_1 = 87;
    public static final int DWATER_2 = 88;
    public static final int DWATER_3 = 89;

    public static final int SWATER_3 = 97;
    public static final int SWATER_2 = 98;
    public static final int SWATER_1 = 99;

    public static final int[] WATER = {DWATER_1, DWATER_2, DWATER_3, SWATER_1, SWATER_2};

    //water bed tiles
    public static final int SOLID_BED = 37;
    public static final int RND_CORNER_BED = 38;
    public static final int TOP_BED = 39;
    public static final int RGH_CORNER_BED = 47;
    public static final int LFT_BED = 48;
    public static final int BOT_BED = 49;
    public static final int RGH_CORNER_BED_B = 57;
    public static final int RND_CORNER_BED_B = 58;
    public static final int LSOLID_BED = 59;

    public static final int STONE = 27;
    public static final int DSTONE = 28;
    public static final int CONV_STONE = 29;

    public static final int SGRASS_1 = 0;
    public static final int TOPGRASS_1 = 1;
    public static final int CORNERGRASS_1 = 2;
    public static final int LFTGRASS_1 = 3;
    public static final int BOTGRASS_1 = 4;
    public static final int CORNERGRASS_1_B = 5;
    public static final int TGRASS_1 = 6;
    public static final int D1GRASS_1 = 7;
    public static final int D2GRASS_1 = 8;
    public static final int CONV_GRASS_1 = 9;

    public static final int SGRASS_2 = 10;
    public static final int TOPGRASS_2 = 11;
    public static final int CORNERGRASS_2 = 12;
    public static final int LFTGRASS_2 = 13;
    public static final int BOTGRASS_2 = 14;
    public static final int CORNERGRASS_2_B = 15;
    public static final int TGRASS_2 = 16;
    public static final int D1GRASS_2 = 17;
    public static final int D2GRASS_2 = 18;
    public static final int CONV_GRASS_2 = 19;

    public static final int SGRASS_3 = 20;
    public static final int EGRASS_3 = 21;
    public static final int CGRASS_3 = 22;
    public static final int TGRASS_3 = 23;
    public static final int D1GRASS_3 = 24;
    public static final int D2GRASS_3 = 25;
    public static final int CONV_GRASS_3 = 26;

    public static final int DUNGEON_FLOOR = 100;
    public static final int DUNGEON_WALL = 101;
    public static final int WOOD_DOOR = 102;
    public static final int TREE = 103;
    public static final int THICK_TREE = 104;
    public static final int BUSH = 105;
    public static final int THICK_BUSH = 106;
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

        flags[SGRASS_1] = PASSABLE | FLAMMABLE;
        flags[TOPGRASS_1] = PASSABLE | FLAMMABLE;
        flags[CORNERGRASS_1] = PASSABLE | FLAMMABLE;
        flags[TGRASS_1] = SOLID | FLAMMABLE;
        flags[D1GRASS_1] = PASSABLE | FLAMMABLE;
        flags[D2GRASS_1] = SOLID | FLAMMABLE;
        flags[CONV_GRASS_1] = SOLID | FLAMMABLE;

        flags[SGRASS_2] = PASSABLE | FLAMMABLE;
        flags[TOPGRASS_2] = PASSABLE | FLAMMABLE;
        flags[CORNERGRASS_2] = PASSABLE | FLAMMABLE;
        flags[TGRASS_2] = SOLID | FLAMMABLE;
        flags[D1GRASS_2] = PASSABLE | FLAMMABLE;
        flags[D2GRASS_2] = SOLID | FLAMMABLE;
        flags[CONV_GRASS_2] = SOLID | FLAMMABLE;

        flags[SGRASS_3] = PASSABLE | FLAMMABLE;
        flags[EGRASS_3] = PASSABLE | FLAMMABLE;
        flags[CGRASS_3] = PASSABLE | FLAMMABLE;
        flags[TGRASS_3] = SOLID | FLAMMABLE;
        flags[D1GRASS_3] = PASSABLE | FLAMMABLE;
        flags[D2GRASS_3] = SOLID | FLAMMABLE;
        flags[CONV_GRASS_3] = SOLID | FLAMMABLE;

        flags[SWATER_1] = SOLID | LIQUID;
        flags[SWATER_2] = SOLID | LIQUID;
        flags[DWATER_1] = SOLID | LIQUID;
        flags[DWATER_2] = SOLID | LIQUID;
        flags[DWATER_3] = SOLID | LIQUID;

        flags[STONE] = SOLID;
        flags[DSTONE] = SOLID;
        flags[CONV_STONE] = SOLID;

        flags[TREE] = PASSABLE | FLAMMABLE;
        flags[THICK_TREE] = SOLID | FLAMMABLE;
        flags[BUSH] = flags[TREE];
        flags[THICK_BUSH] = flags[THICK_TREE];
        flags[LARGE_STONE] = SOLID;
        flags[LARGE_STONE_C] = flags[LARGE_STONE] | CLIMBABLE;
    }
}
