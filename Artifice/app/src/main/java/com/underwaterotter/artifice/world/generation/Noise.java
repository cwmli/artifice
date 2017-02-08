package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.math.*;

public class Noise {

    private float[][] noise;
    private int xmax;
    private int ymax;

    private Seed seed;

    public Noise(int xmax, int ymax, long seedValue){
        this.xmax = xmax;
        this.ymax = ymax;
        noise = new float[ymax][xmax];

        seed = new Seed(seedValue);

        for(int y = 0; y < ymax; y++){
            for(int x = 0; x < xmax; x++){
//                noise[y][x] = seed.range(0.0f, 1.0f);
                float bias = (seed.range(0.0f, 1.0f)) * (((float)ymax - (float)y) / (float)ymax);
                noise[y][x] = bias;
            }
        }
    }

    public double smoothNoise(double x, double y){
        //get fractional part of x and y
        double fractX = x - (int)x;
        double fractY = y - (int)y;

        //wrap around
        int x1 = ((int)x + xmax) % xmax;
        int y1 = ((int)y + ymax) % ymax;

        //neighbor values
        int x2 = (x1 + xmax - 1) % xmax;
        int y2 = (y1 + ymax - 1) % ymax;

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
