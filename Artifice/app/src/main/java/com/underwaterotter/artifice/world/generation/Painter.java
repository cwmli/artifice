package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;

import java.util.Arrays;

public class Painter {

    private static final int VOID_CELL = -1;
    private static int cell;

    public static void setCell(int c){
        cell = c;
    }

    public static int retrieveCell(){
        return cell;
    }

    public static void fill(int[] map, int terrain){
       Arrays.fill(map, terrain);
    }

    public static void fillRect(int[] map, int terrain, int width, int height){
        for(int cy = 0; cy < height; cy++){
            Arrays.fill(map, cell, cell + width, terrain);
            cell += Artifice.level.mapSizeW;
        }
    }

    public static void fillBorderRect(int[] map, int fill, int[] border, int width, int height){
        fillBorderRect(map, fill, border, VOID_CELL, width, height);
    }

    //border should be filled as 0 - top, 1 - left, 2 - right, 3 - bottom
    public static void fillBorderRect(int[] map, int fill, int[] border, int swap, int width, int height) {
        for (int cy = 0; cy < height; cy++) {
            for(int cx = 0; cx < width; cx++) {
                if(cy == 0){
                    map[cell + cy * Artifice.level.mapSizeW + cx] = border[0];
                } else if (cy == height - 1){
                    map[cell + cy * Artifice.level.mapSizeW + cx] = border[3];
                } else if (cx == 0){
                    map[cell + cy * Artifice.level.mapSizeW + cx] = border[1];
                } else if (cx == width - 1){
                    map[cell + cy * Artifice.level.mapSizeW + cx] = border[2];
                } else {
                    map[cell + cy * Artifice.level.mapSizeW + cx] = fill;
                }
            }
        }
    }
}
