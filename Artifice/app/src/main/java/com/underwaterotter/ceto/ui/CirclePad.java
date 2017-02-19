package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.TouchBox;
import com.underwaterotter.cetoinput.Point;
import com.underwaterotter.math.Vector2;

public abstract class CirclePad extends HotCircle {

    private TouchBox hitZone;

    @Override
    protected void createContent(){
        hitZone = new TouchBox(x, y, radius * 2, radius * 2){

            @Override
            public boolean pointOverlap(int x, int y){

                int distance = (int)center().distance(new Vector2(x, y));
                return distance * distance <= radius * radius;
            }

            @Override
            public void onDownTouch(Point t){
                onTouch(t);
            }

            @Override
            public void onTouchRelease(Point t){
                onRelease(t);
            }

            @Override
            public void onDrag(Point t){
                onDragged(t);
            }

            @Override
            public void onClick(Point t){
                CirclePad.this.onClick(t);
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
        hitZone.setPos(x, y, 0);
        hitZone.setWidth(radius * 2);
        hitZone.setHeight(radius * 2);
    }

    protected abstract void onTouch(Point p);

    protected abstract void onDragged(Point p);

    protected abstract void onRelease(Point p);

    protected abstract void onClick(Point p);
}
