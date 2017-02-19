package com.underwaterotter.cetoinput;

import android.view.MotionEvent;

import com.underwaterotter.utils.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class Motions {

    public static Listener<Point> motionListeners = new Listener<>();

    public static HashMap<Integer, Point> touchPoints = new HashMap<>();

    public static void processMotionEvents(ArrayList<MotionEvent> events){

        for(MotionEvent e : events){

            int latest_index = e.getActionIndex();
            int ptr_id = e.getPointerId(latest_index);

            switch (e.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    motionListeners.matched = false;

                    touchPoints.put(ptr_id, new Point(e, latest_index));

                    motionListeners.processTrigger(touchPoints.get(ptr_id));

                    break;
                case MotionEvent.ACTION_UP:
                    motionListeners.matched = false;

                    motionListeners.processTrigger(touchPoints.remove(e.getPointerId(0)).release());
                    break;
                case MotionEvent.ACTION_MOVE:
                    motionListeners.matched = false;

                    for(int i = 0; i < e.getPointerCount(); i++){
                        touchPoints.get(e.getPointerId(i)).update(e, i);
                    }

                    motionListeners.processTrigger(null);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    motionListeners.matched = false;

                    touchPoints.put(ptr_id, new Point(e, latest_index));

                    motionListeners.processTrigger(touchPoints.get(ptr_id));
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    motionListeners.matched = false;

                    motionListeners.processTrigger( touchPoints.remove(ptr_id).release());

                    break;
            }

            e.recycle();
        }
    }
}
