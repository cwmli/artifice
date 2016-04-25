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

    private static final int BED_PATTERN_FREQ = 3;

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

            Painter.fill(map, Terrain.STONE);

            //enlarge seedBase to actual level size
            for(int[] seedXY : seedBase){
                Painter.setCell((seedXY[0] * enlargeW) + ((seedXY[1] * enlargeH) * Artifice.level.mapSizeW ));
                Painter.fillRect(map, Terrain.SGRASS_3, enlargeW, enlargeH);
            }
            Artifice.level.overworldGenerated = true;
        }
    }

    public static void smoothMap(int[] map, boolean[] passable){
        //smooth out land
        for(int i = 0; i < SMOOTH_PASSES; i++) {
            ArrayList<Integer> mapSmooth = new ArrayList<>();
            for (int c = 0; c < map.length; c++) {
                if (!passable[c]) {
                    int surrounding = 0;
                    //check surrounding cells for tiles
                    for(int x = 0; x < Artifice.level.SURROUNDING_CELLS.length; x++) {
                        int index = c + Artifice.level.SURROUNDING_CELLS[x];
                        if (index > 0 && index < map.length && passable[index]) {
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
                if (!passable[c]) {
                    //check surrounding cells for tiles
                    for(int x = 0; x < Artifice.level.SURROUNDING_CELLS.length; x++) {
                        int index = c + Artifice.level.SURROUNDING_CELLS[x];
                        if (index > 0 && index < map.length && passable[c]) {
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

    public static void addLiquids(int[] watermap, boolean[] passable){

        Painter.fill(watermap, Terrain.EMPTY);

        for(int i = 0; i < watermap.length; i++){
            if(!passable[i]){
                watermap[i] = Terrain.SWATER;
            }
        }
    }

    public static void buildLiquidBed(int[] map){

        final int[] BORDERSET_1 = [];
        final int[] BORDERSET_2 = [];

        for(int i = 0; i < map.length; i += BED_PATTERN_FREQ){
            if(map[i] == Terrain.STONE){
                if(Magic.randRange(0, 100) < 30){
                    Painter.setCell(i);
                    if(Magic.randRange(0, 100) < 50)
                        Painter.fillBorderRect(map, Terrain.SOLID_BED, BORDERSET_1, Magic.randRange(2, 5), Magic.randRange(2, 5));
                    else
                        Painter.fillBorderRect(map, Terrain.SOLID_BED, BORDERSET_2, Magic.randRange(2, 5), Magic.randRange(2, 5));
                }
            }
        }
    }

    public static void convertTiles(int[] map, int[] watermap, boolean[] passable){
        for (int c = 0; c < map.length; c++) {
            if (passable[c]) {
                //check surrounding cells for tiles - DOWN TILES
                if(passable[c + Artifice.level.SURROUNDING_CELLS[3]] && passable[c + Artifice.level.SURROUNDING_CELLS[1]] &&
                            !passable[c + Artifice.level.SURROUNDING_CELLS[6]] && !passable[c + Artifice.level.SURROUNDING_CELLS[4]]){

                    watermap[c + Artifice.level.SURROUNDING_CELLS[1]] = Terrain.EMPTY;
                    map[c + Artifice.level.SURROUNDING_CELLS[1]] = Terrain.D1GRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c + Artifice.level.SURROUNDING_CELLS[1], false);

                    watermap[c] = Terrain.EMPTY;
                    map[c] = Terrain.CONV_GRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c, false);

                    if(Magic.randRange(0, 100) < 30) {
                        map[c + Artifice.level.SURROUNDING_CELLS[6]] = Terrain.CONV_STONE;
                    } else {
                        map[c + Artifice.level.SURROUNDING_CELLS[6]] = Terrain.STONE;
                    }
                    GameScene.scene.tilemap.updateFlipData(c + Artifice.level.SURROUNDING_CELLS[6], false);

                    watermap[c] = Terrain.DWATER_1;
                    GameScene.scene.watermap.updateFlipData(c, false);

                } else if(passable[c + Artifice.level.SURROUNDING_CELLS[4]] && passable[c + Artifice.level.SURROUNDING_CELLS[1]] &&
                        !passable[c + Artifice.level.SURROUNDING_CELLS[6]] && !passable[c + Artifice.level.SURROUNDING_CELLS[3]]){

                    watermap[c + Artifice.level.SURROUNDING_CELLS[1]] = Terrain.EMPTY;
                    map[c + Artifice.level.SURROUNDING_CELLS[1]] = Terrain.D1GRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c + Artifice.level.SURROUNDING_CELLS[1], true);

                    watermap[c] = Terrain.EMPTY;
                    map[c] = Terrain.CONV_GRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c, true);

                    if(Magic.randRange(0, 100) < 30) {
                        map[c + Artifice.level.SURROUNDING_CELLS[6]] = Terrain.CONV_STONE;
                    } else {
                        map[c + Artifice.level.SURROUNDING_CELLS[6]] = Terrain.STONE;
                    }
                    GameScene.scene.tilemap.updateFlipData(c + Artifice.level.SURROUNDING_CELLS[6], true);

                    watermap[c] = Terrain.DWATER_1;
                    GameScene.scene.watermap.updateFlipData(c, true);

                } else if(!passable[c + Artifice.level.SURROUNDING_CELLS[6]] && passable[c + Artifice.level.SURROUNDING_CELLS[1]]) {

                    map[c] = Terrain.TGRASS_3;
                    map[c + Artifice.level.SURROUNDING_CELLS[6]] = Terrain.STONE;
                    watermap[c + Artifice.level.SURROUNDING_CELLS[6]] = Terrain.TWATER_1;
                   if(Magic.randRange(0, 100) < 50){
                       GameScene.scene.tilemap.updateFlipData(c, true);
                   }

                } else if(!passable[c + Artifice.level.SURROUNDING_CELLS[6]]){
                   map[c] = Terrain.SWATER;
                }  else if(!passable[c + Artifice.level.SURROUNDING_CELLS[1]] && !passable[c + Artifice.level.SURROUNDING_CELLS[3]]
                        && passable[c + Artifice.level.SURROUNDING_CELLS[4]]) {

                    map[c] = Terrain.CGRASS_3;
                    watermap[c] = Terrain.SWATER;
                    GameScene.scene.tilemap.updateFlipData(c, false);

                } else if(!passable[c + Artifice.level.SURROUNDING_CELLS[1]] && !passable[c + Artifice.level.SURROUNDING_CELLS[4]]
                        && passable[c + Artifice.level.SURROUNDING_CELLS[3]]){

                    map[c] = Terrain.CGRASS_3;
                    watermap[c] = Terrain.SWATER;
                    GameScene.scene.tilemap.updateFlipData(c, true);

                }  else if(!passable[c + Artifice.level.SURROUNDING_CELLS[1]] && passable[c + Artifice.level.SURROUNDING_CELLS[6]]) {

                    map[c] = Terrain.EGRASS_3;
                    watermap[c] = Terrain.SWATER;
                    if (Magic.randRange(0, 100) < 50) {
                        GameScene.scene.tilemap.updateFlipData(c, true);
                    }
                }
            }
        }
    }

    private static void buildDecoration(int[] map, int noise, int smooth){

    }
}
