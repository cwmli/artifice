package com.underwaterotter.artifice;

import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.Tilemap;
import com.underwaterotter.math.Vector3;

import java.util.Arrays;

public class WorldTilemap extends Tilemap {

    public static final int CELL_SIZE_W = 16;
    public static final int CELL_SIZE_H = 16;

    private static int[] oldmap;

    public WorldTilemap(){
        super(Artifice.level.tiles(), CELL_SIZE_W, CELL_SIZE_H);
        oldmap = Artifice.level.map.clone();
        readMapData(Artifice.level.map, Artifice.level.mapSizeW);
    }

    public void update(){
        if(!Arrays.equals(Artifice.level.map, oldmap)){
            readMapData(Artifice.level.map, Artifice.level.mapSizeW);
            oldmap = Artifice.level.map.clone();
        }
    }

    //draw a "lightened" colored tile texture over the existing tilemap
    public void explore(int cellPos){
        Image whiteOverlay = new Image();
        whiteOverlay.position(cellToWorld(cellPos));
        whiteOverlay.tint(0xff000000, 0.5f);

        parent.add(whiteOverlay);
    }

    public Vector3 cellToWorld(int cellPos){
        int row = (int)Math.ceil(cellPos / mapCellsW);
        //offset 1 for index at 0
        int col = row * mapCellsW - cellPos - 1;


        //center of cell
        return new Vector3(col * cellW + (cellW / 2), row * cellH + (cellH / 2), 0);
    }

    public int worldToCell(Vector3 wPos){
        int col = Math.round(wPos.x / cellW);
        int row = Math.round(wPos.y / cellH);

        return row * mapCellsW + col;
    }
}
