package com.underwaterotter.artifice.scenes;

import android.graphics.Color;
import android.util.Log;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Camera;
import com.underwaterotter.ceto.Game;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.Text;
import com.underwaterotter.ceto.ui.Button;

public class MenuScene extends UIScene {

    private static final String BTN_START = "Start";
    private static final String BTN_ACHIEVEMENTS = "Achievements";
    private static final String BTN_SETTINGS = "Settings";
    private static final String BTN_QUIT = "Quit";

    @Override
    public void create(){
        super.create();

        uiCamera.visible = false;

        int vw = Camera.main.viewWidth;
        int vh = Camera.main.viewHeight;
        this.camera = Camera.main;

        //TITLE
        //BACKGROUND SCROLL OF TERRAIN
        MenuItem keyPlay = new MenuItem(BTN_START, 0){
            public void onClick(){
                Artifice.switchScene(GameScene.class);
            }
        };
        keyPlay.position(0, vh / 3 - keyPlay.height());
        add(keyPlay);

        MenuItem keyQuit = new MenuItem(BTN_QUIT, 0){
            public void onClick(){
                //Game.instance.finish();
            }
        };
        keyQuit.position(0, keyPlay.bottom() + 10);
        add(keyQuit);
    }

    private static class MenuItem extends Button{

        public static final int SIZE_W = 64;
        public static final int SIZE_H = 20;
        public static final int LEFT_PADDING = 5;

        private Image image;
        private Text label;

        public MenuItem(String text, int type){
            super();

            image.textureRect(type * SIZE_W, 0, SIZE_W, SIZE_H);

            this.label = createText(text, 12f); //12font
            add(label);

            resize(SIZE_W, SIZE_H);
        }

        @Override
        public void createContent(){
            super.createContent();

            image = new Image(Assets.SELECTORS);
            add(image);
        }

        @Override
        public void updateHitbox(){
            super.updateHitbox();

            label.pos.x = x + LEFT_PADDING;
            label.pos.y = y - 4 + (image.height() / 2);

            image.pos.x = x;
            image.pos.y = y;
            //round x,y positions
        }

        protected void onTouch() {
            Log.v("MENU_TOUCH", "Lightened button.");
            image.tint(Color.WHITE, 1f);
        }

        protected void onRelease() {
            image.resetColor();
        }
    }
}
