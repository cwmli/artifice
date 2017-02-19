package com.underwaterotter.artifice.scenes;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.uibuttons.MenuButton;
import com.underwaterotter.artifice.world.TestLevel;
import com.underwaterotter.ceto.Camera;
import com.underwaterotter.ceto.Game;

public class MenuScene extends UIScene {

    private static final String BTN_START = "Start";
    private static final String BTN_ACHIEVEMENTS = "Achievements";
    private static final String BTN_SETTINGS = "Settings";
    private static final String BTN_QUIT = "Quit";

    @Override
    public void create(){
        super.create();

        int vw = Camera.main.getViewWidth();
        int vh = Camera.main.getViewHeight();
        this.camera = Camera.main;

        //TITLE
        //BACKGROUND SCROLL OF TERRAIN
        MenuButton keyPlay = new MenuButton(BTN_START, 0){
            protected void onClick() {
                Artifice.switchLevel(new TestLevel());
                Artifice.switchScene(GameScene.class);
            }
        };
        keyPlay.position(0, vh / 3 - keyPlay.height());
        add(keyPlay);

        MenuButton keyQuit = new MenuButton(BTN_QUIT, 0){
            protected void onClick(){
                Game.instance.finish();
            }
        };
        keyQuit.position(0, keyPlay.bottom() + 10);
        add(keyQuit);
    }
}
