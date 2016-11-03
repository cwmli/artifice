package com.underwaterotter.artifice.world.generation;

import android.util.Log;

import com.underwaterotter.artifice.Artifice;

public class GenerationDebug {

    private GenerationDebug(){}

    public static void ShowMapData(int[] map){
        Level lv = Artifice.getLevel();
        StringBuilder sb = new StringBuilder();

        for(int y = 0; y < lv.mapHeight; y++){
            for(int x = 0; x < lv.mapWidth; x++){
                sb.append(map[y * lv.mapWidth + x]);
            }
            sb.append("\n");
        }

        Log.d("MAP_DATA", sb.toString());
    }
}
