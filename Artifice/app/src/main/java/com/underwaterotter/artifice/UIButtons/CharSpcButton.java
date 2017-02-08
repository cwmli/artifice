package com.underwaterotter.artifice.UIButtons;

import com.underwaterotter.artifice.entities.mobs.main.Char;
import com.underwaterotter.artifice.entities.mobs.main.CharController;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.ui.CirclePad;
import com.underwaterotter.cetoinput.Motions;

public class CharSpcButton extends CirclePad {

    public static final String STD_BTN_X = "bsc_btn_x";
    public static final String STD_BTN_Y = "bsc_btn_y";

    private static final int SIZE_R = 8;

    private Image button;

    public CharSpcButton() {
        super();

        resize(SIZE_R);
    }

    @Override
    protected void createContent() {
        super.createContent();

        button = new Image(Assets.JOY);
        button.alpha_M(0.5f);
        add(button);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();

        button.setPos(x, y, 0);
    }

    protected void onTouch(Motions.Point p) {}

    protected void onDragged(Motions.Point p) {}

    protected void onRelease(Motions.Point p) {}

    protected void onClick(Motions.Point p) {
        CharController.setAction(Char.HVY_ATK);
    }
}
