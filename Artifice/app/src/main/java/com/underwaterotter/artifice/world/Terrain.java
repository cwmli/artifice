package com.underwaterotter.artifice.world;

public class Terrain {

    public static final int ELEVATED_END  = 95;
    public static final int EMPTY         = 96;

    public static final int TWATER_1      = 77;
    public static final int TWATER_2      = 78;
    public static final int TWATER_3      = 79;

    public static final int DWATER_1      = 87;
    public static final int DWATER_2      = 88;
    public static final int DWATER_3      = 89;

    public static final int SWATER_3      = 97;
    public static final int SWATER_2      = 98;
    public static final int SWATER_1      = 99;

    public static final int[] WATER = {DWATER_1, DWATER_2, DWATER_3, SWATER_1, SWATER_2};

    //ABOVEGROUND TILESET
    public static final int SOLID_GRASS   = 0;
    public static final int EDGE_GRASS    = 1;
    public static final int V_EDGE_GRASS  = 2;
    public static final int CORNER_GRASS  = 3;
    public static final int TOP_GRASS     = 4;
    public static final int DIAG_GRASS    = 5;

    public static final int T_EDGE_GRASS_H= 10;
    public static final int V_EDGE_GRASS_H= 11;
    public static final int B_EDGE_GRASS_H= 12;
    public static final int CORNER_GRASS_H= 13;
    public static final int BT_EDGE_GRASS_H=14;
    public static final int B_TOP_GRASS_H = 15;
    public static final int R_TOP_GRASS_H = 16;

    public static final int DIAG_GRASS_H  = 20;
    public static final int CONV_GRASS    = 21;
    public static final int STONE         = 22;
    public static final int DSTONE        = 23;
    public static final int CONV_STONE    = 24;

    //WATER BED TILES
    public static final int SOLID_BED     = 47;
    public static final int R_C_BED       = 48;
    public static final int TOP_BED       = 49;
    public static final int RG_C_BED      = 57;
    public static final int LFT_BED       = 58;
    public static final int BOT_BED       = 59;
    public static final int RG_C_BED_B    = 67;
    public static final int R_C_BED_B     = 68;
    public static final int LSOLID_BED    = 69;

    public static final int DUNGEON_FLOOR = 100;
    public static final int DUNGEON_WALL  = 101;
    public static final int WOOD_DOOR     = 102;
    public static final int TREE          = 103;
    public static final int THICK_TREE    = 104;
    public static final int BUSH          = 105;
    public static final int THICK_BUSH    = 106;
    public static final int LARGE_STONE   = 108;
    public static final int LARGE_STONE_C = 109;


    //Flag Bitmasks
    public static final int PASSABLE      = 0x01;
    public static final int CLIMBABLE     = 0x02;
    public static final int FLAMMABLE     = 0x04;
    public static final int UNSTABLE      = 0x08;

    public static final int SOLID         = 0x10; //UNPASSABLE
    public static final int LIQUID        = 0x20;

    public static final int[] flags = new int[512];
    static {
        flags[EMPTY] = SOLID;

        flags[SOLID_GRASS] = PASSABLE | FLAMMABLE;
        flags[T_EDGE_GRASS_H] = PASSABLE | FLAMMABLE;
        flags[CORNER_GRASS_H] = PASSABLE | FLAMMABLE;
        flags[TOP_GRASS] = SOLID | FLAMMABLE;
        flags[DIAG_GRASS] = PASSABLE | FLAMMABLE;
        flags[DIAG_GRASS_H] = SOLID | FLAMMABLE;
        flags[CONV_GRASS] = SOLID | FLAMMABLE;

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
