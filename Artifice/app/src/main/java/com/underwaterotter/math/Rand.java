package com.underwaterotter.math;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Random math
 */

public class Rand {

    public static final Random rand = new Random();

    //inclusive
    public static int range(int min, int max){
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float range(float min, float max){
        return min + rand.nextFloat() * (max - min);
    }

    public static long range(long min, long max) {
        return min + rand.nextLong() * (max - min);
    }

    public static boolean bool(){
        return rand.nextBoolean();
    }

    public static List<?> shuffle(List<?> list){
        Collections.shuffle(list, rand);
        return list;
    }

    public static int listIndex(List<?> list){
        return rand.nextInt(list.size());
    }
}
