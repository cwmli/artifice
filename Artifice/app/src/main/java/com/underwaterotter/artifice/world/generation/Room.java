package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

public class Room extends Painter{

    private static void room(int[] map, int pos, int l, int w){
        //setup room entrances
        // X---side1----X
        // |            |
        // side4     side2
        // |            |
        // X---side3----X
        int entranceLimit = Magic.randRange(1, 4);
        for(int i = 0; i < entranceLimit; i++) {
            int side = Magic.randRange(1, 4);
            switch (side){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }

        setCell(pos);
        fillRect(map, Terrain.DUNGEON_FLOOR, l - 1, w - 1);
        //check for doors or open chambers

    }

    private static void chamber(int[] map, int pos, int l, int w){

    }

    private static void tunnel(int[] map, int length){

    }
}
