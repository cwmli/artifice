package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.math.Magic;

public class Seed{

      //specify a polygon of N vertices
      public static int[] genPoints(int vertices, int lower_limit, int upper_limit){

          int[] points = new int[vertices];

          for(int i = 0; i < vertices; i++){
              int x_jitter = Magic.randRange(lower_limit, upper_limit);
              int y_jitter = Magic.randRange(lower_limit, upper_limit);

              //Grab center cell
              points[i] = Painter.retrieveCell() + x_jitter + (y_jitter * Artifice.level.mapSize_W);
          }

          return sortClockwise(points);
      }

    private static int[] sortClockwise(int[] copy) {

        final int width = Artifice.level.mapSize_W;
        final int[] vertices = copy;


        //sort col in ascending order by insertion
        for (int i = 2; i < vertices.length; i += 2) {
            for (int n = 1; n <= i / 2; n++){
                int x1 = vertices[i - (n - 1)] % width;
                int x2 = vertices[i - (2 * n)] % width; //previous
                if(x1 < x2){
                    int temp = vertices[i - (2 * n)];
                    vertices[i - (2 * n)] = vertices[i - (n - 1)];
                    vertices[i - (n - 1)] = temp;
                } else {
                    break;
                }
            }
        }

        int halfx = (int) Math.floor((vertices[0] + 1 + vertices[vertices.length]) / 2);
        //sort row to lowest point (top to bottom) for right half of polygon
        for (int i = halfx + 1; i < vertices.length; i += 2) {
            for (int n = 1; n <= i / 2; n++){
                int y1 = (int) Math.floor(vertices[i - (n - 1)] / width);
                int y2 = (int) Math.floor(vertices[i - (2 * n)] / width); //previous
                if(y1 < y2){
                    int temp = vertices[i - (2 * n)];
                    vertices[i - (2 * n)] = vertices[i - (n - 1)];
                    vertices[i - (n - 1)] = temp;
                } else {
                    break;
                }
            }
        }

        //sort row from lowest back to highest point (bottom to top) for left half of polygon
        for (int i = 1; i < halfx + 1; i += 2) {
            for (int n = 1; n <= i / 2; n++){
                int y1 = (int) Math.floor(vertices[i - (n - 1)] / width);
                int y2 = (int) Math.floor(vertices[i - (2 * n)] / width); //previous
                if(y1 > y2){
                    int temp = vertices[i - (2 * n)];
                    vertices[i - (2 * n)] = vertices[i - (n - 1)];
                    vertices[i - (n - 1)] = temp;
                } else {
                    break;
                }
            }
        }


        return vertices;
    }
}
