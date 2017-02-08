package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Rand;

public class Room extends Painter{

    private static void room(int[] map, int pos, int w, int h){
        //setup room entrances
        // X---side1----X
        // |            |
        // side4     side2
        // |            |
        // X---side3----X
        int entranceLimit = Rand.range(1, 4);
        for(int i = 0; i < entranceLimit; i++) {
            int side = Rand.range(1, 4);
            int selection_w = Rand.range(1, w - 2);
            int selection_h = Rand.range(1, h - 2);
            switch (side){
                case 1:
                    map[pos + selection_w] = Terrain.WOOD_DOOR;
                    break;
                case 2:
                    map[pos + (Artifice.getLevel().mapWidth * selection_h) + w - 1] = Terrain.WOOD_DOOR;
                    break;
                case 3:
                    map[pos + (Artifice.getLevel().mapWidth * (h - 1)) + selection_w] = Terrain.WOOD_DOOR;
                    break;
                case 4:
                    map[pos + (Artifice.getLevel().mapWidth * selection_h)] = Terrain.WOOD_DOOR;
                    break;
            }
        }

        setCell(pos + Artifice.getLevel().mapWidth + 1);
        fillRect(map, Terrain.DUNGEON_FLOOR, w - 1, h - 1);
        //check for doors or open chambers
    }

    private static void chamber(int[] map, int pos, int l, int h){

    }

    private static void tunnel(int[] map, int length){

    }
}
