package com.underwaterotter.artifice;

import android.util.Log;

import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.ui.CirclePad;
import com.underwaterotter.cetoinput.Motions;
import com.underwaterotter.artifice.entities.main.CharController;
import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.scenes.UIScene;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;

public class Joystick extends CirclePad {

    public static final String JOY_X = "joy_x";
    public static final String JOY_Y = "joy_y";

    Image joystick;
    Image nob;

    private Vector2 lastPoint;
    private boolean initialDrag = false;

    public Joystick(){
        this((float) Artifice.settings.getInt(JOY_X, Math.round(70 / UIScene.uiCamera.zoom)),
                (float) Artifice.settings.getInt(JOY_Y, Math.round(110 / UIScene.uiCamera.zoom)));
    }

    public Joystick(float screen_x, float screen_y){
        super(screen_x, screen_y, (32f / UIScene.uiCamera.zoom));

        resize(32);
    }

    @Override
    public void createContent(){
        super.createContent();

        joystick = new Image(new Vector3(x, y, 0),
                Assets.JOY);

        nob = new Image(new Vector3(x + (16 / UIScene.uiCamera.zoom), y + (16 / UIScene.uiCamera.zoom), 0),
                Assets.JOY_NOB);

        add(joystick);
        add(nob);
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
        Vector3 center = center();
        Vector2 touchPoint = screenToJoystick(p);
        Vector2 refPoint = new Vector2(center.x + x, center.y);

        double a1 = Math.atan2(refPoint.y - center.y, refPoint.x - center.x);
        double a2 = Math.atan2(touchPoint.y - center.y, touchPoint.x - center.x);

        return a1 - a2;
    }

    protected void onLongTouch(Motions.Point p){
        nob.position(p.startPos.x - (16 / UIScene.uiCamera.zoom), p.startPos.y - (16 / UIScene.uiCamera.zoom), 0);

        CharController.setVelocity((float)Math.toDegrees(angle(p)));
    }

    protected void onTouch(Motions.Point p){
        nob.position(p.startPos.x - (16 / UIScene.uiCamera.zoom),
                p.startPos.y - (16 / UIScene.uiCamera.zoom), 0);

        CharController.setVelocity((float) Math.toDegrees(angle(p)));
    }

    protected void onRelease(Motions.Point p){
        //recenter the nob
        Vector3 lcJoy = position();
        nob.position(lcJoy.x + (16 / UIScene.uiCamera.zoom), lcJoy.y + (16 / UIScene.uiCamera.zoom), 0);

        initialDrag = false;

        CharController.setSpeed(0);
        CharController.setAction(null);
    }

    protected void onDragged(Motions.Point p){
        CharController.setVelocity((float) Math.toDegrees(angle(p)));

        if(initialDrag) {

            Vector2 dxy = lastPoint.difference(p.endPos);

            Vector3 oldPos = nob.position();
            Vector3 result = new Vector3(
                    oldPos.x += (dxy.x / UIScene.uiCamera.zoom),
                    oldPos.y += (dxy.y / UIScene.uiCamera.zoom),
                    0);

            if(Math.abs(center().distance(result)) > radius / UIScene.uiCamera.zoom){
                return;
            } else {
                nob.position(result.x, result.y, 0);
            }

            int speed = Math.round(GameScene.scene.player.speed *
                    (Math.abs(center().distance(position())) / (radius / UIScene.uiCamera.zoom)));

            CharController.setSpeed(speed);

            lastPoint.set(p.endPos);

        } else {
            initialDrag = true;
            lastPoint =  p.endPos;
        }
    }
}
