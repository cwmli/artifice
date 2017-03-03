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

    public boolean attack(Orientation type, int chain) {

        Log.v("INFO", "Playing attack set: " + chain);
        // Interrupt only if it is the first in chain
        boolean interrupt = chain == 0;

        if (type == Orientation.SIDE) {
            return play(attack[0][chain], interrupt);
        } else if (type == Orientation.DOWN) {
            return play(attack[1][chain], interrupt);
        } else {
            return play(attack[2][chain], interrupt);
        }
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
        idle[0].setFrames(new RectF(0.0625f, 0f, 0.1250f, 0.1250f));
        idle[1] = new Animation(5f, true);
        idle[1].setFrames(new RectF(0f     , 0f, 0.0625f, 0.1250f));
        idle[2] = new Animation(5f, true);
        idle[2].setFrames(new RectF(0.1250f, 0f, 0.1875f, 0.1250f));

        run[0] = new Animation(7f, true);
        // Side view
        run[0].setFrames(new RectF(0f     , 0.1250f, 0.0625f, 0.2500f), new RectF(0.0625f, 0.1250f, 0.1250f, 0.2500f),
                         new RectF(0.1250f, 0.1250f, 0.1875f, 0.2500f), new RectF(0.1875f, 0.1250f, 0.2500f, 0.2500f),
                         new RectF(0.2500f, 0.1250f, 0.3125f, 0.2500f), new RectF(0.3125f, 0.1250f, 0.3750f, 0.2500f),
                         new RectF(0.3750f, 0.1250f, 0.4375f, 0.2500f), new RectF(0.4375f, 0.1250f, 0.5000f, 0.2500f));
        // Frontal view
        run[1] = new Animation(7f, true);
        run[1].setFrames(new RectF(0.5000f, 0f    , 0.5625f, 0.1250f), new RectF(0.5625f, 0f    , 0.6250f, 0.1250f),
                         new RectF(0.6250f, 0f    , 0.6875f, 0.1250f), new RectF(0.6875f, 0f    , 0.7500f, 0.1250f),
                         new RectF(0.7500f, 0f    , 0.8125f, 0.1250f), new RectF(0.8125f, 0f    , 0.8750f, 0.1250f),
                         new RectF(0.8750f, 0f    , 0.9375f, 0.1250f), new RectF(0.9375f, 0f    , 1.0000f, 0.1250f));
        // Back View
        run[2] = new Animation(7f, true);
        run[2].setFrames(new RectF(0.5000f, 0.1250f, 0.5625f, 0.2500f), new RectF(0.5625f, 0.1250f, 0.6250f, 0.2500f),
                         new RectF(0.6250f, 0.1250f, 0.6875f, 0.2500f), new RectF(0.6875f, 0.1250f, 0.7500f, 0.2500f),
                         new RectF(0.7500f, 0.1250f, 0.8125f, 0.2500f), new RectF(0.8125f, 0.1250f, 0.8750f, 0.2500f),
                         new RectF(0.8750f, 0.1250f, 0.9375f, 0.2500f), new RectF(0.9375f, 0.1250f, 1.0000f, 0.2500f));

        attack = new Animation[3][8];
        // Side View
        attack[0][0] = new Animation(15f, false);
        attack[0][0].setFrames(new RectF(0.5000f, 0.2500f, 0.5625f, 0.3750f), new RectF(0.5625f, 0.2500f, 0.6250f, 0.3750f));

        attack[0][1] = new Animation(15f, false);
        attack[0][1].setFrames(new RectF(0.6250f, 0.2500f, 0.6875f, 0.3750f), new RectF(0.6875f, 0.2500f, 0.7500f, 0.3750f));

        attack[0][2] = new Animation(15f, false);
        attack[0][2].setFrames(new RectF(0.7500f, 0.2500f, 0.8125f, 0.3750f), new RectF(0.8125f, 0.2500f, 0.8750f, 0.3750f));

        attack[0][3] = new Animation(15f, false);
        attack[0][3].setFrames(new RectF(0.8750f, 0.2500f, 0.9375f, 0.3750f), new RectF(0.9375f, 0.2500f, 1.0000f, 0.3750f));

        attack[0][4] = new Animation(15f, false);
        attack[0][4].setFrames(new RectF(0.8750f, 0.3750f, 0.9375f, 0.5000f), new RectF(0.9375f, 0.3750f, 1.0000f, 0.5000f));

        attack[0][5] = new Animation(15f, false);
        attack[0][5].setFrames(new RectF(0.5000f, 0.3750f, 0.5625f, 0.5000f), new RectF(0.5625f, 0.3750f, 0.6250f, 0.5000f));

        attack[0][6] = new Animation(15f, false);
        attack[0][6].setFrames(new RectF(0.6250f, 0.3750f, 0.6875f, 0.5000f), new RectF(0.6875f, 0.3750f, 0.7500f, 0.5000f));

        attack[0][7] = new Animation(15f, false);
        attack[0][7].setFrames(new RectF(0.7500f, 0.3750f, 0.8125f, 0.5000f), new RectF(0.8125f, 0.3750f, 0.8750f, 0.5000f));


        // Frontal View
        attack[1][0] = new Animation(15f, false);
        attack[1][0].setFrames(new RectF(0f     , 0.2500f, 0.0625f, 0.3750f), new RectF(0.0625f, 0.2500f, 0.1250f, 0.3750f));

        attack[1][1] = new Animation(15f, false);
        attack[1][1].setFrames(new RectF(0.1250f, 0.2500f, 0.1875f, 0.3750f), new RectF(0.1875f, 0.2500f, 0.2500f, 0.3750f));

        attack[1][2] = new Animation(15f, false);
        attack[1][2].setFrames(new RectF(0.2500f, 0.2500f, 0.3125f, 0.3750f), new RectF(0.3125f, 0.2500f, 0.3750f, 0.3750f));

        attack[1][3] = new Animation(15f, false);
        attack[1][3].setFrames(new RectF(0.3750f, 0.2500f, 0.4375f, 0.3750f), new RectF(0.4375f, 0.2500f, 0.5000f, 0.3750f));

        attack[1][4] = new Animation(15f, false);
        attack[1][4].setFrames(new RectF(0.3750f, 0.3750f, 0.4375f, 0.5000f), new RectF(0.4375f, 0.3750f, 0.5000f, 0.5000f));

        attack[1][5] = new Animation(15f, false);
        attack[1][5].setFrames(new RectF(0f     , 0.3750f, 0.0625f, 0.5000f), new RectF(0.0625f, 0.3750f, 0.1250f, 0.5000f));

        attack[1][6] = new Animation(15f, false);
        attack[1][6].setFrames(new RectF(0.1250f, 0.3750f, 0.1875f, 0.5000f), new RectF(0.1875f, 0.3750f, 0.2500f, 0.5000f));

        attack[1][7] = new Animation(15f, false);
        attack[1][7].setFrames(new RectF(0.2500f, 0.3750f, 0.3125f, 0.5000f), new RectF(0.3125f, 0.3750f, 0.3750f, 0.5000f));


        // Back View
        attack[2][0] = new Animation(15f, false);
        attack[2][0].setFrames(new RectF(0f     , 0.5000f, 0.0625f, 0.6250f), new RectF(0.0625f, 0.5000f, 0.1250f, 0.6250f));

        attack[2][1] = new Animation(15f, false);
        attack[2][1].setFrames(new RectF(0.1250f, 0.5000f, 0.1875f, 0.6250f), new RectF(0.1875f, 0.5000f, 0.2500f, 0.6250f));

        attack[2][2] = new Animation(15f, false);
        attack[2][2].setFrames(new RectF(0.2500f, 0.5000f, 0.3125f, 0.6250f), new RectF(0.3125f, 0.5000f, 0.3750f, 0.6250f));

        attack[2][3] = new Animation(15f, false);
        attack[2][3].setFrames(new RectF(0.3750f, 0.5000f, 0.4375f, 0.6250f), new RectF(0.4375f, 0.5000f, 0.5000f, 0.6250f));

        attack[2][4] = new Animation(15f, false);
        attack[2][4].setFrames(new RectF(0.3750f, 0.6250f, 0.4375f, 0.7500f), new RectF(0.4375f, 0.6250f, 0.1250f, 0.7500f));

        attack[2][5] = new Animation(15f, false);
        attack[2][5].setFrames(new RectF(0f     , 0.6250f, 0.0625f, 0.7500f), new RectF(0.0625f, 0.6250f, 0.1250f, 0.7500f));

        attack[2][6] = new Animation(15f, false);
        attack[2][6].setFrames(new RectF(0.1250f, 0.6250f, 0.1875f, 0.7500f), new RectF(0.1875f, 0.6250f, 0.2500f, 0.7500f));

        attack[2][7] = new Animation(15f, false);
        attack[2][7].setFrames(new RectF(0.2500f, 0.6250f, 0.3125f, 0.7500f), new RectF(0.3125f, 0.6250f, 0.3750f, 0.7500f));
    }
}
