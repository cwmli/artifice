package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.Game;
import com.underwaterotter.ceto.TouchBox;
import com.underwaterotter.cetoinput.Motions;

public class Button extends HotBox {

    protected TouchBox hitZone;

    private boolean pressed;
    private boolean readed;
    private long start;

    @Override
    public void createContent(){
        hitZone = new TouchBox(x, y, width, height){

            @Override
            public void onDownTouch(Motions.Point t){
                pressed = true;
                start = 0;
                readed = false;
                onTouch();
            }

            @Override
            public void onTouchRelease(Motions.Point t){
                pressed = false;
                onRelease();
            }

            @Override
            public void onClick(Motions.Point t){
                if(!readed) {
                    readed = true;
                    Button.this.onClick();
                }
            }
        };
        add(hitZone);
    }

    @Override
    public void update() {
        super.update();

        hitZone.active = visible;

        if (pressed) {
            if ((start += Game.elapsedTime) >= 1f) {
                pressed = false;
                if (longTouchAllowed()) {

                    hitZone.reset();
                    readed = true;
                    onRelease();
                }
            }
        }
    }

    @Override
    protected void updateHitbox(){
        hitZone.position(x, y, 0);
        hitZone.width = width;
        hitZone.height = height;
    }

    protected boolean longTouchAllowed(){
        return false;
    }

    protected void onTouch() {};

    protected void onRelease() {};

    protected void onClick() {};
}