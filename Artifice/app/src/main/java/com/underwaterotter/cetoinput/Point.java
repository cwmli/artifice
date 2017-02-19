package com.underwaterotter.cetoinput;

import android.view.MotionEvent;

import com.underwaterotter.math.Vector2;

/**
 * Created by Calvin on 16/02/2017.
 */

public class Point {

    public Vector2 startPos;
    public Vector2 endPos;
    public boolean isDown;

    Point(MotionEvent e, int activeIndex){
        startPos = new Vector2(e.getX(activeIndex), e.getY(activeIndex));
        endPos = new Vector2(e.getX(activeIndex), e.getY(activeIndex));;
        isDown = true;
    }

    public void update(MotionEvent e, int activeIndex){
        endPos.set(e.getX(activeIndex), e.getY(activeIndex));
    }

    Point release(){
        isDown = false;
        return this;
    }
}
