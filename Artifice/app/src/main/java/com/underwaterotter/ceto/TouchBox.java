package com.underwaterotter.ceto;

import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Listener;

public class TouchBox extends Overlay implements Listener.Trigger<Motions.Point> {

    protected Motions.Point lastPoint = null;

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

    public void onCall(Motions.Point newPoint){

        if(!isActive()){
            return;
        }
        
        boolean overlap = newPoint != null && screenPointOverlap((int) newPoint.startPos.x, (int) newPoint.startPos.y);

        if(overlap){

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
                }
            }

        } else {

            if(newPoint == null && lastPoint != null){
                //use touchPoints for drag
                onDrag(lastPoint);
            }
            else if(newPoint != null && lastPoint != null && !newPoint.isDown){
                lastPoint = null;
                onTouchRelease(newPoint);
            }
        }
    }

    public void reset(){
        lastPoint = null;
    }

    public void onDownTouch(Motions.Point t){};

    public void onTouchRelease(Motions.Point t){};

    public void onDrag(Motions.Point t){};

    public void onClick(Motions.Point t){};
}
