package com.underwaterotter.artifice.windows;

import android.graphics.Color;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.scenes.MenuScene;
import com.underwaterotter.artifice.uibuttons.MenuButton;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.glesutils.TextureCache;
import com.underwaterotter.math.Vector3;

/**
 * Created by Calvin on 09/02/2017.
 */

public class PauseWindow extends Window {

    private static final int PADDING = 10;
    private static final int SPACING = 15;

    private static final String BTN_CONTINUE = "Continue";
    private static final String BTN_OPTIONS = "Options";
    private static final String BTN_MAIN_MENU = "Exit";

    public PauseWindow() {
        super(new Image(TextureCache.createRect((int)MenuScene.VIRTUAL_WIDTH,
                (int)MenuScene.VIRTUAL_HEIGHT, Color.BLACK, true))
                , new Vector3(0, 0, 0));
        setAlpha(0.5f);

        camera = GameScene.uiCamera;
    }

    protected void createContent() {

        MenuButton resume = new MenuButton(BTN_CONTINUE, 0) {
            @Override
            protected void onClick() {
                GameScene.scene.revive();
                GameScene.scene.closeWindow((Window)parent);
                this.destroy();
            }
        };
        resume.position(position.x + getWidth() / 2 + resume.width() / 2,
                PADDING);
        add(resume);


        MenuButton options = new MenuButton(BTN_OPTIONS, 0) {
            @Override
            protected void onClick() {
                //open options window
            }
        };
        options.position(position.x + getWidth() / 2 + resume.width() / 2,
                resume.bottom() + SPACING);
        add(options);


        MenuButton exit = new MenuButton(BTN_MAIN_MENU, 0) {
            @Override
            protected void onClick() {
                GameScene.scene.closeWindow((Window)parent);
                //Do saves
                Artifice.switchScene(MenuScene.class);
            }
        };
        exit.position(position.x + getWidth() / 2 + resume.width() / 2,
                options.bottom() + SPACING);
        add(exit);
    }
}
