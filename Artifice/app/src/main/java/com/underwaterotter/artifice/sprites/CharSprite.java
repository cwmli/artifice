package com.underwaterotter.artifice.sprites;

import android.graphics.RectF;
import android.util.Log;

import com.underwaterotter.artifice.entities.mobs.Mob;
import com.underwaterotter.ceto.Animation;

public class CharSprite extends MobSprite {

    private static final int SIZE_W = 16;
    private static final int SIZE_H = 32;

    public CharSprite(Object id) {
        super(id);
    }

    public boolean attack(Orientation type, int chain){

        Log.v("INFO", "Playing attack set: " + chain);
        if (chain == 0) {
            return play(attack[chain], true);
        } else {
            return play(attack[chain], false);
        }

//        if (goNext) {
//            next();
//        } else { //chain counter is valid continue to next combo
//            if (type == Orientation.SIDE) {
//                play(attack[0], true);
//            } else if (type == Orientation.DOWN) {
//                play(attack[1], true);
//            } else {
//                play(attack[2], true);
//            }
//        }
    }

    @Override
    public void setMob(Mob mob) {
        super.setMob(mob);

        width = SIZE_W;
        height = SIZE_H;
    }

    @Override
    protected void setAnimations() {
        idle[0] = new Animation(5f, true);
        idle[0].setFrames(new RectF(0.125f, 0.500f, 0.250f, 2f/3f));
        idle[1] = new Animation(5f, true);
        idle[1].setFrames(new RectF(0f, 0.500f, 0.125f, 2f/3f));
        idle[2] = new Animation(5f, true);
        idle[2].setFrames(new RectF(0.250f, 0.500f, 0.375f, 2f/3f));

        run[0] = new Animation(7f, true);
        run[0].setFrames(new RectF(0     , 1f/6f, 0.125f, 1f/3f), new RectF(0.125f, 1f/6f, 0.250f, 1f/3f),
                         new RectF(0.250f, 1f/6f, 0.375f, 1f/3f), new RectF(0.375f, 1f/6f, 0.500f, 1f/3f),
                         new RectF(0.500f, 1f/6f, 0.625f, 1f/3f), new RectF(0.625f, 1f/6f, 0.750f, 1f/3f),
                         new RectF(0.750f, 1f/6f, 0.875f, 1f/3f), new RectF(0.875f, 1f/6f, 1.000f, 1f/3f));
        run[1] = new Animation(7f, true);
        run[1].setFrames(new RectF(0     , 0, 0.125f, 1f/6f), new RectF(0.125f, 0, 0.250f, 1f/6f),
                         new RectF(0.250f, 0, 0.375f, 1f/6f), new RectF(0.375f, 0, 0.500f, 1f/6f),
                         new RectF(0.500f, 0, 0.625f, 1f/6f), new RectF(0.625f, 0, 0.750f, 1f/6f),
                         new RectF(0.750f, 0, 0.875f, 1f/6f), new RectF(0.875f, 0, 1.000f, 1f/6f));
        run[2] = new Animation(7f, true);
        run[2].setFrames(new RectF(0    ,  1f/3f, 0.125f, 0.500f), new RectF(0.125f, 1f/3f, 0.250f, 0.500f),
                         new RectF(0.250f, 1f/3f, 0.375f, 0.500f), new RectF(0.375f, 1f/3f, 0.500f, 0.500f),
                         new RectF(0.500f, 1f/3f, 0.625f, 0.500f), new RectF(0.625f, 1f/3f, 0.750f, 0.500f),
                         new RectF(0.750f, 1f/3f, 0.875f, 0.500f), new RectF(0.875f, 1f/3f, 1.000f, 0.500f));

        attack = new Animation[8];
        attack[0] = new Animation(15f, false);
        attack[0].setFrames(new RectF(0    ,  2f/3f, 0.125f, 5f/6f), new RectF(0.125f, 2f/3f, 0.250f, 5f/6f));

        attack[1] = new Animation(15f, false);
        attack[1].setFrames(new RectF(0.250f, 2f/3f, 0.375f, 5f/6f), new RectF(0.375f, 2f/3f, 0.500f, 5f/6f));

        attack[2] = new Animation(15f, false);
        attack[2].setFrames(new RectF(0.500f, 2f/3f, 0.625f, 5f/6f), new RectF(0.625f, 2f/3f, 0.750f, 5f/6f));

        attack[3] = new Animation(15f, false);
        attack[3].setFrames(new RectF(0.750f, 2f/3f, 0.875f, 5f/6f), new RectF(0.875f, 2f/3f, 1.000f, 5f/6f));

        attack[4] = new Animation(15f, false);
        attack[4].setFrames(new RectF(0.750f, 5f/6f, 0.875f, 1f), new RectF(0.875f, 5f/6f, 1.000f, 1f));

        attack[5] = new Animation(15f, false);
        attack[5].setFrames(new RectF(0    ,  5f/6f, 0.125f, 1f), new RectF(0.125f, 5f/6f, 0.250f, 1f));

        attack[6] = new Animation(15f, false);
        attack[6].setFrames(new RectF(0.250f, 5f/6f, 0.375f, 1f), new RectF(0.375f, 5f/6f, 0.500f, 1f));

        attack[7] = new Animation(15f, false);
        attack[7].setFrames(new RectF(0.500f, 5f/6f, 0.625f, 1f), new RectF(0.625f, 5f/6f, 0.750f, 1f));
    }
}
