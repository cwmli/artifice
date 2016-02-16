package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

public class World {

    private int roomLimit;
    private int rooms;

    private boolean exitRoom;
    private boolean entranceRoom;

    public static void buildWorld(int[] map){
        boolean exitRoom = false;
        boolean entranceRoom = false;
        int rooms = 0;
        //quadratic? or radical?
        int roomLimit = Magic.randRange(Artifice.depth * 10 - 3, Artifice.depth * 10 + 3);

        //build base
        if(Artifice.level.isUnderground){
            Painter.fill(map, Terrain.DUNGEON_WALL);
            do {
                //place the entrance first
                if(!entranceRoom){

                }
                //as the rooms approaches roomLimit, the chance of exitRoom increases
            } while(rooms < roomLimit);
        } else if(!Artifice.level.overworldGenerated) { //build the overworld if it has not been generated
            Painter.fill(map, Terrain.DEEP_WATER);
            //generate geometric island
            Painter.fillPolygon(
                    map,
                    Seed.genPoints(Magic.randRange(10,20), 3, 3),
                    Terrain.GRASS);

            Artifice.level.overworldGenerated = true;
        }
    }

    private static void buildDecoration(int[] map, int noise, int smooth){

    }
}
