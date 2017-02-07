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

    @Override
    public void setMob(Mob mob) {
        super.setMob(mob);

        width = SIZE_W;
        height = SIZE_H;
    }

    @Override
    public void setSpeed(float spd) {
        super.setSpeed(spd);

        activeAnimation.setFrameDuration((spd / 1f) * 7f); //GET RID OF MAGIC NUMBERS
    }

    @Override
    protected void setAnimations() {
        idle[0] = new Animation(5f, true);
        idle[0].setFrames(new RectF(0.125f, 2f/3f, 0.250f, 1f));
        idle[1] = new Animation(5f, true);
        idle[1].setFrames(new RectF(0f, 2f/3f, 0.125f, 1f));
        //idle[2]

        run[0] = new Animation(7f, true);
        run[0].setFrames(new RectF(0    , 1f/3f, 0.125f, 2f/3f), new RectF(0.125f, 1f/3f, 0.25f, 2f/3f),
                         new RectF(0.25f, 1f/3f, 0.375f, 2f/3f), new RectF(0.375f, 1f/3f, 0.5f, 2f/3f),
                         new RectF(0.5f, 1f/3f, 0.625f, 2f/3f), new RectF(0.625f, 1f/3f, 0.750f, 2f/3f),
                         new RectF(0.750f, 1f/3f, 0.875f, 2f/3f), new RectF(0.875f, 1f/3f, 1f, 2f/3f));
        run[1] = new Animation(7f, true);
        run[1].setFrames(new RectF(0    , 0, 0.125f, 1f/3f), new RectF(0.125f, 0, 0.25f, 1f/3f),
                         new RectF(0.25f, 0, 0.375f, 1f/3f), new RectF(0.375f, 0, 0.5f, 1f/3f),
                         new RectF(0.5f, 0, 0.625f, 1f/3f), new RectF(0.625f, 0, 0.750f, 1f/3f),
                         new RectF(0.750f, 0, 0.875f, 1f/3f), new RectF(0.875f, 0, 1f, 1f/3f));
        //run[2]
    }
}
