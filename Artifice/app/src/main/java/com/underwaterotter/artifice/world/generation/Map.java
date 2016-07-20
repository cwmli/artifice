package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.world.Terrain;

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
    //------------------------------------
    private Noise hmapnoise;
    private Noise vmapnoise;

    private double[][] heightmap;
    private double[][] moisturemap;

    private int xsize, ysize;

    double amp;           //amplitude = 0.0 - 1.0
    double rise;          //rise = 0.0 - 2.0
    double drop;          //drop = 0.0 - 10.0
    double f1, f2, f3;    //frequencies
    double eo1, eo2, eo3; //elevation octaves
    int smooth;        //average sample size
    double redis;         //redistribution

    public Map(int w, int h, long seed,
               double amp, double rise, double drop,
               double f1, double f2, double f3,
               double eo1, double eo2, double eo3,
               int smooth, double redis){
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

    public void build(){
        for(int y = 0; y < ysize; y++){
            for (int x = 0; x < xsize; x++){
                double e = eo1 * hmapnoise.smoothNoise(x / f1, y / f1)
                         + eo2 * hmapnoise.smoothNoise(x / f2, y / f2)
                         + eo3 * hmapnoise.smoothNoise(x / f3, y / f3);
                e /= (eo1 + eo2 + eo3);
                e = Math.pow(e, redis);

                //Manhattan Distance
                double d = 2 * Math.max(Math.abs(x / xsize - 0.5),
                        Math.abs(y / ysize - 0.5));

                heightmap[y][x] = (e + amp) * (1 - rise * Math.pow(d, drop));
            }
        }

        for(int y = 0; y < ysize; y++){
            for (int x = 0; x < xsize; x++){
                moisturemap[y][x] = vmapnoise.turbulence(x, y, 64); //64 is size of texture 64x64
            }
        }

        smoothMap(heightmap, smooth);
    }

    public double[][] getHeightmap(){
        return heightmap;
    }

    public double[][] getMoisturemap(){
        return moisturemap;
    }

    public int getHeight(double e){
        return 0;
    }

    public int getBiome(double e){
        //take data from moisture and elevation
        if(e < 0.1) return Terrain.SWATER_1;
        //do rest of biomes here
        return Terrain.EMPTY;
    }

    public int getVegetation(double m){
        if(m < 0.1) return Terrain.BUSH;
        //do rest of vegetation here
        return Terrain.EMPTY;
    }

    public void setFrequencies(double f1, double f2, double f3){
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;

        build();
    }

    public void setElevationOctaves(double eo1, double eo2, double eo3){
        this.eo1 = eo1;
        this.eo2 = eo2;
        this.eo3 = eo3;

        build();
    }

    public void setAmp(double amp){
        this.amp = amp;

        build();
    }

    public void setRise(double rise){
        this.rise = rise;

        build();
    }

    public void setDrop(double drop){
        this.drop = drop;

        build(); //cache results for faster rebuilding???
    }

    public void setRedistribution(double redis){
        this.redis = redis;

        build();
    }

    public void setSmooth(int smooth){
        this.smooth = smooth;

        build();
    }

    public int[] generateDisplayMap(int w, int h){
        int[] map = new int[w * h];
        int[] shiftY = new int[w];
        for(int y = h - 2; y > 0; y--){
            for(int x = w - 1; x > 0; x--){
                int h1 = getHeight(heightmap[y][x]);
                int h2 = getHeight(heightmap[y + 1][x]);
                if(h1 == 0 || h2 == 0)
                    continue;

                if(h1 - h2 > 0) {
                    shiftY[x] = h1 - h2;
                    map[(y - shiftY[x]) * w + x] = getBiome(heightmap[y][x]);
                    //fill intermediate height blocks
                } else if (h1 - h2 == 0)
                    map[y * w + x] = getBiome(heightmap[y][x]);
            }
        }

        return map;
    }

    private void smoothMap(double[][] map, int sampleSize){
        double averages = 0;
        for(int y = 0; y < ysize; y++){
            for(int x = 0; x < xsize; x++){
                for(int iy = 0; iy < sampleSize; iy++){
                    for(int ix = 0; ix < sampleSize; ix++){
                        averages += map[y + iy][x + ix];
                    }
                }
                map[y][x] = averages / (sampleSize * sampleSize);
                averages = 0;
            }
        }
    }
}
