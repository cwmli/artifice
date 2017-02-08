package com.underwaterotter.ceto.ui;

import com.underwaterotter.ceto.Group;
import com.underwaterotter.math.Vector2;

public abstract class HotBox extends Group {

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    public HotBox() {
        this(0, 0);
    }

    public HotBox(float x, float y) {
        super();

        this.x = x;
        this.y = y;

        createContent();
    }

    public void resize(float w, float h) {
        width = w;
        height = h;

        updateHitbox();
    }

    public float width() {
        return width;
    }

    public float height() {
        return height;
    }

    public Vector2 position() {
        return new Vector2(x, y);
    }

    public void position(float x, float y) {
        this.x = x;
        this.y = y;

        updateHitbox();
    }

    public int left() {
        return (int) x;
    }

    public int top() {
        return (int) y;
    }

    public int right() {
        return (int) (x + width);
    }

    public int bottom() {
        return (int) (y + height);
    }

    public Vector2 center() {
        return new Vector2(x + width / 2, y + height / 2);
    }

    //define sub-entities to this ui card
    protected abstract void createContent();

    protected abstract void updateHitbox();
}
