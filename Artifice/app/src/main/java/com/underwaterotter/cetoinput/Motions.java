package com.underwaterotter.cetoinput;

import android.view.MotionEvent;

import com.underwaterotter.math.Vector2;
import com.underwaterotter.utils.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class Motions {

    public static Listener<Point> motionListeners = new Listener<Point>();

    public static HashMap<Integer, Point> touchPoints = new HashMap<Integer, Point>();

    public static boolean activeTouch;

    public static void processMotionEvents(ArrayList<MotionEvent> events){

        Point point;

        for(MotionEvent e : events){

            int latest_index = e.getActionIndex();

            switch (e.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    activeTouch = true;
                    motionListeners.matched = false;
                    point = new Point(new Vector2(e.getX(), e.getY()));
                    touchPoints.put(e.getPointerId(0), point);
                    motionListeners.processTrigger(point);
                    break;
                case MotionEvent.ACTION_UP:
                    activeTouch = false;
                    motionListeners.matched = false;
                    motionListeners.processTrigger(touchPoints.remove(e.getPointerId(0)).release());
                    break;
                case MotionEvent.ACTION_MOVE:
                    activeTouch = true;
                    motionListeners.matched = false;
                    for(int i = 0; i < e.getPointerCount(); i++){
                        touchPoints.get(e.getPointerId(i)).update(new Vector2(e.getX(i), e.getY(i)));
                    }
                    motionListeners.processTrigger(null);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    activeTouch = true;
                    motionListeners.matched = false;
                    point = new Point(new Vector2(e.getX(latest_index), e.getY(latest_index)));
                    touchPoints.put(e.getPointerId(latest_index), point);
                    motionListeners.processTrigger(point);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    activeTouch = false;
                    motionListeners.matched = false;
                    motionListeners.processTrigger(touchPoints.remove(e.getPointerId(latest_index)).release());
                    break;
            }

            e.recycle();
        }
    }

    public static class Point {

        public Vector2 startPos;
        public Vector2 endPos;
        public boolean isDown;

        public Point(Vector2 startPos){
            this.startPos = startPos;
            endPos = startPos;
            isDown = true;
        }

        public void update(Vector2 pos){
            endPos.set(pos.x, pos.y);
        }

        public Point release(){
            isDown = false;
            return this;
        }
    }
}
