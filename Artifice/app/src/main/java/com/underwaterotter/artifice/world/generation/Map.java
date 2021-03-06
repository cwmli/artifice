package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Terrain;

import java.util.Arrays;

public class Map {

    //-------DEFAULT CONSTANTS-----------
    public static final double F1 = 4.0;
    public static final double F2 = 2.0;
    public static final double F3 = 1.0;
    //Elevation octaves
    public static final double EOCT1 = 1.0;
    public static final double EOCT2 = 0.5;
    public static final double EOCT3 = 0.25;

    public static final double AMP = 0.15;
    public static final double RISE = 1.30;
    public static final double DROP = 2.60;

    public static final double REDIS = 3.65;
    public static final int SMOOTH = 5;

    private static final double INTERMEDIATE_TILE = 100d;

    //------------------------------------
    private enum TileType {
        TR_CORNER, RIGHT, BR_CORNER,
        BOTTOM, SOLID, TOP,
        TL_CORNER, LEFT, BL_CORNER,
        L_AND_R, T_L_AND_R, B_L_AND_R
    }

    private Noise hmapnoise;
    private Noise vmapnoise;

    private double[][] heightmap;
    private double[][] moisturemap;

    private int[] mapdata;
    private int[] watermap;
    private int[] heightdata;

    private int xsize, ysize;

    private double amp;           //amplitude = 0.0 - 1.0d
    private double rise;          //rise = 0.0 - 2.0d
    private double drop;          //drop = 0.0 - 10.0d
    private double f1, f2, f3;    //frequencies
    private double eo1, eo2, eo3; //elevation octaves
    private int smooth;           //average sample size
    private double redis;         //redistribution

    public Map(int w, int h, long seed,
               double amp, double rise, double drop,
               double f1, double f2, double f3,
               double eo1, double eo2, double eo3,
               int smooth, double redis) {
        xsize = w;
        ysize = h;

        hmapnoise = new Noise(w, h, seed);
        vmapnoise = new Noise(w, h, seed);

        heightmap = new double[h][w];
        moisturemap = new double[h][w];

        this.amp = amp;
        this.rise = rise;
        this.drop = drop;

        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        this.eo1 = eo1;
        this.eo2 = eo2;
        this.eo3 = eo3;

        this.smooth = smooth;
        this.redis = redis;
    }

    public void build() {
        mapdata = new int[xsize * (ysize + Level.SAFE_OFFSET)];
        Arrays.fill(mapdata, Terrain.SOLID_BED);

        watermap = new int[xsize * (ysize + Level.SAFE_OFFSET)];
        Arrays.fill(watermap, Terrain.SWATER_1);

        heightdata = new int[xsize * (ysize + Level.SAFE_OFFSET)];

        genHeightmap();
        normalizeHeightmap();
        genDisplayMap();
        cleanWaterTiles();
        cleanGrassTiles();
        GenerationDebug.ShowMapData(heightdata);
        GenerationDebug.ShowMapData(mapdata);
    }

    public int[][] getDisplayMaps() {
        return new int[][]{mapdata, watermap, heightdata};
    }

    public double[][] getHeightmap() {
        return heightmap;
    }

    public double[][] getMoisturemap() {
        return moisturemap;
    }

    private int getHeight(double e) {
        if (e < 0.1)
            return 0;
        else if (e < 0.2)
            return 1;
        else if (e < 0.3)
            return 2;
        else if (e < 0.4)
            return 3;
        else if (e < 0.5)
            return 4;
        else
            return 5;
    }

    private int getTile(double e) {
        //take data from moisture and elevation
        if (e < 0.1)
            return Terrain.SWATER_1;

        //do rest of biomes here
        return Terrain.EMPTY;
    }

    public int getVegetation(double m) {
        if (m < 0.1) return Terrain.BUSH;
        //do rest of vegetation here
        return Terrain.EMPTY;
    }

    public void setFrequencies(double f1, double f2, double f3) {
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        build();
    }

    public void setElevationOctaves(double eo1, double eo2, double eo3) {
        this.eo1 = eo1;
        this.eo2 = eo2;
        this.eo3 = eo3;

        build();
    }

    public void setAmp(double amp) {
        this.amp = amp;

        build();
    }

    public void setRise(double rise) {
        this.rise = rise;

        build();
    }

    public void setDrop(double drop) {
        this.drop = drop;

        build(); //cache results for faster rebuilding???
    }

    public void setRedistribution(double redis) {
        this.redis = redis;

        build();
    }

    public void setSmooth(int smooth) {
        this.smooth = smooth;

        build();
    }

    private void genHeightmap() {
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                double e = eo1 * hmapnoise.smoothNoise(x / f1, y / f1)
                         + eo2 * hmapnoise.smoothNoise(x / f2, y / f2)
                         + eo3 * hmapnoise.smoothNoise(x / f3, y / f3);
                e /= (eo1 + eo2 + eo3);
                e = Math.pow(e, redis);

                //Manhattan Distance
                double d = 2 * Math.max(Math.abs((double) x / xsize - 0.5),
                        Math.abs((double) y / ysize - 0.5));

                heightmap[y][x] = (e + amp) * (1 - rise * Math.pow(d, drop));
            }
        }

        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                moisturemap[y][x] = vmapnoise.turbulence(x, y, 64); //64 is size of texture 64x64
            }
        }
    }

    private void normalizeHeightmap() {
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                heightdata[(y * xsize) + x] = getHeight(heightmap[y][x]);
            }
        }
    }

    private void genDisplayMap() {
        for (int y = ysize - 1; y >= 0; y--) {
            for (int x = xsize - 1; x >= 0; x--) {
                int h1 = heightdata[(y * xsize) + x];
                int h2 = heightdata[((y + 1) * xsize) + x];

                if (h1 == 0) {
                    watermap[y * xsize + x] = Terrain.SWATER_1;
                    mapdata[y * xsize + x] = Terrain.SOLID_BED;
                } else if (h1 > h2 && h1 > 1) {
                    mapdata[y * xsize + x] = Terrain.TOP_GRASS;
                    watermap[y * xsize + x] = Terrain.EMPTY;
                    mapdata[(y - 1) * xsize + x] = Terrain.SOLID_GRASS;
                    watermap[(y - 1) * xsize + x] = Terrain.EMPTY;
                    offsetHeightData(x, y);
                } else if (h1 == h2 && h1 > 1){
                    mapdata[y * xsize + x] = Terrain.SOLID_GRASS;
                    watermap[y * xsize + x] = Terrain.EMPTY;
                } else {
                    mapdata[y * xsize + x] = Terrain.SOLID_GRASS;
                    watermap[y * xsize + x] = Terrain.EMPTY;
                }
            }
        }
    }

    private void cleanWaterTiles() {
        Level lv = Artifice.getLevel();
        for (int i = 0; i < watermap.length; i++) {
            //clean tiles that are adjacent to water tiles
            if (watermap[i] == Terrain.EMPTY && heightdata[i] == 1) {
                TileType tiletype = checkTile(i);
                if (tiletype == TileType.BR_CORNER ||
                        tiletype == TileType.BL_CORNER) {
                    mapdata[i] = Terrain.CONV_GRASS;
                    mapdata[i + lv.s_cells[1]] = Terrain.DIAG_GRASS;

                    watermap[i] = Terrain.DWATER_1;
                    watermap[i + lv.s_cells[1]] = Terrain.EMPTY;

                    if (tiletype == TileType.BR_CORNER) {
                        lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        lv.updateFlipData(i + lv.s_cells[1], false, Level.MapType.TILEMAP);
                        lv.updateFlipData(i, false, Level.MapType.WATERMAP);
                    } else {
                        lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                        lv.updateFlipData(i + lv.s_cells[1], true, Level.MapType.TILEMAP);
                        lv.updateFlipData(i, true, Level.MapType.WATERMAP);
                    }
                } else if (tiletype == TileType.TR_CORNER ||
                        tiletype == TileType.TL_CORNER) {
                    if (mapdata[i + lv.s_cells[6]] != Terrain.SOLID_GRASS) {
                        mapdata[i] = Terrain.TOP_GRASS;
                        watermap[i + lv.s_cells[6]] = Terrain.TWATER_1;
                    } else {
                        mapdata[i] = Terrain.CORNER_GRASS;
                        watermap[i] = Terrain.SWATER_2;
                    }

                    if (tiletype == TileType.TR_CORNER) {
                        lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        lv.updateFlipData(i + lv.s_cells[1], false, Level.MapType.TILEMAP);
                        lv.updateFlipData(i, false, Level.MapType.WATERMAP);
                    } else {
                        lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                        lv.updateFlipData(i + lv.s_cells[1], true, Level.MapType.TILEMAP);
                        lv.updateFlipData(i, true, Level.MapType.WATERMAP);
                    }
                } else if (tiletype == TileType.BOTTOM ||
                        tiletype == TileType.B_L_AND_R) {
                    mapdata[i] = Terrain.TOP_GRASS;
                    watermap[i + lv.s_cells[6]] = Terrain.TWATER_1;
                } else if (tiletype == TileType.TOP ||
                        tiletype == TileType.T_L_AND_R) {
                    if (mapdata[i + lv.s_cells[6]] == Terrain.SWATER_1) {
                        mapdata[i] = Terrain.TOP_GRASS;
                    } else {
                        mapdata[i] = Terrain.EDGE_GRASS;
                        watermap[i] = Terrain.SWATER_3;
                    }
                }
            }
        }
    }

    private void cleanGrassTiles() {
        Level lv = Artifice.getLevel();
        //clean tiles based on height differences
        for (int e = 2; e < 6; e++) {
            for (int i = 1 + lv.mapWidth; i < heightdata.length - lv.mapWidth - 1; i++) {
                if (heightdata[i] == e) {
                    if (mapdata[i] == Terrain.TOP_GRASS) {
                        TileType tiletype = checkTile(i);

                        if (tiletype == TileType.BR_CORNER ||
                                tiletype == TileType.BL_CORNER) {
                            mapdata[i] = Terrain.DIAG_GRASS_H;
                            mapdata[i + lv.s_cells[1]] = Terrain.DIAG_GRASS;

                            if (tiletype == TileType.BR_CORNER) {
                                lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                                lv.updateFlipData(i + lv.s_cells[1], false, Level.MapType.TILEMAP);
                            } else {
                                lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                                lv.updateFlipData(i + lv.s_cells[1], true, Level.MapType.TILEMAP);
                            }
                        } else if (tiletype == TileType.B_L_AND_R) {
                            mapdata[i] = Terrain.B_TOP_GRASS_H;
                            lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        } else if (tiletype == TileType.RIGHT) {
                            mapdata[i] = Terrain.R_TOP_GRASS_H;
                            lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        } else if (tiletype== TileType.LEFT) {
                            mapdata[i] = Terrain.R_TOP_GRASS_H;
                            lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                        }
                    } else if (mapdata[i] == Terrain.SOLID_GRASS) {
                        TileType tiletype = checkTile(i);

                        if (tiletype == TileType.TR_CORNER ||
                                tiletype == TileType.TL_CORNER) {
                            mapdata[i] = Terrain.CORNER_GRASS_H;

                            if (tiletype == TileType.TR_CORNER)
                                lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                            else
                                lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                        } else if (tiletype == TileType.TOP) {
                            mapdata[i] = Terrain.T_EDGE_GRASS_H;
                            lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        } else if (tiletype == TileType.L_AND_R) {
                            mapdata[i] = Terrain.B_EDGE_GRASS_H;
                            lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                        } else if (tiletype == TileType.T_L_AND_R) {
                            mapdata[i] = Terrain.BT_EDGE_GRASS_H;
                            lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        } else if (tiletype == TileType.RIGHT) {
                            mapdata[i] = Terrain.V_EDGE_GRASS_H;
                            lv.updateFlipData(i, false, Level.MapType.TILEMAP);
                        } else if (tiletype== TileType.LEFT) {
                            mapdata[i] = Terrain.V_EDGE_GRASS_H;
                            lv.updateFlipData(i, true, Level.MapType.TILEMAP);
                        }
                    }
                }
            }
        }
    }

    private TileType checkTile(int cell) {
        boolean[] sameHeight = checkSimilarHeight(cell);
        boolean[] lrgrHeight = checkLrgrHeight(cell);

        if (lrgrHeight[4] && lrgrHeight[3]) {
            if (!sameHeight[1] && sameHeight[6])
                return TileType.T_L_AND_R;
            else if (!sameHeight[6] && sameHeight[1])
                return TileType.B_L_AND_R;
            else
                return TileType.L_AND_R;
        } else if (lrgrHeight[4]) {
            if (lrgrHeight[1])
                return TileType.TR_CORNER;
            else if (lrgrHeight[6])
                return TileType.BR_CORNER;
            else
                return TileType.RIGHT;
        } else if (lrgrHeight[3]) {
            if (lrgrHeight[1])
                return TileType.TL_CORNER;
            else if (lrgrHeight[6])
                return TileType.BL_CORNER;
            else
                return TileType.LEFT;
        } else if (lrgrHeight[1]) {
            return TileType.TOP;
        } else if (lrgrHeight[6]) {
            return TileType.BOTTOM;
        } else {
            return TileType.SOLID;
        }
    }

    private boolean[] checkSimilarHeight(int cell) {
        Level lv = Artifice.getLevel();

        boolean[] heightSim = new boolean[lv.s_cells.length];

        for (int i = 0; i < lv.s_cells.length; i++) {
            if (heightdata[cell] == heightdata[cell + lv.s_cells[i]])
                heightSim[i] = true;
        }

        return heightSim;
    }

    private boolean[] checkLrgrHeight(int cell) {
        Level lv = Artifice.getLevel();

        boolean[] heightSim = new boolean[lv.s_cells.length];

        for (int i = 0; i < lv.s_cells.length; i++) {
            if (heightdata[cell] > heightdata[cell + lv.s_cells[i]])
                heightSim[i] = true;
        }

        return heightSim;
    }

    private void offsetHeightData(int x, int y){
        if(heightdata[(y - 1) * xsize + x] == heightdata[y * xsize + x]) {
            offsetHeightData(x, y - 1);
        } else if (heightdata[(y - 1) * xsize + x] < heightdata[y * xsize + x]){
            heightdata[(y - 1) * xsize + x] = heightdata[y * xsize + x];
        } else {
            heightdata[(y - 2) * xsize + x] = heightdata[(y - 1) * xsize + x];
            heightdata[(y - 1) * xsize + x] = heightdata[y * xsize + x];
            offsetHeightData(x, y - 3);
        }
    }
}
