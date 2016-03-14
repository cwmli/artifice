package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

import java.util.ArrayList;

public class World {

    private static final int SPACING = 3;

    public static void buildWorld(int[] map){

        boolean exitRoom = false;
        boolean entranceRoom = false;

        int rooms = 0;
        int roomLimit = Magic.randRange(Artifice.depth * 10 - 3, Artifice.depth * 10 + 3);

        int enlargeW = Artifice.level.mapSizeW / Seed.MIN_SIDE_LENGTH;
        int enlargeH = Artifice.level.mapSizeH / Seed.MIN_SIDE_LENGTH;

        //build base
        if(Artifice.level.isUnderground){
            Painter.fill(map, Terrain.DUNGEON_WALL);
            do {
                if(!entranceRoom){

                }
                //as the rooms approaches roomLimit, the chance of exitRoom increases
            } while(rooms < roomLimit);
        } else if(!Artifice.level.overworldGenerated) { //build the overworld if it has not been generated
            ArrayList<Integer> seedBase = Seed.initBase();


            Painter.fill(map, Terrain.DEEP_WATER);

            for(int seedValue = 0; seedValue < seedBase.size(); seedValue++){
                Painter.setCell(seedBase.get(seedValue) + );
                //shift the original positions to growth factor
            }

            Artifice.level.overworldGenerated = true;
        }
    }

    private static void buildDecoration(int[] map, int noise, int smooth){

    }
}
