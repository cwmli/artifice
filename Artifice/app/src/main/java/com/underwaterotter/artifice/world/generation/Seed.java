package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.math.Magic;

import java.util.ArrayList;

public class Seed{

    //defaults
    public static int passes = 7;
    public static int noise = 1;
    public static int jitter_chance = 0;
    public static int grass_chance = 100;

    public static final int MIN_SIZE = 16;
    public static final int MIN_SIDE_LENGTH = 4; //sqrt(MIN_SIZE)

    private Seed(){}

    public static ArrayList<Integer> initBase(){

        final int g_scale = Math.round(100 / passes);
        final int j_scale = Math.round(passes / g_scale);

        ArrayList<Integer> cellList = new ArrayList<>();

        boolean[] map = new boolean[MIN_SIZE];
        //odd center is (map.length - 1) / 2
        //even center is (int)Math.floor((map.length) / 2) + 2

        for(int pass = passes - 1; pass >= 0; pass--){
            grass_chance = Magic.randRange(100 - (g_scale * pass), 100);
            jitter_chance = Magic.randRange(100 - (j_scale * pass), 100);
            spiralTopRight( map, 0, 0, MIN_SIDE_LENGTH - 1, MIN_SIDE_LENGTH - 1);
        }

        for(int i = 0; i < map.length; i++){
            if(map[i] == true){
                cellList.add(i);
            }
        }

        return cellList;
    }

    private static void spiralTopRight(boolean[] map, int x, int y, int x_max, int y_max){

        for (int i = x; i < x_max; i++) {
            int random = Magic.randRange(0, 100);
            int jitter = random < jitter_chance ? i + noise : i;
            map[(y * MIN_SIDE_LENGTH) + i] =
                    random < grass_chance ? true : false;
        }

        for(int i = y + 1; i < y_max; i++){
            int random = Magic.randRange(0, 100);
            map[(i * MIN_SIDE_LENGTH) + x_max] =
                    random < grass_chance ? true : false;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x, y + 1, x_max - 1, y_max);
        }
    }

    private static void spiralBottomRight(boolean[] map, int x, int y, int x_max, int y_max){

        for(int i = x_max; i > x; i--){
            int random = Magic.randRange(0, 100);
            map[(y * MIN_SIDE_LENGTH) + i] =
                    random < grass_chance ? true : false;
        }

        for(int i = y_max - 1; i > y; i--){
            int random = Magic.randRange(0, 100);
            map[(i * MIN_SIDE_LENGTH) + x] =
                    random < grass_chance ? true : false;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x + 1, y, x_max - 1, y_max - 1);
        }
    }
}
