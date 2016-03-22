package com.underwaterotter.artifice;

import android.graphics.Color;
import android.util.Log;

import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.ui.CirclePad;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.artifice.entities.main.CharController;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.glesutils.TextureCache;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;

public class Joystick extends CirclePad {

    public static final String JOY_X = "joy_x";
    public static final String JOY_Y = "joy_y";

    public static final int SIZE_R = 16;
    public static final int SIZE_N = 8;

    private Image joystick;
    private Image nob;

    private Vector2 lastPoint;
    private boolean initialDrag = false;

    public Joystick(){
        super();

        resize(SIZE_R);
    }

    @Override
    public void createContent(){
        super.createContent();

        joystick = new Image(Assets.JOY);
        add(joystick);

        nob = new Image(TextureCache.createCircle(SIZE_R / 2, Color.GRAY, true));
        add(nob);
    }

    @Override
    public void updateHitbox(){
        super.updateHitbox();

        nob.pos.x = x + SIZE_R - (nob.width / 2);
        nob.pos.y = y + SIZE_R - (nob.height / 2);

        joystick.pos.x = x;
        joystick.pos.y = y;
    }

    public double angle(Motions.Point p){
        Vector2 pos = camera().screenToCamera((int)p.startPos.x, (int)p.startPos.y);
        pos.set(pos.x - center().x, pos.y - center().y);
        Vector2 refPoint = new Vector2(SIZE_R, 0);

        Log.v("JOYSTICK ANGLE", String.valueOf(Math.atan2(pos.dot(refPoint), pos.det(refPoint))));

        return Math.atan2(pos.dot(refPoint), pos.det(refPoint));
    }

    protected void onLongTouch(Motions.Point p){

    }

    protected void onTouch(Motions.Point p){

    }

    protected void onRelease(Motions.Point p){
        //recenter the nob
        Vector2 lcJoy = position();
        nob.position(lcJoy.x + SIZE_R - (nob.width / 2), lcJoy.y + SIZE_R - (nob.height / 2), 0);

        Log.v("JOYNOB", "JOYSTICK NOB HAS MOVED.");

        initialDrag = false;

        CharController.setSpeed(0);
        CharController.setAction("none");
    }

    protected void onDragged(Motions.Point p){
        Vector2 pos = camera().screenToCamera((int)p.endPos.x, (int)p.endPos.y);
        CharController.setVelocity((float) Math.toDegrees(angle(p)));

        if(initialDrag) {

            Vector2 dxy = lastPoint.difference(pos);

            Vector3 oldPos = nob.position();
            Vector2 result = new Vector2(
                    oldPos.x + dxy.x - (SIZE_N / 2),
                    oldPos.y + dxy.y - (SIZE_N / 2));

            if(Math.abs(center().distance(result)) > radius){
                nob.position((int)Math.cos(Math.toDegrees(angle(p))) * SIZE_R + center().x,
                        (int)Math.sin(Math.toDegrees(angle(p))) * SIZE_R + center().y, 0);
            } else {
                nob.position(result.x, result.y, 0);
            }

            int speed = 1;

            CharController.setSpeed(speed);

            lastPoint.set(p.endPos);

        } else {
            initialDrag = true;
            lastPoint =  p.endPos;
        }
    }
}
