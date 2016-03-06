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

              spiralTopRight(map, 0, 0, Artifice.level.mapSize_W - 1, Artifice.level.mapSize_H - 1, grass_chance);
          }
      }

    private static void spiralTopRight(int[] map, int row, int col, int row_max, int col_max, int chance){

        for (int i = col; i < col_max; i++) {
            int random = Magic.randRange(0, 100);
            map[(row * Artifice.level.mapSize_W) + i] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        for(int i = row + 1; i < row_max; i++){
            int random = Magic.randRange(0, 100);
            map[(i * Artifice.level.mapSize_W) + Artifice.level.mapSize_W - 1] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        if(col_max - col > 0){
            spiralBottomRight(map, col, row + 1, col_max - 1, row_max, chance);
        }
    }

    private static void spiralBottomRight(int[] map, int row, int col, int row_max, int col_max, int chance){

        for(int i = col_max; i > col; i--){
            int random = Magic.randRange(0, 100);
            map[(i * Artifice.level.mapSize_W) + Artifice.level.mapSize_W - 1] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        for(int i = row_max - 1; i > row; i--){
            int random = Magic.randRange(0, 100);
            map[(i * Artifice.level.mapSize_W) + Artifice.level.mapSize_W - 1] =
                    random < chance ? Terrain.GRASS : Terrain.WATER;
        }

        if(col_max - col > 0){
            spiralBottomRight(map, col + 1, row, col_max, row_max - 1, chance);
        }
    }
}
