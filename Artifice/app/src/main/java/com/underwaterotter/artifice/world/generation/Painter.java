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
            cell += Math.sqrt(map.length) * cy;
        }
    }
}
