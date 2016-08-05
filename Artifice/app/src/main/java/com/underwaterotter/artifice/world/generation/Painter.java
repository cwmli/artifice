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
            cell += Artifice.getLevel().mapSizeW;
        }
    }

    public static int[] fillBorderRect(int[] map, int fill, int[] border, int width, int height){
        int[] flipdata = new int[height];
        for (int cy = 0; cy < height; cy++) {
            for(int cx = 0; cx < width && cell + cy * Artifice.getLevel().mapSizeW + cx < map.length; cx++) {
                if ((cy == 0 && cx == 0) || (cy == height - 1 && cx == 0)) {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[3];
                } else if ((cy == 0 && cx == width - 1) ||
                        (cy == height - 1 && cx == width - 1)) {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[3];
                    flipdata[cy] = cell + cy * Artifice.getLevel().mapSizeW + cx;
                } else if (cy == 0) {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[0];
                } else if (cy == height - 1) {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[2];
                } else if (cx == 0) {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[1];
                } else if (cx == width - 1) {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[1];
                    flipdata[cy] = cell + cy * Artifice.getLevel().mapSizeW + cx;
                } else {
                    map[cell + cy * Artifice.getLevel().mapSizeW + cx] = fill;
                }
            }
        }

        return flipdata;
    }

    //border should be filled as 0 - top, 1 - left/right, 2 - bottom, 3 - corner, 4-corner bot
    public static int[] fillSelectiveBorderRect(int[] map, int fill, int[] border,
                                       int swap, int width, int height) {
        int[] flipdata = new int[height];
        for (int cy = 0; cy < height; cy++) {
            for(int cx = 0; cx < width && cell + cy * Artifice.getLevel().mapSizeW + cx < map.length; cx++){
                if (map[cell + cy * Artifice.getLevel().mapSizeW + cx] == swap) {
                    if (cy == 0 && cx == 0) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[3];
                    } else if (cy == height - 1 && cx == 0) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[4];
                    } else if (cy == 0 && cx == width - 1) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[3];
                        flipdata[cy] = cell + cy * Artifice.getLevel().mapSizeW + cx;
                    } else if (cy == height - 1 && cx == width - 1) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[4];
                        flipdata[cy] = cell + cy * Artifice.getLevel().mapSizeW + cx;
                    } else if (cy == 0) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[0];
                    } else if (cy == height - 1) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[2];
                    } else if (cx == 0) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[1];
                    } else if (cx == width - 1) {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = border[1];
                        flipdata[cy] = cell + cy * Artifice.getLevel().mapSizeW + cx;
                    } else {
                        map[cell + cy * Artifice.getLevel().mapSizeW + cx] = fill;
                    }
                }
            }
        }

        return flipdata;
    }
}
