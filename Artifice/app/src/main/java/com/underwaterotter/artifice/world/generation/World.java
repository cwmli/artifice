package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.world.Terrain;

public class World {

    //-------DEFAULT CONSTANTS-----------
    public static final double F1 = 1.0;
    public static final double F2 = 2.0;
    public static final double F3 = 4.0;

    public static final double AMP = 0.05;
    public static final double RISE = 1.30;
    public static final double DROP = 2.60;

    public static final double REDIS = 3.65;
    public static final int SMOOTH = 5;
    //------------------------------------
    private Noise hmapnoise;
    private Noise vmapnoise;

    private double[][] heightmap;
    private double[][] moisturemap;

    double amp;         //amplitude = 0.0 - 1.0
    double rise;        //rise = 0.0 - 2.0
    double drop;        //drop = 0.0 - 10.0
    double f1, f2, f3;  //frequencies
    double smooth;      //average sample size
    double redis;       //redistribution

    public World(int w, int h, long seed, double amp, double rise, double drop,
                 double f1, double f2, double f3, double smooth, double redis){

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

        this.smooth = smooth;
        this.redis = redis;
    }

    public void buildmaps(){
        for(int y = 0; y < heightmap.length; y++){
            for (int x = 0; x < heightmap[0].length; x++){
                double e =  hmapnoise.smoothNoise(x / f1, y / f1)
                        +  hmapnoise.smoothNoise(x / f2, y / f2)
                        +  hmapnoise.smoothNoise(x / f3, y / f3);
                e = Math.pow(e, redis);

                //Manhattan Distance
                double d = 2 * Math.max(Math.abs(x), Math.abs(y));

                heightmap[y][x] = (e + amp) * (1 - rise * Math.pow(d, drop));
            }
        }

        for(int y = 0; y < moisturemap.length; y++){
            for (int x = 0; x < moisturemap[0].length; x++){
                moisturemap[y][x] = vmapnoise.turbulence(x, y, 64);
            }
        }
    }

    public double[][] getHeightmap(){
        return heightmap;
    }

    public double[][] getMoisturemap(){
        return moisturemap;
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
    }

    public void setAmp(double amp){
        this.amp = amp;
    }

    public void setRise(double rise){
        this.rise = rise;
    }

    public void setDrop(double drop){
        this.drop = drop;
    }

    public void setRedistribution(double redis){
        this.redis = redis;
    }

    public void setSmooth(double smooth){
        this.smooth = smooth;
    }

    private void smoothMap(double[][] map, int sampleSize){
        //average out the map
        for(int y = (int)sampleSize / 2; y < Math.floor(map.length / sampleSize); y += sampleSize / 2){
            for(int x = (int)sampleSize / 2; x < Math.floor(map[0].length / sampleSize); x += sampleSize / 2){

            }
        }
    }
}
