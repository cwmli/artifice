package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;

import java.util.Arrays;

public class Painter {

    private static int VOID_CELL = -1;
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
        int levelWidth = Artifice.level.mapSize_W;
        for(int cy = 0; cy < height; cy++){
            for(int cx = cell + (cy * levelWidth); cx < cell + (cy * levelWidth) + width; cx++){
               map[cell] = terrain;
            }
        }
    }

    public static void fillPolygon(int[] map, int[] points, int terrain){
        double levelWidth = Artifice.level.mapSize_W;
        int[] polygon = new int[Artifice.level.mapLength];
        Arrays.fill(polygon, VOID_CELL);

        for(int i = 0; i < points.length; i++){
            polygon[points[i]] = terrain;
            //connect with last point
            if(i > 0){
                //m = (y2 - y1) / (x2 - x1)
                int dy = (int)Math.floor((double)points[i] / levelWidth - (double)points[i - 1] / levelWidth);
                int dx = (int)Math.floor((double)points[i] % levelWidth - (double)points[i - 1] % levelWidth);
                int m = dy / dx;

                //start and endpoints are already set
                int distance = (int)Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2)) - 2;
                //connect the two points
                for(int d = 1; d <= distance; d++){
                    //polygon[1st_point + row_number + slope_offset]
                    polygon[i - 1 + (d * (int)levelWidth) + (int)Math.ceil(d / m)] = terrain;
                }
            }
        }

        //fill
        boolean fillOn = false;
        for(int i = 0; i < polygon.length; i++){
            //find first point
            if(polygon[i] != VOID_CELL) {
                fillOn = true;
            }

            while(fillOn){
                //test second point
                if(polygon[i] == terrain){
                    fillOn = false;
                    i++;
                    break;
                }

                polygon[i] = terrain;
                i++;
            }
        }

        overwriteMap(map, polygon);
    }

    //uses cell as center point
    public static void fillCircle(int[] map, int terrain, int radius){
        int levelWidth = Artifice.level.mapSize_W;
        int[] fillcells = new int[Artifice.level.mapLength];
        Arrays.fill(fillcells, VOID_CELL);

        //build perimeter
        for(int i = 0; i < 360; i++) {
            int x = (int)(radius * Math.cos(Math.toRadians(i)));
            int y = (int)(radius * Math.sin(Math.toRadians(i)));
            fillcells[cell + x + y * levelWidth] = terrain;
        }

        //fill
        boolean fillOn = false;
        for(int i = 0; i < fillcells.length; i++){
            //find first point
            if(fillcells[i] != VOID_CELL) {
                fillOn = true;
            }

            while(fillOn){
                //test second point
                if(fillcells[i] == terrain){
                    fillOn = false;
                    i++;
                    break;
                }

                fillcells[i] = terrain;
                i++;
            }
        }

        overwriteMap(map, fillcells);
    }

    private static void overwriteMap(int[] base, int[] layer){
        for(int i = 0; i < Artifice.level.mapLength; i++){
            if(layer[i] != VOID_CELL){
                base[i] = layer[i];
            }
        }
    }
}
