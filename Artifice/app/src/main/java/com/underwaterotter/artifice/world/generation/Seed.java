package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.math.Magic;

public class Seed{

      //specify a polygon of N vertices
      public static void genIsland(int[] map, int passes, int jitter){

          final int g_scale = Math.round(100 / passes);
          final int j_scale = Math.round(passes / g_scale);
          //odd center is (map.length - 1) / 2
          //even center is (int)Math.floor((map.length) / 2) + 2

          for(int pass = passes - 1; pass >= 0; pass--){
              int grass_chance = Magic.randRange(100 - (g_scale * pass), 100);
              int jitter_chance = Magic.randRange(100 - (j_scale * pass), 100);

              spiralTopRight(map, 0, 0, Artifice.level.mapSideLength - 1, Artifice.level.mapSideLength - 1, grass_chance);
          }
      }

    private static void spiralTopRight(int[] map, int x, int y, int x_max, int y_max, int chance){

        for (int i = x; i < x_max; i++) {
            int random = Magic.randRange(0, 100);
            map[(y * Artifice.level.mapSideLength) + i] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        for(int i = y + 1; i < y_max; i++){
            int random = Magic.randRange(0, 100);
            map[(i * Artifice.level.mapSideLength) + x_max] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x, y + 1, x_max - 1, y_max, chance);
        }
    }

    private static void spiralBottomRight(int[] map, int x, int y, int x_max, int y_max, int chance){

        for(int i = x_max; i > x; i--){
            int random = Magic.randRange(0, 100);
            map[(y * Artifice.level.mapSideLength) + i] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        for(int i = y_max - 1; i > y; i--){
            int random = Magic.randRange(0, 100);
            map[(i * Artifice.level.mapSideLength) + x] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        if(x_max - x > 0){
            spiralBottomRight(map, x + 1, y, x_max - 1, y_max - 1, chance);
        }
    }
}
