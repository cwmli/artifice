package com.underwaterotter.artifice.sprites;

import android.graphics.Color;
import android.graphics.RectF;

import com.underwaterotter.ceto.Game;
import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.glesutils.TextureAtlas;
import com.underwaterotter.math.Vector2;

public class ItemSprite extends Sprite {

    public static final int SIZE = 16;
    protected static TextureAtlas source;

    public float fullglowTime = 1f;

    private boolean brighten;
    private float timer;

    public int enchantColor;

    protected RectF texLoc;

    public Item item;

    public ItemSprite(int id){
        //super(ITEMSPRITESHHET);
        if(source == null) {
            source = new TextureAtlas(texture);
            source.populateFixedCells(SIZE, SIZE);
        }
        texLoc = source.get(id);
        setOrigin();
    }

    public void setOrigin(){
        origin(new Vector2(SIZE / 2, SIZE / 2));
    }

    @Override
    public void update(){
        super.update();

        updateEnchantedEffect();
    }

    public void updateEnchantedEffect(){

        if(brighten && (timer += Game.elapsedTime) > fullglowTime){
            brighten = false;
            timer = fullglowTime;
        } else if(!brighten && (timer -= Game.elapsedTime) < 0){
            brighten = true;
            timer = 0;
        }

        float strength = timer / fullglowTime;

        mr = mg = mb = 1 - strength;
        ar = Color.red(enchantColor) * strength;
        ag = Color.green(enchantColor) * strength;
        ab = Color.blue(enchantColor) * strength;
    }
}
