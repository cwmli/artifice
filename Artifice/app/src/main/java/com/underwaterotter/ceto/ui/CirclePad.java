package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.TouchBox;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.utils.Time;

public abstract class CirclePad extends HotCircle {

    protected TouchBox hitZone;

    private long start;
    private float duration;

    @Override
    public void createContent(){
        hitZone = new TouchBox(x, y, radius * 2, radius * 2){

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
    public void update() {
        super.update();

        hitZone.active = visible;
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
}
