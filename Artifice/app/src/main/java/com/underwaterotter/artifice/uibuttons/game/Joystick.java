package com.underwaterotter.artifice.uibuttons.game;

import android.graphics.Color;
import android.util.Log;

import com.underwaterotter.artifice.entities.mobs.Mob;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.ui.CirclePad;
import com.underwaterotter.artifice.entities.mobs.main.CharController;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.cetoinput.Point;
import com.underwaterotter.glesutils.TextureCache;
import com.underwaterotter.math.Vector2;

public class Joystick extends CirclePad {

    public static final String JOY_X = "joy_x";
    public static final String JOY_Y = "joy_y";

    private static final int SIZE_R = 16;
    private static final int SIZE_N = 8;

    private Image joystick;
    private Image nob;

    private Vector2 lastPoint;
    private boolean initialDrag = false;

    public Joystick() {
        super();

        resize(SIZE_R);
    }

    @Override
    public void createContent() {
        super.createContent();

        joystick = new Image(Assets.JOY);
        joystick.alpha_M(0.5f);
        add(joystick);

        nob = new Image(TextureCache.createCircle(SIZE_R / 2, Color.GRAY, true));
        nob.alpha_M(0.5f);
        add(nob);
    }

    @Override
    public void updateHitbox() {
        super.updateHitbox();

        nob.setPos(x + SIZE_R - (nob.getWidth() / 2),
                y + SIZE_R - (nob.getHeight() / 2),
                0);

        joystick.setPos(x, y, 0);
    }

    private double angle(Point p) {
        Vector2 pos = camera().screenToCamera((int)p.endPos.x, (int)p.endPos.y);
        pos.set(pos.x - center().x, pos.y - center().y);
        Vector2 refPoint = new Vector2(SIZE_R, 0);

        double angle = Math.toDegrees(Math.atan2(refPoint.x, refPoint.y) - Math.atan2(pos.x, pos.y));
        if (angle < 0) angle += 2 * Math.PI;

        return angle;
    }

    protected void onLongTouch(Point p) {}

    protected void onTouch(Point p) {}

    protected void onDragged(Point p) {
        Vector2 pos = camera().screenToCamera((int)p.endPos.x, (int)p.endPos.y);
        CharController.setAngle((float)angle(p));

        if(initialDrag) {

            if(joystick.center().distance(pos) > SIZE_R){
                nob.setPos((float)Math.cos(Math.toRadians(angle(p))) *
                                SIZE_R + joystick.center().x - (nob.getWidth() / 2),
                        (float)Math.sin(Math.toRadians(angle(p))) *
                                SIZE_R + joystick.center().y - (nob.getHeight() / 2), 0);
                CharController.setSpeed(1.0f);
            } else {
                nob.setPos(pos.x - SIZE_N, pos.y - SIZE_N, 0);
                CharController.setSpeed(joystick.center().distance(pos) / (SIZE_R * 2));
            }

            lastPoint.set(pos);

        } else {
            initialDrag = true;
            lastPoint =  pos;
        }

    }

    protected void onRelease(Point p) {
        Log.v("INFO", "Released joy.");
        //recenter the nob
        Vector2 lcJoy = position();
        nob.setPos(lcJoy.x + SIZE_R - (nob.getWidth() / 2),
                lcJoy.y + SIZE_R - (nob.getHeight() / 2), 0);

        initialDrag = false;

        CharController.setSpeed(0);
        CharController.setAction(Mob.IDLE);
    }

    protected void onClick(Point p) {}
}
