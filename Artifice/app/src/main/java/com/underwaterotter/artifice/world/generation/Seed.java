package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Seed{

    //defaults
    public static int passes = 1;
    public static int grass_chance = 0;

    public static final int MIN_SIZE = 100;
    public static final int MIN_SIDE_LENGTH = 10; //sqrt(MIN_SIZE)

    final static int scale = 100 / MIN_SIDE_LENGTH;

    private Seed(){}

    public static int[] initBase(){

        //ArrayList<int[]> grassTiles = new ArrayList<>();

        int[] map = new int[MIN_SIZE];
        //odd center is (map.length - 1) / 2
        //even center is (int)Math.floor((map.length) / 2) + 2

        for(int pass = passes - 1; pass >= 0; pass--){
            spiralTopRight( map, 0, 0, MIN_SIDE_LENGTH, MIN_SIDE_LENGTH);
        }

        /*for(int i = 0; i < map.length; i++){
            if(map[i] == Terrain.SGRASS1){ //is a grass tile
                int[] data = {i % MIN_SIDE_LENGTH, (int)Math.floor(i / MIN_SIDE_LENGTH)};
                grassTiles.add(data);
            }
        }

        return grassTiles;*/
        return map;
    }

    private static void spiralTopRight(int[] map, int x, int y, int x_max, int y_max){

        grass_chance = 100 - (scale * x_max);

        for (int i = x; i < x_max; i++) {
            int random = Magic.randRange(0, 100);
            map[(y * (MIN_SIDE_LENGTH - 1)) + i] = random < grass_chance ? Terrain.SGRASS_1 : Terrain.DEEP_WATER;
        }

        for(int i = y + 1; i < y_max; i++){
            int random = Magic.randRange(0, 100);
            map[(i * (MIN_SIDE_LENGTH - 1)) + x_max] = random < grass_chance ? Terrain.SGRASS_1 : Terrain.DEEP_WATER;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x, y + 1, x_max - 1, y_max);
        }
    }

    private static void spiralBottomRight(int[] map, int x, int y, int x_max, int y_max){

        grass_chance = 100 - (scale * x_max);

        for(int i = x_max - 1; i >= x; i--){
            int random = Magic.randRange(0, 100);
            map[(y * (MIN_SIDE_LENGTH - 1)) + i] = random < grass_chance ? Terrain.SGRASS_1 : Terrain.DEEP_WATER;
        }

        for(int i = y_max - 1; i >= y; i--){
            int random = Magic.randRange(0, 100);
            map[(i * (MIN_SIDE_LENGTH - 1)) + x] = random < grass_chance ? Terrain.SGRASS_1 : Terrain.DEEP_WATER;
        }

        if(x_max - x > 0){
            spiralTopRight(map, x + 1, y, x_max - 1, y_max - 1);
        }
    }
}
