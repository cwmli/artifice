package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

public class Seed{
    
    private static final int MIN_SIZE = 16;
    private static final int MIN_SIDE_LENGTH = 4; //sqrt(MIN_SIZE)
    
    private static int jitter_chance, grass_chance;

    public static void genBase(int[] map, int passes, int jitter){

        final int g_scale = Math.round(100 / passes);
        final int j_scale = Math.round(passes / g_scale);
        //odd center is (map.length - 1) / 2
        //even center is (int)Math.floor((map.length) / 2) + 2

        for(int pass = passes - 1; pass >= 0; pass--){
            grass_chance = Magic.randRange(100 - (g_scale * pass), 100);
            jitter_chance = Magic.randRange(100 - (j_scale * pass), 100);
            spiralTopRight(map, 0, 0, MIN_SIDE_LENGTH - 1, MIN_SIDE_LENGTH - 1);
        }
    }

    private static void spiralTopRight(int[] map, int x, int y, int x_max, int y_max){

        for (int i = x; i < x_max; i++) {
            int random = Magic.randRange(0, 100);
            map[(y * MIN_SIDE_LENGTH) + i] =
                    random < grass_chance ? Terrain.GRASS : Terrain.WATER;
        }

        for(int i = y + 1; i < y_max; i++){
            int random = Magic.randRange(0, 100);
            map[(i * MIN_SIDE_LENGTH) + x_max] =
                    random < grass_chance ? Terrain.GRASS : Terrain.WATER;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x, y + 1, x_max - 1, y_max);
        }
    }

    private static void spiralBottomRight(int[] map, int x, int y, int x_max, int y_max){

        for(int i = x_max; i > x; i--){
            int random = Magic.randRange(0, 100);
            map[(y * MIN_SIDE_LENGTH) + i] =
                    random < grass_chance ? Terrain.GRASS : Terrain.WATER;
        }

        for(int i = y_max - 1; i > y; i--){
            int random = Magic.randRange(0, 100);
            map[(i * MIN_SIDE_LENGTH) + x] =
                    random < grass_chance ? Terrain.GRASS : Terrain.WATER;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x + 1, y, x_max - 1, y_max - 1);
        }
    }
}
