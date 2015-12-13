package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.Overlay;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Listener;
import com.underwaterotter.utils.Time;

public abstract class CirclePad extends HotCircle {

    protected TouchCircle hitZone;

    private long start;
    private float duration;

    public CirclePad(float x, float y, float r){
        super(x, y, r);
    }

    @Override
    public void createContent(){
        hitZone = new TouchCircle(x, y, radius){

            @Override
            public void onDownTouch(Motions.Point t){
                start = Time.time;
                onTouch(t);
            }

            @Override
            public void onTouchRelease(Motions.Point t){

                duration = Time.time - start;

                //longer than 1.25 seconds
                if (duration > 1250){
                    onLongTouch(t);
                } else {
                    onRelease(t);
                }
            }

            @Override
            public void onDrag(Motions.Point t){
                onDragged(t);
            }

            @Override
            public void onClick(Motions.Point t){
                onTouch(t);
            }
        };
        add(hitZone);
    }

    @Override
    protected void updateHitbox(){
        hitZone.position(x, y, 0);
        hitZone.width = radius * 2;
        hitZone.height = radius * 2;
    }

    protected abstract void onLongTouch(Motions.Point p);

    protected abstract void onTouch(Motions.Point p);

    protected abstract void onDragged(Motions.Point p);

    protected abstract void onRelease(Motions.Point p);

    public class TouchCircle extends Overlay implements Listener.Trigger<Motions.Point>{

        protected Motions.Point lastPoint = null;

        public TouchCircle(float x, float y, float radius){
            super(new Vector3(x, y, 0), radius * 2, radius * 2);

            Motions.motionListeners.add(this);
        }

        @Override
        public boolean screenPointOverlap(int x, int y){

            int distance = (int)center().distance(new Vector2(x, y));
            if(distance * distance <= radius * radius){
                return true;
            } else {
                return false;
            }
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

            boolean overlap = screenPointOverlap((int) newPoint.startPos.x, (int) newPoint.startPos.y);

            if(overlap && newPoint != null){

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
                    onDrag(newPoint);
                }
                else if(newPoint != null && lastPoint != null && !newPoint.isDown){
                    lastPoint = null;
                    onTouchRelease(newPoint);
                }
            }
        }

        public void onDownTouch(Motions.Point t){};

        public void onTouchRelease(Motions.Point t){};

        public void onDrag(Motions.Point t){};

        public void onClick(Motions.Point t){};
    }
}
