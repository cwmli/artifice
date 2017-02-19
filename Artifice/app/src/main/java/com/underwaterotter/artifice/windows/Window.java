package com.underwaterotter.artifice.windows;

import android.graphics.RectF;

import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.ceto.Group;
import com.underwaterotter.ceto.Image;
import com.underwaterotter.math.Vector3;

/**
 * Created by Calvin on 08/02/2017.
 */

public abstract class Window extends Group {

    Vector3 position;

    private RectF windowDimensions;
    private Image windowBase;

    public Window(Object base, Vector3 pos) {
        position = pos;

        if (base.getClass() == Image.class) {
            windowBase = (Image)base;
        } else {
            windowBase = new Image(base);
        }
        add(windowBase);

        windowDimensions = new RectF(position.x,
                position.y,
                position.x + windowBase.width(),
                position.y + windowBase.height());

        createContent();
    }

    public void destroy() {
        super.destroy();
        windowBase.destroy();

        GameScene.scene.closeWindow(this);
    }

    public void resize(int width, int height) {
        windowBase.setScaleX(width);
        windowBase.setScaleY(height);

        windowDimensions = new RectF(position.x,
                position.y,
                position.x + windowBase.width(),
                position.y + windowBase.height());
    }

    public void setAlpha(float a) {
        windowBase.alpha_M(a);
    }

    public float getWidth() {
        return windowDimensions.width();
    }

    public float getHeight() {
        return windowDimensions.height();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 newPosition) {
        position = newPosition;
    }

    protected abstract void createContent();
}
