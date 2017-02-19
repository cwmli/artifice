package com.underwaterotter.ceto;

import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.cetoinput.Point;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Listener;

public class TouchBox extends Overlay implements Listener.Trigger<Point> {

    private Point lastPoint = null;

    public TouchBox(float x, float y, float width, float height){
        super(new Vector3(x, y, 0), width, height);
        //bounds are the same size as the overlay
        Motions.motionListeners.add(this);
    }

    @Override
    public void destroy(){
        super.destroy();
        Motions.motionListeners.remove(this);
    }

    public void onCall(Point newPoint){

        if(!isActive()){
            return;
        }
        
        boolean overlap = newPoint != null && screenPointOverlap((int) newPoint.startPos.x, (int) newPoint.startPos.y);

        if(overlap) {

            Motions.motionListeners.matched = true;

            if(newPoint.isDown){

                if(lastPoint == null) {
                    lastPoint = newPoint;
                }

                onDownTouch(newPoint);
            } else {

                onTouchRelease(newPoint);

                if(lastPoint == newPoint){
                    lastPoint = null;

                    onClick(newPoint);
                } else {
                    lastPoint = null;
                }
            }

        } else if(newPoint == null && lastPoint != null) {
            //use touchPoints for drag
            onDrag(lastPoint);
        }
    }

    public void reset(){
        lastPoint = null;
    }

    public void onDownTouch(Point t){}

    public void onTouchRelease(Point t){}

    public void onDrag(Point t){}

    public void onClick(Point t){}
}
