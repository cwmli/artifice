package com.underwaterotter.artifice.scenes;

import android.util.Log;

import com.underwaterotter.artifice.world.AnimatedTilemap;
import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.Joystick;
import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Group;
import com.underwaterotter.artifice.entities.Mob;
import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.artifice.entities.main.Char;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.Text;
import com.underwaterotter.ceto.ui.Button;

public class GameScene extends UIScene {

    public static GameScene scene;

    private Level currentLevel;

    private Char player;

    private Group world;
    private Group weather;
    private Group fauna;
    private Group pouches;

    public void create(){
        super.create();

        scene = this;
        currentLevel = Artifice.getLevel();

        player = new Char();
        //pre-init currentLevel setup
        if(Artifice.getDepth() < 0){
            currentLevel.isUnderground = true;
        }
        currentLevel.init();
        currentLevel.addMob(player);

        world = new Group();
        add(world);

        weather = new Group();
        add(weather);

        world.add(currentLevel);
        world.add(weather);

        fauna = new Group();
        add(fauna);

        pouches = new Group();
        add(pouches);

        Joystick joy = new Joystick();
        joy.camera = uiCamera;
        joy.position(Artifice.settings.getInt(Joystick.JOY_X, 20),
                Artifice.settings.getInt(Joystick.JOY_Y, 130));
        add(joy);
    }

    @Override
    public void destroy(){
        scene = null;

        super.destroy();
    }

    @Override
    public synchronized void update(){
        super.update();
    }

    public Char getPlayer(){
        return player;
    }

    private static class DebugButton extends Button {

        public static final int SIZE_W = 64;
        public static final int SIZE_H = 20;
        public static final int LEFT_PADDING = 5;

        private Image image;
        private Text label;

        public DebugButton(String text){
            super();

            image.textureRect(0, 0, SIZE_W, SIZE_H);

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
        }

        protected void onTouch() {
            Log.v("DEBUG_TOUCH", "Lightened button.");
        }
    }
}
