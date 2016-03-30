package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

import java.util.ArrayList;

public class World {

    private static final int SMOOTH_PASSES = 3;
    private static final int BASE_TOUCH = 3;

    private static final int NOISE_PASS = 5;
    private static final int NOISE_CHANCE = 60;

    public static void buildWorld(int[] map){

        boolean exitRoom = false;
        boolean entranceRoom = false;

        int rooms = 0;
        int roomLimit = Magic.randRange(Artifice.depth * 10 - 3, Artifice.depth * 10 + 3);

        int enlargeW = (int)Math.floor(Artifice.level.mapSizeW / Seed.MIN_SIDE_LENGTH);
        int enlargeH = (int)Math.floor(Artifice.level.mapSizeH / Seed.MIN_SIDE_LENGTH);

        //build base
        if(Artifice.level.isUnderground){
            Painter.fill(map, Terrain.DUNGEON_WALL);
            do {
                if(!entranceRoom){

                }
                //as the rooms approaches roomLimit, the chance of exitRoom increases
            } while(rooms < roomLimit);
        } else if(!Artifice.level.overworldGenerated) { //build the overworld if it has not been generated
            ArrayList<int[]> seedBase = Seed.initBase();

            Painter.fill(map, Terrain.WATER);

            //enlarge seedBase to actual level size
            for(int[] seedXY : seedBase){
                Painter.setCell((seedXY[0] * enlargeW) + ((seedXY[1] * enlargeH) * Artifice.level.mapSizeW ));
                Painter.fillRect(map, Terrain.SGRASS_3, enlargeW, enlargeH);
            }

            smoothMap(map);
            convertTiles(map);

            Artifice.level.overworldGenerated = true;
        }
    }

    private static void smoothMap(int[] map){
        //smooth out land
        for(int i = 0; i < SMOOTH_PASSES; i++) {
            ArrayList<Integer> mapSmooth = new ArrayList<>();
            for (int c = 0; c < map.length; c++) {
                if (map[c] == Terrain.WATER) {
                    int surrounding = 0;
                    //check surrounding cells for tiles
                    for(int x = 0; x < Artifice.level.SURROUNDING_CELLS.length; x++) {
                        int index = c + Artifice.level.SURROUNDING_CELLS[x];
                        if (index > 0 && index < map.length && map[index] == Terrain.SGRASS_3) {
                            surrounding += 1;

                            if(surrounding == BASE_TOUCH + i){
                                break;
                            }
                        }
                    }
                    //queue up the smooth
                    if(surrounding == BASE_TOUCH + i){
                        mapSmooth.add(c);
                    }
                }
            }
            for(int cell : mapSmooth){
                map[cell] = Terrain.SGRASS_3;
            }
        }
        //add some noise
        //randomly expand the island
        for(int i = 0; i < NOISE_PASS; i++) {
            ArrayList<Integer> mapChanges = new ArrayList<>();
            for (int c = 0; c < map.length; c++) {
                if (map[c] == Terrain.WATER) {
                    //check surrounding cells for tiles
                    for(int x = 0; x < Artifice.level.SURROUNDING_CELLS.length; x++) {
                        int index = c + Artifice.level.SURROUNDING_CELLS[x];
                        if (index > 0 && index < map.length && map[index] == Terrain.SGRASS_3) {
                            if(Magic.randRange(0, 100) < NOISE_CHANCE){
                                mapChanges.add(c);
                            }
                        }
                    }
                }
            }
            for(int cell : mapChanges){
                map[cell] = Terrain.SGRASS_3;
            }
        }
    }

    private static void convertTiles(int[] map){
        for (int c = 0; c < map.length; c++) {
            if (map[c] == Terrain.SGRASS_3) {
                //check surrounding cells for tiles
                if(map[c + Artifice.level.SURROUNDING_CELLS[3]] != Terrain.WATER && map[c + Artifice.level.SURROUNDING_CELLS[1]] == Terrain.SGRASS_3 &&
                            map[c + Artifice.level.SURROUNDING_CELLS[6]] == Terrain.WATER && map[c + Artifice.level.SURROUNDING_CELLS[4]] == Terrain.WATER){
                    map[c + Artifice.level.SURROUNDING_CELLS[1]] = Terrain.D1GRASS_3;
                    map[c] = Terrain.D2GRASS_3;
                } else if(map[c + Artifice.level.SURROUNDING_CELLS[4]] != Terrain.WATER && map[c + Artifice.level.SURROUNDING_CELLS[1]] == Terrain.SGRASS_3 &&
                        map[c + Artifice.level.SURROUNDING_CELLS[6]] == Terrain.WATER && map[c + Artifice.level.SURROUNDING_CELLS[3]] == Terrain.WATER){
                    map[c + Artifice.level.SURROUNDING_CELLS[1]] = Terrain.D1GRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c + Artifice.level.SURROUNDING_CELLS[1], true);
                    map[c] = Terrain.D2GRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c, true);
                } else if(map[c + Artifice.level.SURROUNDING_CELLS[6]] == Terrain.WATER && map[c + Artifice.level.SURROUNDING_CELLS[1]] == Terrain.SGRASS_3) {
                   map[c] = Terrain.TGRASS_3;
                   if(Magic.randRange(0, 100) < 50){
                       GameScene.scene.tilemap.updateFlipData(c, true);
                   }
                } else if(map[c + Artifice.level.SURROUNDING_CELLS[1]] == Terrain.WATER && map[c + Artifice.level.SURROUNDING_CELLS[6]] == Terrain.SGRASS_3){
                    map[c] = Terrain.EGRASS_3;
                    if(Magic.randRange(0, 100) < 50){
                        GameScene.scene.tilemap.updateFlipData(c, true);
                    }
                } else if(map[c + Artifice.level.SURROUNDING_CELLS[6]] == Terrain.WATER){
                   map[c] = Terrain.WATER;
                }
            }
        }
    }

    private static void buildDecoration(int[] map, int noise, int smooth){

    }
}
