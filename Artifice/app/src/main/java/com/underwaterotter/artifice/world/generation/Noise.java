package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.math.Magic;

public class Noise {

    private float[][] noise;
    private int xmax;
    private int ymax;

    public Noise(int xmax, int ymax){
        this.xmax = xmax;
        this.ymax = ymax;
        noise = new float[ymax][xmax];

        for(int y = 0; y < ymax; y++){
            for(int x = 0; x < xmax; x++){
                noise[y][x] = Magic.randRange(0.0f, 1.0f);
            }
        }
    }

    public double smoothNoise(double x, double y){
        //get fractional part of x and y
        double fractX = x - (int)x;
        double fractY = y - (int)y;

        //wrap around
        int x1 = ((int)x + xmax) % ymax;
        int y1 = ((int)y + xmax) % ymax;

        //neighbor values
        int x2 = (x1 + xmax - 1) % xmax;
        int y2 = (y1 + xmax - 1) % ymax;

        //smooth the noise with bilinear interpolation
        double value = 0.0;
        value += fractX       * fractY       * noise[y1][x1];
        value += (1 - fractX) * fractY       * noise[y1][x2];
        value += fractX       * (1 - fractY) * noise[y2][x1];
        value += (1 - fractX) * (1 - fractY) * noise[y2][x2];

        return value;
    }

    public double turbulence(float x, float y, float size){
        double value = 0.0, initialSize = size;

        while(size >= 1)
        {
            value += smoothNoise(x / size, y / size) * size;
            size /= 2.0;
        }

        return(128.0 * value / initialSize);
    }
}