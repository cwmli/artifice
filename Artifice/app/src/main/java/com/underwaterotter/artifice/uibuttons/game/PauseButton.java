package com.underwaterotter.artifice.uibuttons.game;

import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.windows.PauseWindow;
import com.underwaterotter.artifice.windows.Window;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.ui.Button;
import com.underwaterotter.math.Vector3;

/**
 * Created by Calvin on 01/02/2017.
 */

public class PauseButton extends Button {

    private static final int SIZE_W = 16;
    private static final int SIZE_H = 16;

    private Image button;

    public PauseButton() {
        super();

        resize(SIZE_W, SIZE_H);
    }

    @Override
    protected void createContent() {
        super.createContent();

        button = new Image(Assets.PAUSE);
        button.alpha_M(0.5f);
        add(button);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();

        button.setPos(x, y, 0);
    }

    @Override
    protected void onTouch() {
        button.alpha_M(1f);
    }

    @Override
    protected void onRelease() {
        button.alpha_M(0.5f);
    }

    @Override
    protected void onClick() {
        GameScene.scene.kill();
        PauseWindow pauseWindow = new PauseWindow();
        GameScene.scene.openWindow(pauseWindow);
    }
}
