package com.underwaterotter.artifice;

import android.graphics.Color;

import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.ui.CirclePad;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.artifice.entities.main.CharController;
import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.glesutils.TextureCache;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;

public class Joystick extends CirclePad {

    public static final String JOY_X = "joy_x";
    public static final String JOY_Y = "joy_y";

    public static final int SIZE_R = 16;

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

        nob.pos.x = x + (SIZE_R / 2) - (nob.width / 2);
        nob.pos.y = y + (SIZE_R / 2) - (nob.height / 2);

        joystick.pos.x = x;
        joystick.pos.y = y;
    }

    protected Vector2 screenToJoystick(Motions.Point p){

        float y = p.startPos.y;
        float x = p.startPos.x;

        //localize the vector signs
        boolean positiveY = y > center().y;
        //divide the joystick area into quadrants
        if (x > center().x){
            if(!positiveY){
                y *= -1;
            }

        } else {
            x *= -1;
            if(!positiveY){
                y *= -1;
            }
        }

        return new Vector2(x, y);
    }

    public double angle(Motions.Point p){
        Vector2 center = center();
        Vector2 touchPoint = screenToJoystick(p);
        Vector2 refPoint = new Vector2(center.x + x, center.y);

        double a1 = Math.atan2(refPoint.y - center.y, refPoint.x - center.x);
        double a2 = Math.atan2(touchPoint.y - center.y, touchPoint.x - center.x);

        return a1 - a2;
    }

    protected void onLongTouch(Motions.Point p){
        nob.position(p.startPos.x - 8, p.startPos.y - 8, 0);

        CharController.setVelocity((float)Math.toDegrees(angle(p)));
    }

    protected void onTouch(Motions.Point p){
        nob.position(p.startPos.x - 8,
                p.startPos.y - 8 , 0);

        CharController.setVelocity((float) Math.toDegrees(angle(p)));
    }

    protected void onRelease(Motions.Point p){
        //recenter the nob
        Vector2 lcJoy = position();
        nob.position(lcJoy.x + (SIZE_R / 2) - (nob.width / 2), lcJoy.y + (SIZE_R / 2) - (nob.height / 2), 0);

        initialDrag = false;

        CharController.setSpeed(0);
        CharController.setAction("idle");
    }

    protected void onDragged(Motions.Point p){
        CharController.setVelocity((float) Math.toDegrees(angle(p)));

        if(initialDrag) {

            Vector2 dxy = lastPoint.difference(p.endPos);

            Vector3 oldPos = nob.position();
            Vector2 result = new Vector2(
                    oldPos.x + dxy.x,
                    oldPos.y + dxy.y);

            if(Math.abs(center().distance(result)) > radius){
                return;
            } else {
                nob.position(result.x, result.y, 0);
            }

            int speed = Math.round(GameScene.scene.player.speed *
                    (Math.abs(center().distance(position())) / radius));

            CharController.setSpeed(speed);

            lastPoint.set(p.endPos);

        } else {
            initialDrag = true;
            lastPoint =  p.endPos;
        }
    }
}
