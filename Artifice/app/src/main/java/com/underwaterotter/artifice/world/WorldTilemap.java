package com.underwaterotter.artifice.world;

import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.Tilemap;
import com.underwaterotter.math.Vector3;

import java.util.Arrays;

public class WorldTilemap extends Tilemap {

    public static final int CELL_SIZE_W = 16;
    public static final int CELL_SIZE_H = 16;

    public static final int INVALID_TILE = -1;

    public enum TILEMAP {
        WATER,
        LAND,
        FOREGROUND
    }

    private Level level;
    private TILEMAP type;

    protected int[] map;
    private int[] oldmap;

    public WorldTilemap(String tiles, Level level, TILEMAP type){
        super(tiles, CELL_SIZE_W, CELL_SIZE_H);

        this.level = level;
        this.type = type;

        oldmap = new int[level.sfLength];
        Arrays.fill(oldmap, INVALID_TILE);

        flipData = new boolean[level.sfLength];
        Arrays.fill(flipData, false);

        readMapData(oldmap, flipData, level.sfMapW);
    }

    @Override
    public void update(){
        map = level.getMapData(type);
        readMapData(map, flipData, level.sfMapW);
        oldmap = map.clone();
    }

    //draw a "lightened" colored tile texture over the existing tilemap
    public void explore(int cellPos){
        Image whiteOverlay = new Image();
        whiteOverlay.position(cellToWorld(cellPos));
        whiteOverlay.tint(0xff000000, 0.5f);

        parent.add(whiteOverlay);
    }

    public void updateFlipData(int index, boolean bool){
        flipData[index] = bool;
    }

    public Vector3 cellToWorld(int cellPos){
        int row = (int)Math.ceil(cellPos / mapCellsW);
        //offset 1 for index at 0
        int col = cellPos & mapCellsW;


        //center of cell
        return new Vector3(col * cellW + (cellW / 2), row * cellH + (cellH / 2), 0);
    }

    public int worldToCell(Vector3 wPos){
       return worldToCell((int)wPos.x, (int)wPos.y);
    }

    public int worldToCell(int x, int y){
        int col = Math.round(x / cellW);
        int row = Math.round(y / cellH);

        return row * mapCellsW + col;
    }
}
