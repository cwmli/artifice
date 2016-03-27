package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.TouchBox;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.math.Vector2;

public abstract class CirclePad extends HotCircle {

    protected TouchBox hitZone;

    @Override
    public void createContent(){
        hitZone = new TouchBox(x, y, radius * 2, radius * 2){

            @Override
            public boolean pointOverlap(int x, int y){

                int distance = (int)center().distance(new Vector2(x, y));
                if(distance * distance <= radius * radius){
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onDownTouch(Motions.Point t){
                onTouch(t);
            }

            @Override
            public void onTouchRelease(Motions.Point t){
                onRelease(t);
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

    protected abstract void onTouch(Motions.Point p);

    protected abstract void onDragged(Motions.Point p);

    protected abstract void onRelease(Motions.Point p);
}
