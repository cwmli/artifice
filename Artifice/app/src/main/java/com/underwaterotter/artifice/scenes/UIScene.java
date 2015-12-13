package com.underwaterotter.artifice.scenes;

import android.graphics.Color;

import com.underwaterotter.artifice.world.Assets;
import com.underwaterotter.ceto.Camera;
import com.underwaterotter.ceto.Game;
import com.underwaterotter.ceto.Scene;
import com.underwaterotter.ceto.Text;
import com.underwaterotter.cetoinput.Motions;

/**
 * UI Overlay that goes over extended scenes
 */

public class UIScene extends Scene{

    //Target virtual resolution
    public static final float VIRTUAL_WIDTH = 320;
    public static final float VIRTUAL_HEIGHT = 180;
    //Target zoom level
    public static final float EXPAND_LIMIT = 2f;

    public static float autoZoom;

    public static float minZoom;
    public static float maxZoom;

    public static Camera uiCamera;

    //FONTS PT
    public static Text.Font font12pt;
    public static Text.Font font16pt;
    public static Text.Font font20pt;

    public static Text.Font activeFont;
    public static float scale;

    public void create(){
        super.create();

        autoZoom = (int)Math.ceil(Game.density * EXPAND_LIMIT);

        while((Game.width / autoZoom < VIRTUAL_WIDTH &&
                Game.height / autoZoom < VIRTUAL_HEIGHT) &&
                autoZoom > 1){
            autoZoom--;
        }

        minZoom = 1;
        maxZoom = autoZoom * 1.5f;

        Camera.defaultCamera(autoZoom);

        uiCamera = Camera.createCamera(autoZoom);
        Camera.add(uiCamera);

        if(font12pt == null){

            font12pt = Text.Font.build(Assets.FONT12, Color.WHITE, Text.Font.LATIN_SCRIPT_F);
            font12pt.baseline = 3;

            font16pt = Text.Font.build(Assets.FONT16, Color.WHITE, Text.Font.LATIN_SCRIPT_F);
            font16pt.baseline = 4;

            font20pt = Text.Font.build(Assets.FONT20, Color.WHITE, Text.Font.LATIN_SCRIPT_F);
            font20pt.baseline = 5;
        }
    }

    @Override
    public void destroy(){
        super.destroy();
        Motions.motionListeners.clear();
    }

    public static void fontSize(float pt){
        fontSize(pt, autoZoom);
    }

    public static void fontSize(float pt, float zoom){

        float adjustedPt = pt * zoom;

        if(adjustedPt >= 20){
            float sizeOverflow = adjustedPt / 20;

            activeFont = font20pt;
            scale = (int)sizeOverflow;
        } else if(adjustedPt >= 16){
            float sizeOverflow = adjustedPt / 16;

            activeFont = font16pt;
            scale = (int)sizeOverflow;
        } else if(adjustedPt < 16){
            float sizeOverflow = adjustedPt / 12;

            activeFont = font12pt;
            scale = (int)sizeOverflow;
        }
        //get higher res and scale down
        scale /= zoom;
    }

    public static Text createText(String lines, Float size){
        fontSize(size);

        Text text = new Text(lines, activeFont);
        text.scale(scale);

        return text;
    }


}
