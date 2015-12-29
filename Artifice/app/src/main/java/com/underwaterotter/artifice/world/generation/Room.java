package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

public class Room extends Painter{

    private static int[] room(Level.Map map, int pos, int w, int h){
        //setup room entrances
        // X---side1----X
        // |            |
        // side4     side2
        // |            |
        // X---side3----X
        int entranceLimit = Magic.randRange(1, 4);
        for(int i = 0; i < entranceLimit; i++) {
            int side = Magic.randRange(1, 4);
            int selection_w = Magic.randRange(1, w - 2);
            int selection_h = Magic.randRange(1, h - 2);
            switch (side){
                case 1:
                    map.add(pos + selection_w, Terrain.WOOD_DOOR);
                    break;
                case 2:
                    map[pos + (Artifice.level.mapSize_W * selection_h) + w - 1] = Terrain.WOOD_DOOR;
                    break;
                case 3:
                    map[pos + (Artifice.level.mapSize_W * (h - 1)) + selection_w] = Terrain.WOOD_DOOR;
                    break;
                case 4:
                    map[pos + (Artifice.level.mapSize_W * selection_h)] = Terrain.WOOD_DOOR;
                    break;
            }
        }

        setCell(pos + Artifice.level.mapSize_W + 1);
        fillRect(map, Terrain.DUNGEON_FLOOR, w - 1, h - 1);
        //check for doors or open chambers
        return map;
    }

    private static void chamber(int[] map, int pos, int l, int h){

    }

    private static void tunnel(int[] map, int length){

    }
}
