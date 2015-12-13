package com.underwaterotter.math;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Random math
 */

public class Magic {

    public static final Random rand = new Random();

    //inclusive
    public static int randRange(int min, int max){
        int randValue = rand.nextInt((max - min) + 1) + min;

        return randValue;
    }

    public static float randRange(float min, float max){
        float randValue = min + rand.nextFloat() * (max - min);

        return randValue;
    }

    public static boolean randBool(){
        return rand.nextBoolean();
    }

    public static List<?> shuffle(List<?> list){
        Collections.shuffle(list, rand);
        return list;
    }

    public static int randListIndex(List<?> list){
        int randIndex = rand.nextInt(list.size());
        return randIndex;
    }
}
