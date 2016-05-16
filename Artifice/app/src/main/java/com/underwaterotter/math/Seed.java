package com.underwaterotter.math;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Seed version of Rand.class
 */
public class Seed {

    private Random r;

    public Seed(long seed){
        r = new Random(seed);
    }

    //inclusive
    public int range(int min, int max){
        return r.nextInt((max - min) + 1) + min;
    }

    public float range(float min, float max){
        return min + r.nextFloat() * (max - min);
    }

    public long range(long min, long max) {
        return min + r.nextLong() * (max - min);
    }

    public boolean bool(){
        return r.nextBoolean();
    }

    public List<?> shuffle(List<?> list){
        Collections.shuffle(list, r);
        return list;
    }

    public int listIndex(List<?> list){
        return r.nextInt(list.size());
    }
}
