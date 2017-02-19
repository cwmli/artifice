package com.underwaterotter.artifice.uibuttons;

import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.ceto.Text;
import com.underwaterotter.ceto.ui.Button;

import static com.underwaterotter.artifice.scenes.UIScene.createText;

/**
 * Created by Calvin on 09/02/2017.
 */

public abstract class MenuButton extends Button {
    public static final int SIZE_W = 64;
    public static final int SIZE_H = 20;
    public static final int LEFT_PADDING = 5;

    private Image image;
    private Text label;

    public MenuButton(String text, int type){
        super();

        image.textureRect(type * SIZE_W, 0, SIZE_W, SIZE_H);

        this.label = createText(text, 12f); //12font
        add(label);

        resize(SIZE_W, SIZE_H);
    }

    @Override
    protected void createContent(){
        super.createContent();

        image = new Image(Assets.SELECTORS);
        add(image);
    }

    @Override
    protected void updateHitbox(){
        super.updateHitbox();

        label.setPos(x + LEFT_PADDING, y - 4 + (image.height() / 2), 0);

        image.setPos(x, y, 0);
        //round x,y positions
    }

    protected void onTouch() {
        image.alpha_M(0.5f);
    }

    protected void onRelease() {
        image.alpha_M(1f);
    }
}
