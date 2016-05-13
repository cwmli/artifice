package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

import java.util.ArrayList;

public class World {

    public static final int DEFAULT = 0;

    private static final int SMOOTH_PASSES = 3;
    private static final int BASE_TOUCH = 3;

    private static final int NOISE_PASS = 5;
    private static final int NOISE_CHANCE = 60;

    public static final double F1 = 1.0;
    public static final double F2 = 2.0;
    public static final double F3 = 4.0;

    public static final double REDIS = 3.65;

    public static final double AMP = 0.05;
    public static final double SMOOTH = 1.30;
    public static final double DROP = 2.60;

    private static final int BED_PATTERN_FREQ = 3;

    private double[][] elevation;

    /*
     * amplitude = 0.0 - 1.0
     * smoothing = 0.0 - 2.0
     * dropoff = 0.0 - 10.0
     */
    public World(int w, int h, double amplitude, double smooth, double drop, double f1, double f2, double f3, double redistribution){
        Noise noise = new Noise(Artifice.level.mapSizeW, Artifice.level.mapSizeH);

        elevation = new double[Artifice.level.mapSizeH][Artifice.level.mapSizeW];

        for(int y = 0; y < elevation.length; y++){
            for (int x = 0; x < elevation[0].length; x++){
                double e =  noise.smoothNoise(x / f1, y / f1)
                         +  noise.smoothNoise(x / f2, y / f2)
                         +  noise.smoothNoise(x / f3, y / f3);
                e = Math.pow(e, redistribution);

                //Manhattan Distance
                double d = 2 * Math.max(Math.abs(x), Math.abs(y));

                elevation[y][x] = (e + amplitude) * (1 - smooth * Math.pow(d, drop));
            }
        }
    }

    public double[][] getElevation(){
        return elevation;
    }

    public int getBiome(double e){
        if(e < 0.1) return Terrain.SWATER_1;
        else if(e < 0.12) return Terrain.
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
                watermap[i] = Terrain.SWATER_1;
            }
        }
    }

    public static void buildLiquidBed(int[] map){

        final int[] BORDERSET = {Terrain.TOP_BED, Terrain.LFT_BED, Terrain.BOT_BED, Terrain.RND_CORNER_BED, Terrain.RND_CORNER_BED_B};


        for(int i = 0; i < map.length; i += BED_PATTERN_FREQ){
            if(map[i] == Terrain.SOLID_BED){
                if(Magic.randRange(0, 100) < 30){
                    Painter.setCell(i);
                    int[] queueflips = Painter.fillSelectiveBorderRect(map, Terrain.LSOLID_BED, BORDERSET, Terrain.SOLID_BED, Magic.randRange(2, 5), Magic.randRange(2, 5));
                    for(int x = 0; x < queueflips.length; x++){
                        GameScene.scene.tilemap.updateFlipData(queueflips[x], true);
                    }
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
                   map[c] = Terrain.SWATER_1;
                }  else if(!passable[c + Artifice.level.SURROUNDING_CELLS[1]] && !passable[c + Artifice.level.SURROUNDING_CELLS[3]]
                        && passable[c + Artifice.level.SURROUNDING_CELLS[4]]) {

                    map[c] = Terrain.CGRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c, false);
                    watermap[c] = Terrain.SWATER_2;
                    GameScene.scene.watermap.updateFlipData(c, false);

                } else if(!passable[c + Artifice.level.SURROUNDING_CELLS[1]] && !passable[c + Artifice.level.SURROUNDING_CELLS[4]]
                        && passable[c + Artifice.level.SURROUNDING_CELLS[3]]){

                    map[c] = Terrain.CGRASS_3;
                    GameScene.scene.tilemap.updateFlipData(c, true);
                    watermap[c] = Terrain.SWATER_2;
                    GameScene.scene.watermap.updateFlipData(c, true);

                }  else if(!passable[c + Artifice.level.SURROUNDING_CELLS[1]] && passable[c + Artifice.level.SURROUNDING_CELLS[6]]) {

                    map[c] = Terrain.EGRASS_3;
                    watermap[c] = Terrain.SWATER_3;
                    if (Magic.randRange(0, 100) < 50) {
                        GameScene.scene.tilemap.updateFlipData(c, true);
                    }
                }
            }
        }
    }

    public static void addWorldLayers(int[] map, int[] maplayer){
        final int[] BORDERSETLIGHT = {Terrain.TOPGRASS_2, Terrain.LFTGRASS_2, Terrain.BOTGRASS_2, Terrain.CORNERGRASS_2, Terrain.CORNERGRASS_2_B};
        final int[] BORDERSETDARK = {Terrain.TOPGRASS_1, Terrain.LFTGRASS_1, Terrain.BOTGRASS_1, Terrain.CORNERGRASS_1, Terrain.CORNERGRASS_1_B};

        for(int i = 0; i < map.length; i += 5){
            if(map[i] == Terrain.SGRASS_3){
                if(Magic.randRange(0, 100) < 30){
                    Painter.setCell(i);
                    int[] queueflips;
                    if(Magic.randRange(0, 100) < 50) {
                        queueflips = Painter.fillSelectiveBorderRect(maplayer, Terrain.SGRASS_2, BORDERSETLIGHT, Terrain.SGRASS_3, Magic.randRange(2, 5), Magic.randRange(2, 5));
                    } else {
                        queueflips = Painter.fillSelectiveBorderRect(maplayer, Terrain.SGRASS_1, BORDERSETDARK, Terrain.SGRASS_3, Magic.randRange(2, 5), Magic.randRange(2, 5));
                    }
                    for(int x = 0; x < queueflips.length; x++){
                        GameScene.scene.tilemap.updateFlipData(queueflips[x], true);
                    }
                }
            }
        }
    }

    public static void buildDecoration(int[] map, int noise, int smooth){

    }
}
